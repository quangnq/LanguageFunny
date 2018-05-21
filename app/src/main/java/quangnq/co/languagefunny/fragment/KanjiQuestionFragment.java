package quangnq.co.languagefunny.fragment;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import quangnq.co.languagefunny.R;
import quangnq.co.languagefunny.entity.Choice;
import quangnq.co.languagefunny.entity.KanjiQuestionEntity;
import quangnq.co.languagefunny.entity.KanjiQuestionEntityManager;
import quangnq.co.languagefunny.entity.KanjiSampleQuestionEntity;
import quangnq.co.languagefunny.entity.QuestionEntity;

/**
 * Created by quang on 5/19/2018.
 */

public class KanjiQuestionFragment extends QuestionFragment {
  KanjiQuestionEntityManager kanjiQuestionEntityManager = new KanjiQuestionEntityManager();
  int indexKanjiSample;
  boolean isKanjiSampleQuestion;
  ArrayList<KanjiSampleQuestionEntity> listKanjiSample =  new ArrayList<>();
  
  @Override
  void initial() {
    super.initial();
    kanjiQuestionEntityManager.clear();
    kanjiQuestionEntityManager.initial(questionEntityManager);
    for (KanjiQuestionEntity entity : kanjiQuestionEntityManager) {
      if (entity.getNumberAgain() <= MIN_NUMBER_AGAIN && entity.getIsSave() == NOT_SAVE) {
        entity.setIsSave(SAVE);
        entity.updateToFile();
      }
    }
    Collections.shuffle(kanjiQuestionEntityManager);
    display();
  }
  
  void displayKanjiSample() {
    // if index kanji sample >= list kanji Sample then display next kanji Question
    if (indexKanjiSample >= listKanjiSample.size()) {
      index++;
      display();
      return;
    }
    isKanjiSampleQuestion = true;
    // if index kanji sample < list kanji Sample then display next kanji Sample Question
    currentQuestionEntity = listKanjiSample.get(indexKanjiSample);
    tvConten.setText(currentQuestionEntity.getContent());
    
    listChoice.clear();
    String answerTrue = currentQuestionEntity.getAnswer();
    listChoice.add(new Choice(answerTrue, true));
    ArrayList<KanjiSampleQuestionEntity> listTemp = new ArrayList<>(listKanjiSample);
    Collections.shuffle(listTemp);
    for (int i = 0; i < listTemp.size(); i++) {
      if (!listTemp.get(i).getAnswer().equals(answerTrue)) {
        listChoice.add(new Choice(listTemp.get(i).getAnswer(), false));
      }
      if (listChoice.size() == 4) {
        break;
      }
    }
    setDisplayControllBeforeSelect();
  }
  
  @Override
  void executeSelected(boolean isChoosed) {
    if (isKanjiSampleQuestion) {
      if (!isChoosed) {
        listKanjiSample.add(listKanjiSample.get(indexKanjiSample));
      }
      return;
    }
    super.executeSelected(isChoosed);
  }
  
  @Override
  void addQuestion() {
    
    kanjiQuestionEntityManager.add((KanjiQuestionEntity) currentQuestionEntity);
    tvQuestionSum.setText(Integer.toString(kanjiQuestionEntityManager.size()));
    listKanjiSample.clear();
    
    if (currentQuestionEntity.getNumberAgain() != MAX_NUMBER_AGAIN) {
      currentQuestionEntity.setNumberAgain(MAX_NUMBER_AGAIN);
      currentQuestionEntity.setIsSave(SAVE);
      currentQuestionEntity.updateToFile();
    }
    Toast.makeText(getActivity(), "Number Again +" + currentQuestionEntity.getNumberAgain(), Toast.LENGTH_SHORT).show();
  }
  
  @Override
  void setDisplayControllAfterSelected() {
    super.setDisplayControllAfterSelected();
  }
  
  void display() {
    if (index >= kanjiQuestionEntityManager.size()) {
      showDialog("You are finished those Lesson", "You are continue test");
      return;
    }
    isKanjiSampleQuestion = false;
    indexKanjiSample = -1;
    KanjiQuestionEntity currentKanji = kanjiQuestionEntityManager.get(index);
    tvConten.setText(currentKanji.getContent());
    
    listKanjiSample.clear();
    listKanjiSample.addAll(currentKanji.getListSample());
    Collections.shuffle(listKanjiSample);
    currentQuestionEntity = currentKanji;
    
    listChoice.clear();
    String answerTrue = currentKanji.getAnswer();
    listChoice.add(new Choice(answerTrue, true));
    ArrayList<KanjiQuestionEntity> listTemp = new ArrayList<>(kanjiQuestionEntityManager);
    Collections.shuffle(listTemp);
    for (int i = 0; i < listTemp.size(); i++) {
      if (!listTemp.get(i).getAnswer().equals(answerTrue)) {
        listChoice.add(new Choice(listTemp.get(i).getAnswer(), false));
      }
      if (listChoice.size() == 4) {
        break;
      }
    }
    tvQuestionSum.setText(Integer.toString(kanjiQuestionEntityManager.size()));
    setDisplayControllBeforeSelect();
  }
  
  @Override
  void executeButtonNext() {
    indexKanjiSample++;
    displayKanjiSample();
  }
  
  @Override
  void executeButtonAdd() {
  
  }
}
