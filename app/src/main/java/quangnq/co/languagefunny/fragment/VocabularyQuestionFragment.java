package quangnq.co.languagefunny.fragment;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import quangnq.co.languagefunny.entity.Choice;
import quangnq.co.languagefunny.entity.KanjiQuestionEntity;
import quangnq.co.languagefunny.entity.QuestionEntity;
import quangnq.co.languagefunny.entity.QuestionEntityManager;

/**
 * Created by quang on 5/19/2018.
 */

public class VocabularyQuestionFragment extends QuestionFragment {
    
    @Override
    void executeButtonChange() {
        String temp = currentQuestionEntity.getContent();
        currentQuestionEntity.setContent(currentQuestionEntity.getAnswer());
        currentQuestionEntity.setAnswer(temp);
        currentQuestionEntity.updateToFile();
        tvConten.setText(currentQuestionEntity.getContent());
        tvQuestionAnswered.setText(currentQuestionEntity.getAnswer());
    }
    
    @Override
    void initial() {
        super.initial();
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
        tvConten.setText(currentQuestionEntity.getContent());
        
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
        display();
    }
}