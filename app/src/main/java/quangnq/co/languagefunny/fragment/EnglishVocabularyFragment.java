package quangnq.co.languagefunny.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import quangnq.co.languagefunny.R;
import quangnq.co.languagefunny.common.FileCommon;
import quangnq.co.languagefunny.entity.Choice;
import quangnq.co.languagefunny.entity.EnglishVocabuaryEntity;
import quangnq.co.languagefunny.entity.LessonEntity;
import quangnq.co.languagefunny.entity.LessonEntityManager;
import quangnq.co.languagefunny.entity.QuestionEntity;
import quangnq.co.languagefunny.entity.QuestionEntityManager;
import quangnq.co.languagefunny.manager.EnglishVocabuaryEntityManager;

/**
 * Created by quang on 5/19/2018.
 */

public class EnglishVocabularyFragment extends QuestionFragment {
  
  Button btnSound, btnAdd, btnConirmNext, btnEnglishWordOne, btnEnglishWordTwo, btnEnglishWordThree, btnEnglishWordFour;
  TextView tvClock, tvQuestionTrue, tvQuestionAnswered, tvQuestionSum, tvVietnamWord, tvPronounceWord;
  ArrayList<Choice> listChoice = new ArrayList<>();
  
  EnglishVocabuaryEntityManager entities = new EnglishVocabuaryEntityManager();
  EnglishVocabuaryEntity currentEntity;
  
