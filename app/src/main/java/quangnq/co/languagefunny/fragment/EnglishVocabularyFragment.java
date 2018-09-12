package quangnq.co.languagefunny.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
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
    ArrayList<String> EntityStrings = FileCommon.readFile(PATH_ENGLISH_VOCABULARY + "vocabulary.txt");
    index = 0;

    for (QuestionEntity entity : questionEntityManager) {
      if (entity.getNumberAgain() <= MIN_NUMBER_AGAIN && entity.getIsSave() == NOT_SAVE) {
        entity.setIsSave(SAVE);
        entity.updateToFile();
      }
    }

    Collections.shuffle(questionEntityManager);
    display();
  }

  @Override
  void display (){
    if (index >= questionEntityManager.size()) {
      showDialog("You are finished those Lesson", "You are continue test");
      return;
    }
    currentQuestionEntity = questionEntityManager.get(index);
    String[] arr = currentQuestionEntity.getContent().split("-");
    tvConten.setText(arr[0].trim());

    listChoice.clear();
    String answerTrue = currentQuestionEntity.getAnswer();
    listChoice.add(new Choice(answerTrue, true));
    ArrayList<QuestionEntity> listTemp = new ArrayList<>(questionEntityManager);
    Collections.shuffle(listTemp);
    for (int i = 0; i < listTemp.size(); i++) {
      if (!listTemp.get(i).getAnswer().equals(answerTrue)) {
        listChoice.add(new Choice(listTemp.get(i).getAnswer(), false));
      }
      if (listChoice.size() == 4) {
        break;
      }
    }
    tvQuestionSum.setText(Integer.toString(questionEntityManager.size()));
    setDisplayControllBeforeSelect();
  }

  @Override
  void addQuestion() {

    questionEntityManager.add(currentQuestionEntity);
    tvQuestionSum.setText(Integer.toString(questionEntityManager.size()));

    if (currentQuestionEntity.getNumberAgain() != MAX_NUMBER_AGAIN) {
      currentQuestionEntity.setNumberAgain(MAX_NUMBER_AGAIN);
      currentQuestionEntity.setIsSave(SAVE);
      currentQuestionEntity.updateToFile();
    }
    Toast.makeText(getActivity(), "Number Again +" + currentQuestionEntity.getNumberAgain(), Toast.LENGTH_SHORT).show();
  }

  @Override
  void executeButtonNext() {
    index++;
    btnConirmNext.setText(CONFIRM_BUTTON);
    display();
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
