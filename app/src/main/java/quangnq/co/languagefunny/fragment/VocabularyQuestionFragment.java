package quangnq.co.languagefunny.fragment;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import quangnq.co.languagefunny.entity.Choice;
import quangnq.co.languagefunny.entity.QuestionEntity;

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
        super.initial();
        
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
}