  int index;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_english_vocabulary, container, false);
    
    btnSound = (Button) view.findViewById(R.id.btn_sound);
    btnAdd = (Button) view.findViewById(R.id.btn_add);
    btnConirmNext = (Button) view.findViewById(R.id.btn_conirm_next);
    
    tvClock = (TextView) view.findViewById(R.id.tv_clock);
    tvQuestionTrue = (TextView) view.findViewById(R.id.tv_question_true);
    tvQuestionAnswered = (TextView) view.findViewById(R.id.tv_question_answered);
    tvQuestionSum = (TextView) view.findViewById(R.id.tv_question_sum);
    tvConten = (TextView) view.findViewById(R.id.tv_content);
    tvDisplay = (TextView) view.findViewById(R.id.tv_display);
    
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
  void executeButtonChange() {
    String temp = currentQuestionEntity.getContent();
    currentQuestionEntity.setContent(currentQuestionEntity.getAnswer());
    currentQuestionEntity.setAnswer(temp);
    currentQuestionEntity.updateToFile();
    tvConten.setText(currentQuestionEntity.getContent());

    if (listChoice.get(0).isTrue) {
      btnOne.setText(currentQuestionEntity.getAnswer());
    } else if (listChoice.get(1).isTrue) {
      btnTwo.setText(currentQuestionEntity.getAnswer());
    } else if (listChoice.get(2).isTrue) {
      btnThree.setText(currentQuestionEntity.getAnswer());
    } else if (listChoice.get(3).isTrue) {
      btnFour.setText(currentQuestionEntity.getAnswer());
    }
  }

  @Override
  void initial() {
    entities.clear();
    LessonEntityManager lessonEntities = (LessonEntityManager) getArguments().getSerializable(KEY_LIST_LESSON_SELECTED);
    ArrayList<String> entityStrings = FileCommon.readFile(PATH_ENGLISH_VOCABULARY + "vocabulary.txt");
    EnglishVocabuaryEntityManager allEntities = new EnglishVocabuaryEntityManager();
    allEntities.createListFromArrayString(entityStrings);
    for (LessonEntity lessonEntity : lessonEntities) {
      int position = Integer.parseInt(lessonEntity.getId());
      for (int i = position*50; i < allEntities.size() && i < position*50 + 50; i++) {
        entities.add(allEntities.get(i));
      }
    }
  
    for (EnglishVocabuaryEntity entity : allEntities) {
      if (entity.getIsSave() == 1 || entity.getNumberAgain() > 0) {
        if (entities.getPosition(entity.getId()) == -1) {
          entities.add(entity);
        }
      }
    }
    
    index = 0;
    
    for (EnglishVocabuaryEntity entity : entities) {
      if (entity.getNumberAgain() <= 0 && entity.getIsSave() == 0) {
        entity.setIsSave(1);
        entity.updateToFile();
      }
    }

    Collections.shuffle(entities);
    display();
  }

  @Override
  void display (){
    if (index >= entities.size()) {
      showDialog("You are finished those Lesson", "You are continue test");
      return;
    }
    currentEntity = entities.get(index);
    tvVietnamWord.setText("");
    tvPronounceWord.setText("");
    tvQuestionSum.setText(Integer.toString(entities.size()));
    
    listChoice.clear();
    String answerTrue = currentEntity.getEnglishWord() + " " + currentEntity.getKindWord();
    listChoice.add(new Choice(answerTrue, true));
    EnglishVocabuaryEntityManager tempEntities = new EnglishVocabuaryEntityManager(entities);
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
  }
  
  void setDisplayControllAfterSelected () {
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
  }
  
  void executeSelected(boolean isChoosed) {
    if (isChoosed) {
      if (currentEntity.getNumberAgain() > 0) {
        currentEntity.setNumberAgain(currentEntity.getNumberAgain()-1);
      } else {
        currentEntity.setNumberAgain(0);
      }
      Toast.makeText(getActivity(), "Number Again +" + currentEntity.getNumberAgain(), Toast.LENGTH_SHORT).show();
      currentEntity.setIsSave(NOT_SAVE);
      currentEntity.updateToFile();
      int numberTrue = Integer.parseInt(tvQuestionTrue.getText().toString());
      numberTrue++;
      tvQuestionTrue.setText(Integer.toString(numberTrue));
      btnAdd.setEnabled(true);
    } else {
      addQuestion();
    }
    int answered = Integer.parseInt(tvQuestionAnswered.getText().toString());
    answered++;
    tvQuestionAnswered.setText(Integer.toString(answered));
    
  }
  
  boolean clickButton(int numberClick) {
    setDisplayControllAfterSelected();
    if (listChoice.get(numberClick).isTrue) {
      executeSelected(true);
      return true;
    }
    executeSelected(false);
    return false;
  }

  @Override
  void addQuestion() {

    entities.add(currentEntity);
    tvQuestionSum.setText(Integer.toString(entities.size()));

    if (currentEntity.getNumberAgain() != 5) {
      currentEntity.setNumberAgain(5);
      currentEntity.setIsSave(1);
      currentEntity.updateToFile();
    }
    Toast.makeText(getActivity(), "Number Again +" + currentEntity.getNumberAgain(), Toast.LENGTH_SHORT).show();
  }

  @Override
  void executeButtonNext() {
    index++;
    btnConirmNext.setText(CONFIRM_BUTTON);
    display();
  }
  
  
  
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_add:
        addQuestion();
        btnAdd.setEnabled(false);
        break;
      case R.id.btn_sound:
        executeButtonChange();
        break;
      case R.id.btn_conirm_next:
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
          btnTwo.setBackgroundResource(R.drawable.bcg_bt_qs_false);
        }
        break;
      case R.id.btn_three:
        if (!clickButton(2)) {
          btnThree.setBackgroundResource(R.drawable.bcg_bt_qs_false);
        }
        break;
      case R.id.btn_four:
        if (!clickButton(3)) {
          btnFour.setBackgroundResource(R.drawable.bcg_bt_qs_false);
        }
        break;
    }
  }
  
  private void openDeleteDialog(String title, String content) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
    builder.setTitle(title);
    builder.setMessage(content);
    builder.setCancelable(false);
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        currentEntity.deleteQuestionToFile();
        executeButtonNext();
      }
    });
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.cancel();
      }
    });
    AlertDialog alertDialog = builder.create();
    alertDialog.show();
  }
  
  @Override
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
