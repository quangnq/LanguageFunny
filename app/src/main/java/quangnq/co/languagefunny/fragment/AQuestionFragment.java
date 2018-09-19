package quangnq.co.languagefunny.fragment;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import quangnq.co.languagefunny.entity.AQuestionEntity;
import quangnq.co.languagefunny.entity.Choice;
import quangnq.co.languagefunny.manager.AEntityManager;

/**
 * Created by quang on 4/21/2018.
 */

public abstract class AQuestionFragment<E extends AQuestionEntity, M extends AEntityManager<E>> extends BaseFragment {
  
  Button btnAdd, btnConirmNext;
  TextView tvClock, tvQuestionTrue, tvQuestionAnswered, tvQuestionSum;
  ArrayList<Choice> listChoice = new ArrayList<>();
  
  public E currentEntity;
  public int index;
  
  M entitiesTemp;
  M entities;
  
  public abstract void initial();
  public abstract void display ();
  public abstract void setDisplayControllAfterSelected();
  
  public void showDialog(String title, String content) {
    
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle(title);
    builder.setMessage(content);
    builder.setCancelable(false);
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        initial();
        dialogInterface.dismiss();
      }
    });
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        forward(getBackFragment());
      }
    });
    AlertDialog alertDialog = builder.create();
    alertDialog.show();
  }
  
  public void openDeleteDialog(String title, String content) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
    builder.setTitle(title);
    builder.setMessage(content);
    builder.setCancelable(false);
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        currentEntity.deleteQuestionToFile();
        entities.remove(entities.getPosition(currentEntity.getId()));
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
  
  public void executeButtonNext() {
    index++;
    btnConirmNext.setText(CONFIRM_BUTTON);
    display();
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
  
  void addQuestion() {
    entitiesTemp.add(currentEntity);
    tvQuestionSum.setText(Integer.toString(entitiesTemp.size()));
    
    if (currentEntity.getNumberAgain() != 5) {
      currentEntity.setNumberAgain(5);
      currentEntity.setIsSave(1);
      currentEntity.updateToFile();
    }
    Toast.makeText(getActivity(), "Number Again +" + currentEntity.getNumberAgain(), Toast.LENGTH_SHORT).show();
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
}
