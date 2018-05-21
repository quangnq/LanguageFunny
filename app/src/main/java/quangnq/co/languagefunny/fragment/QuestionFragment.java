package quangnq.co.languagefunny.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import quangnq.co.languagefunny.entity.LessonEntityManager;
import quangnq.co.languagefunny.entity.QuestionEntity;
import quangnq.co.languagefunny.entity.QuestionEntityManager;

/**
 * Created by quang on 4/21/2018.
 */

public class QuestionFragment extends BaseFragment implements View.OnClickListener {
  
  Button btnChangeLevel, btnAdd, btnConirmNext, btnOne, btnTwo, btnThree, btnFour;
  TextView tvClock, tvQuestionTrue, tvQuestionAnswered, tvQuestionSum, tvConten, tvDisplay;
  ArrayList<Choice> listChoice = new ArrayList<>();
  
  QuestionEntityManager questionEntityManager = new QuestionEntityManager();
  QuestionEntity currentQuestionEntity;
  int index;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_question, container, false);
    
    btnChangeLevel = (Button) view.findViewById(R.id.btn_change_level);
    btnAdd = (Button) view.findViewById(R.id.btn_add);
    btnConirmNext = (Button) view.findViewById(R.id.btn_conirm_next);
    
    tvClock = (TextView) view.findViewById(R.id.tv_clock);
    tvQuestionTrue = (TextView) view.findViewById(R.id.tv_question_true);
    tvQuestionAnswered = (TextView) view.findViewById(R.id.tv_question_answered);
    tvQuestionSum = (TextView) view.findViewById(R.id.tv_question_sum);
    tvConten = (TextView) view.findViewById(R.id.tv_content);
    tvDisplay = (TextView) view.findViewById(R.id.tv_display);
    
    btnOne = (Button) view.findViewById(R.id.btn_one);
    btnTwo = (Button) view.findViewById(R.id.btn_two);
    btnThree = (Button) view.findViewById(R.id.btn_three);
    btnFour = (Button) view.findViewById(R.id.btn_four);
    
    btnChangeLevel.setOnClickListener(this);
    btnAdd.setOnClickListener(this);
    btnConirmNext.setOnClickListener(this);
    btnOne.setOnClickListener(this);
    btnTwo.setOnClickListener(this);
    btnThree.setOnClickListener(this);
    btnFour.setOnClickListener(this);
    
    tvConten.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View view) {
        openEditDialog();
        return true;
      }
    });
    
    return view;
  }
  
  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initial();
    Bundle bundle = new Bundle();
    bundle.putSerializable(KEY_ENTITY, currentQuestionEntity.getLessonEntity().getLearningTypeEntity());
    LessonFragment lessonFragment = new LessonFragment();
    lessonFragment.setArguments(bundle);
    setBackFragment(lessonFragment);
  }
  
  void initial() {
    questionEntityManager.clear();
    Toast.makeText(getActivity(), questionEntityManager.createEntityListFromLessons((LessonEntityManager) getArguments().getSerializable(KEY_LIST_LESSON_SELECTED))
        , Toast.LENGTH_SHORT).show();
    index = 0;
  }
  
  void display (){
  }
  
  void setDisplayControllAfterSelected () {
    if (listChoice.get(0).isTrue)
      btnOne.setBackgroundResource(R.drawable.bcg_bt_qs_true);
    if (listChoice.get(1).isTrue)
      btnTwo.setBackgroundResource(R.drawable.bcg_bt_qs_true);
    if (listChoice.get(2).isTrue)
      btnThree.setBackgroundResource(R.drawable.bcg_bt_qs_true);
    if (listChoice.get(3).isTrue)
      btnFour.setBackgroundResource(R.drawable.bcg_bt_qs_true);
    btnOne.setEnabled(false);
    btnTwo.setEnabled(false);
    btnThree.setEnabled(false);
    btnFour.setEnabled(false);
    
    btnChangeLevel.setEnabled(true);
    btnConirmNext.setText(NEXT_BUTTON);
    tvDisplay.setText(currentQuestionEntity.getDisplay()
        + "_" + currentQuestionEntity.getId());
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
  
  void executeSelected(boolean isChoosed) {
    if (isChoosed) {
      if (currentQuestionEntity.getNumberAgain() > MIN_NUMBER_AGAIN) {
        int number = currentQuestionEntity.getNumberAgain();
        number--;
        currentQuestionEntity.setNumberAgain(number);
      } else {
        currentQuestionEntity.setNumberAgain(MIN_NUMBER_AGAIN);
      }
      Toast.makeText(getActivity(), "Number Again +" + currentQuestionEntity.getNumberAgain(), Toast.LENGTH_SHORT).show();
      currentQuestionEntity.setIsSave(NOT_SAVE);
      currentQuestionEntity.updateToFile();
      int temp = Integer.parseInt(tvQuestionTrue.getText().toString());
      temp++;
      tvQuestionTrue.setText(Integer.toString(temp));
      btnAdd.setEnabled(true);
    } else {
      addQuestion();
    }
    int answered = Integer.parseInt(tvQuestionAnswered.getText().toString());
    answered++;
    tvQuestionAnswered.setText(Integer.toString(answered));
  }
  
  void addQuestion() {
  
  }
  
  void setDisplayControllBeforeSelect () {
    for (int i = listChoice.size(); i < 4; i++) {
      listChoice.add(new Choice("choice sample", false));
    }
    Collections.shuffle(listChoice);
    tvDisplay.setText("");
    
    btnOne.setBackgroundResource(R.drawable.bcg_bt_qs);
    btnTwo.setBackgroundResource(R.drawable.bcg_bt_qs);
    btnThree.setBackgroundResource(R.drawable.bcg_bt_qs);
    btnFour.setBackgroundResource(R.drawable.bcg_bt_qs);
    btnOne.setText(listChoice.get(0).content);
    btnTwo.setText(listChoice.get(1).content);
    btnThree.setText(listChoice.get(2).content);
    btnFour.setText(listChoice.get(3).content);
    btnOne.setEnabled(true);
    btnTwo.setEnabled(true);
    btnThree.setEnabled(true);
    btnFour.setEnabled(true);
    btnChangeLevel.setEnabled(false);
    
    btnAdd.setEnabled(false);
  }
  
  void executeButtonNext() {}
  
  void showDialog(String title, String content) {
    String lessonSelecteds = getArguments().getString(KEY_STRING_LESSON_SELECTEDS);
    if (lessonSelecteds != null && !"".equals(lessonSelecteds)) {
      if (getArguments().getBoolean(KEY_ISAPPEND)) {
        FileCommon.writeFile(currentQuestionEntity.getLessonEntity().getLearningTypeEntity().getPath() + FILE_LESSON_LEARNED, lessonSelecteds, true);
      } else {
        FileCommon.writeFile(currentQuestionEntity.getLessonEntity().getLearningTypeEntity().getPath() + FILE_LESSON_LEARNED, lessonSelecteds, false);
      }
    }
    
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
  
  void openEditDialog() {
    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
    // Get the layout inflater
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View view = inflater.inflate(R.layout.dialog_question_update, null);
    final EditText editContent = (EditText) view.findViewById(R.id.content_input);
    final EditText editDisplay = (EditText) view.findViewById(R.id.display_input);
    final EditText editAnswer = (EditText) view.findViewById(R.id.answer_input);
    editContent.setText(currentQuestionEntity.getContent());
    editDisplay.setText(currentQuestionEntity.getDisplay());
    editAnswer.setText(currentQuestionEntity.getAnswer());
    builder.setView(view)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            String content = String.valueOf(editContent.getText()).trim();
            String display = String.valueOf(editDisplay.getText()).trim();
            String answer = String.valueOf(editAnswer.getText()).trim();
            if (!"".equals(content)) {
              currentQuestionEntity.setContent(content);
            }
            if (!"".equals(display)) {
              currentQuestionEntity.setDisplay(display);
            }
            if (!"".equals(answer)) {
              currentQuestionEntity.setAnswer(answer);
            }
            currentQuestionEntity.updateToFile();
            Toast.makeText(getActivity(), "Update Success", Toast.LENGTH_SHORT).show();
            tvConten.setText(currentQuestionEntity.getContent());
            if (!"".equals(tvDisplay.getText())) {
              tvDisplay.setText(currentQuestionEntity.getDisplay());
            }
            
            if (listChoice.get(0).isTrue)
              btnOne.setText(currentQuestionEntity.getAnswer());
            if (listChoice.get(1).isTrue)
              btnTwo.setText(currentQuestionEntity.getAnswer());
            if (listChoice.get(2).isTrue)
              btnThree.setText(currentQuestionEntity.getAnswer());
            if (listChoice.get(3).isTrue)
              btnFour.setText(currentQuestionEntity.getAnswer());
          }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        dialog.cancel();
      }
    });
    builder.create().show();
  }
  
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_add:
        addQuestion();
        btnAdd.setEnabled(false);
        break;
      case R.id.btn_change_level:
        executeButtonChange();
        break;
      case R.id.btn_conirm_next:
        if (btnConirmNext.getText().equals(NEXT_BUTTON)) {
          executeButtonNext();
          btnConirmNext.setText(CONFIRM_BUTTON);
        } else {
          setDisplayControllAfterSelected();
          executeSelected(false);
          
        }
        break;
      case R.id.btn_one:
        if (!clickButton(0)) {
          btnOne.setBackgroundResource(R.drawable.bcg_bt_qs_false);
        }
        break;
      case R.id.btn_two:
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
  
  void executeButtonChange() {
  
  }
  
  void executeButtonAdd() {
  
  }
  
}
