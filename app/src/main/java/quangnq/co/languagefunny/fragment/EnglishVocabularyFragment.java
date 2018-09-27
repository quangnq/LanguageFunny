package quangnq.co.languagefunny.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;

import quangnq.co.languagefunny.R;
import quangnq.co.languagefunny.common.FileCommon;
import quangnq.co.languagefunny.common.SoundManager;
import quangnq.co.languagefunny.entity.Choice;
import quangnq.co.languagefunny.entity.EnglishVocabuaryEntity;
import quangnq.co.languagefunny.manager.EnglishVocabuaryEntityManager;

/**
 * Created by quang on 5/19/2018.
 */

public class EnglishVocabularyFragment extends AQuestionFragment<EnglishVocabuaryEntity, EnglishVocabuaryEntityManager> implements View.OnClickListener {
  
  Button btnSound, btnEnglishWordOne, btnEnglishWordTwo, btnEnglishWordThree, btnEnglishWordFour;
  TextView tvVietnamWord, tvPronounceWord;
  
  /**
   * manage sound
   */
  private SoundManager mSoundManager = new SoundManager(getMainActivity());
  
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_english_vocabulary, container, false);
  
    entitiesTemp = new EnglishVocabuaryEntityManager();
    
    btnSound = (Button) view.findViewById(R.id.btn_sound);
    btnAdd = (Button) view.findViewById(R.id.btn_add);
    btnConirmNext = (Button) view.findViewById(R.id.btn_confirm_next);
    
    tvClock = (TextView) view.findViewById(R.id.tv_clock);
    tvQuestionTrue = (TextView) view.findViewById(R.id.tv_question_true);
    tvQuestionAnswered = (TextView) view.findViewById(R.id.tv_question_answered);
    tvQuestionSum = (TextView) view.findViewById(R.id.tv_question_sum);
    tvVietnamWord = (TextView) view.findViewById(R.id.tv_vietnam_word);
    tvPronounceWord = (TextView) view.findViewById(R.id.tv_pronounce_word);
    
    btnEnglishWordOne = (Button) view.findViewById(R.id.btn_english_word_one);
    btnEnglishWordTwo = (Button) view.findViewById(R.id.btn_english_word_two);
    btnEnglishWordThree = (Button) view.findViewById(R.id.btn_english_word_three);
    btnEnglishWordFour = (Button) view.findViewById(R.id.btn_english_word_four);
  
    btnSound.setOnClickListener(this);
    btnAdd.setOnClickListener(this);
    btnConirmNext.setOnClickListener(this);
    btnEnglishWordOne.setOnClickListener(this);
    btnEnglishWordTwo.setOnClickListener(this);
    btnEnglishWordThree.setOnClickListener(this);
    btnEnglishWordFour.setOnClickListener(this);
    
    tvVietnamWord.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View view) {
        openEditDialog();
        return true;
      }
    });
  
    tvPronounceWord.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View view) {
        if (btnConirmNext.getText().toString().equals(NEXT_BUTTON)) {
          openDeleteDialog("Confirm", "You want to delete this question ???");
        }
        return true;
      }
    });
    
    return view;
  }
  
  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    entities = (EnglishVocabuaryEntityManager) getArguments().getSerializable(KEY_QUESTION_SELECTED);
    initial();
    Bundle bundle = new Bundle();
    bundle.putSerializable(KEY_ENTITY, currentEntity.getLessonEntity().getLearningTypeEntity());
    LessonFragment lessonFragment = new LessonFragment();
    lessonFragment.setArguments(bundle);
    setBackFragment(lessonFragment);
  }
  
  void executeButtonSound() {
    mSoundManager.setPlay(currentEntity.getLessonEntity().getLearningTypeEntity().getPath() + "/Sound/" + currentEntity.getSoundID() + ".mp3");
  }

  @Override
  public void initial() {
    entitiesTemp.clear();
    entitiesTemp.addAll(entities);
    
    index = 0;
    
    for (EnglishVocabuaryEntity entity : entitiesTemp) {
      if (entity.getNumberAgain() <= 0 && entity.getIsSave() == 0) {
        entity.setIsSave(1);
        entity.updateToFile();
      }
    }

    Collections.shuffle(entitiesTemp);
    display();
  }
  
  @Override
  public void display (){
    if (index >= entitiesTemp.size()) {
      showDialog("You are finished those Lesson", "You are continue test");
      FileCommon.writeFile(currentEntity.getLessonEntity().getLearningTypeEntity().getPath() + FILE_LESSON_LEARNED,
          getArguments().getString(KEY_STRING_LESSON_SELECTEDS), true);
      return;
    }
    
    currentEntity = entitiesTemp.get(index);
    tvVietnamWord.setText("");
    tvPronounceWord.setText("");
    tvQuestionSum.setText(Integer.toString(entitiesTemp.size()));
    
    listChoice.clear();
    String answerTrue = currentEntity.getEnglishWord() + " " + currentEntity.getKindWord();
    listChoice.add(new Choice(answerTrue, true));
    EnglishVocabuaryEntityManager tempEntities = new EnglishVocabuaryEntityManager(entitiesTemp);
    Collections.shuffle(tempEntities);
    
    for (EnglishVocabuaryEntity entity : tempEntities) {
      if (!entity.getEnglishWord().trim().equals(currentEntity.getEnglishWord().trim())) {
        listChoice.add(new Choice(entity.getEnglishWord() + " " + entity.getKindWord(), false));
      }
      if (listChoice.size() == 4) {
        break;
      }
    }
    for (int i = listChoice.size(); i < 4; i++) {
      listChoice.add(new Choice("choice sample", false));
    }
    
    Collections.shuffle(listChoice);
  
    btnEnglishWordOne.setBackgroundResource(R.drawable.bcg_bt_qs);
    btnEnglishWordTwo.setBackgroundResource(R.drawable.bcg_bt_qs);
    btnEnglishWordThree.setBackgroundResource(R.drawable.bcg_bt_qs);
    btnEnglishWordFour.setBackgroundResource(R.drawable.bcg_bt_qs);
    btnEnglishWordOne.setText(listChoice.get(0).content);
    btnEnglishWordTwo.setText(listChoice.get(1).content);
    btnEnglishWordThree.setText(listChoice.get(2).content);
    btnEnglishWordFour.setText(listChoice.get(3).content);
    btnEnglishWordOne.setEnabled(true);
    btnEnglishWordTwo.setEnabled(true);
    btnEnglishWordThree.setEnabled(true);
    btnEnglishWordFour.setEnabled(true);
  
    btnAdd.setEnabled(false);
    executeButtonSound();
  }
  
  @Override
  public void setDisplayControllAfterSelected () {
    if (listChoice.get(0).isTrue)
      btnEnglishWordOne.setBackgroundResource(R.drawable.bcg_bt_qs_true);
    if (listChoice.get(1).isTrue)
      btnEnglishWordTwo.setBackgroundResource(R.drawable.bcg_bt_qs_true);
    if (listChoice.get(2).isTrue)
      btnEnglishWordThree.setBackgroundResource(R.drawable.bcg_bt_qs_true);
    if (listChoice.get(3).isTrue)
      btnEnglishWordFour.setBackgroundResource(R.drawable.bcg_bt_qs_true);
    btnEnglishWordOne.setEnabled(false);
    btnEnglishWordTwo.setEnabled(false);
    btnEnglishWordThree.setEnabled(false);
    btnEnglishWordFour.setEnabled(false);
    
    btnConirmNext.setText(NEXT_BUTTON);
    tvVietnamWord.setText(currentEntity.getVietnamWord());
    tvPronounceWord.setText(currentEntity.getPronounceWord());
    executeButtonSound();
  }
  
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_add:
        addQuestion();
        btnAdd.setEnabled(false);
        executeButtonSound();
        break;
      case R.id.btn_sound:
        executeButtonSound();
        break;
      case R.id.btn_confirm_next:
        if (btnConirmNext.getText().equals(NEXT_BUTTON)) {
          executeButtonNext();
        } else {
          setDisplayControllAfterSelected();
          executeSelected(false);
        }
        break;
      case R.id.btn_english_word_one:
        if (!clickButton(0)) {
          btnEnglishWordOne.setBackgroundResource(R.drawable.bcg_bt_qs_false);
        }
        break;
      case R.id.btn_english_word_two:
        if (!clickButton(1)) {
          btnEnglishWordTwo.setBackgroundResource(R.drawable.bcg_bt_qs_false);
        }
        break;
      case R.id.btn_english_word_three:
        if (!clickButton(2)) {
          btnEnglishWordThree.setBackgroundResource(R.drawable.bcg_bt_qs_false);
        }
        break;
      case R.id.btn_english_word_four:
        if (!clickButton(3)) {
          btnEnglishWordFour.setBackgroundResource(R.drawable.bcg_bt_qs_false);
        }
        break;
    }
  }
  
  void openEditDialog() {
    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
    // Get the layout inflater
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View view = inflater.inflate(R.layout.dialog_english_vocabulary_update, null);
    final EditText edtEnglishWord = (EditText) view.findViewById(R.id.edt_english_word);
    final EditText edtKindWord = (EditText) view.findViewById(R.id.edt_kind_word);
    final EditText edtPronounceWord = (EditText) view.findViewById(R.id.edt_pronounce_word);
    final EditText edtVietnameWord = (EditText) view.findViewById(R.id.edt_vietnam_word);
    edtEnglishWord.setText(currentEntity.getEnglishWord());
    edtKindWord.setText(currentEntity.getKindWord());
    edtPronounceWord.setText(currentEntity.getPronounceWord());
    edtVietnameWord.setText(currentEntity.getVietnamWord());
    builder.setView(view)
      .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int id) {
          String englishWord = String.valueOf(edtEnglishWord.getText()).trim();
          String kindWord = String.valueOf(edtKindWord.getText()).trim();
          String pronounceWord = String.valueOf(edtPronounceWord.getText()).trim();
          String vietnameWord = String.valueOf(edtVietnameWord.getText()).trim();
          if (!"".equals(englishWord)) {
            currentEntity.setEnglishWord(englishWord);
          }
          if (!"".equals(kindWord)) {
            currentEntity.setKindWord(kindWord);
          }
          if (!"".equals(pronounceWord)) {
            currentEntity.setPronounceWord(pronounceWord);
          }
          if (!"".equals(vietnameWord)) {
            currentEntity.setVietnamWord(vietnameWord);
          }
          currentEntity.updateToFile();
          Toast.makeText(getActivity(), "Update Success", Toast.LENGTH_SHORT).show();
          tvVietnamWord.setText(currentEntity.getVietnamWord());
          if (!"".equals(tvPronounceWord.getText())) {
            tvPronounceWord.setText(currentEntity.getPronounceWord());
          }
          
          if (listChoice.get(0).isTrue)
            btnEnglishWordOne.setText(currentEntity.getEnglishWord());
          if (listChoice.get(1).isTrue)
            btnEnglishWordTwo.setText(currentEntity.getEnglishWord());
          if (listChoice.get(2).isTrue)
            btnEnglishWordThree.setText(currentEntity.getEnglishWord());
          if (listChoice.get(3).isTrue)
            btnEnglishWordFour.setText(currentEntity.getEnglishWord());
        }
      }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        dialog.cancel();
      }
    });
    builder.create().show();
  }
}
