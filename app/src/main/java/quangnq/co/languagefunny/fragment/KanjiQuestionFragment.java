package quangnq.co.languagefunny.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import quangnq.co.languagefunny.R;
import quangnq.co.languagefunny.common.FileCommon;
import quangnq.co.languagefunny.entity.Choice;
import quangnq.co.languagefunny.entity.KanjiQuestionEntity;
import quangnq.co.languagefunny.entity.KanjiQuestionEntityManager;
import quangnq.co.languagefunny.entity.KanjiSampleQuestionEntity;
import quangnq.co.languagefunny.entity.LanguageEntity;
import quangnq.co.languagefunny.entity.LearningTypeEntity;
import quangnq.co.languagefunny.entity.LessonEntityManager;
import quangnq.co.languagefunny.entity.QuestionEntity;
import quangnq.co.languagefunny.entity.QuestionEntityManager;

public class KanjiQuestionFragment extends BaseFragment implements View.OnClickListener {
  
  Button btnChangeLevel, btnAdd, btnConirmNext, btnOne, btnTwo, btnThree, btnFour;
  TextView tvClock, tvQuestionTrue, tvQuestionAnswered, tvQuestionSum, tvConten, tvDisplay;
  
  KanjiQuestionEntityManager kanjiQuestionEntityManager = new KanjiQuestionEntityManager();
  QuestionEntity currentQuestionEntity;
  int index;
  
  Button[] kunButtons = new Button[8];
  Button[] onButtons = new Button[8];
  LinearLayout layoutButtonKun, layoutButtonOn, layoutKanjiQuestion, layoutKanjiQuestionSample, layoutTotal;
  
  int indexKanjiSample;
  boolean isKanjiSampleQuestion;
  ArrayList<KanjiSampleQuestionEntity> listKanjiSample = new ArrayList<>();
  ArrayList<Choice> listChoice = new ArrayList<>();
  ArrayList<String> listTotalKun = new ArrayList<>();
  ArrayList<String> listTotalOn = new ArrayList<>();
  ArrayList<String> buttonTexts = new ArrayList<>();
  private QuestionEntityManager questionEntities = new QuestionEntityManager();
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_demo, container, false);
    layoutKanjiQuestion = (LinearLayout) view.findViewById(R.id.kanji_question);
    layoutKanjiQuestionSample = (LinearLayout) view.findViewById(R.id.kanji_question_sample);
    layoutTotal = (LinearLayout) view.findViewById(R.id.layout_total);
    
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
        if (isKanjiSampleQuestion) {
          openEditDialog();
        } else {
          if (btnConirmNext.getText().toString().equals(NEXT_BUTTON)) {
            openEditDialogKanji();
          }
        }
        
        return true;
      }
    });
    
    tvDisplay.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View view) {
        if (isKanjiSampleQuestion && btnConirmNext.getText().toString().equals(NEXT_BUTTON)) {
          openDeleteDialog("Confirm", "You want to delete this question ???");
          
        }
        return true;
      }
    });
    return view;
  }
  
  @Override
  public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    layoutButtonKun = view.findViewById(R.id.layout_buton_kun);
    layoutButtonOn = view.findViewById(R.id.layout_buton_on);
    layoutKanjiQuestionSample.setVisibility(View.GONE);
    layoutKanjiQuestion.setVisibility(View.VISIBLE);
    newButtons(layoutButtonOn, onButtons);
    newButtons(layoutButtonKun, kunButtons);
    
    initial();
    
    view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        createKunButtons(layoutButtonOn, onButtons);
        createKunButtons(layoutButtonKun, kunButtons);
      }
    });
    Bundle bundle = new Bundle();
    bundle.putSerializable(KEY_ENTITY, currentQuestionEntity.getLessonEntity().getLearningTypeEntity());
    LessonFragment lessonFragment = new LessonFragment();
    lessonFragment.setArguments(bundle);
    setBackFragment(lessonFragment);
  }
  
  void newButtons(LinearLayout layoutButtons, final Button[] buttons) {
    for (int i = 0; i < buttons.length; i++) {
      buttons[i] = new Button(getMainActivity());
      buttons[i].setId(i);
      buttons[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
      buttons[i].setTextSize(14);
      buttons[i].setTypeface(null, Typeface.BOLD);
      layoutButtons.addView(buttons[i]);
      final int j = i;
      buttons[i].setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (buttons[j].isActivated()) {
            buttons[j].setActivated(false);
            buttons[j].setBackgroundResource(R.drawable.custom_button_default);
          } else {
            buttons[j].setActivated(true);
            buttons[j].setBackgroundResource(R.drawable.custom_button_seleted);
          }
        }
      });
    }
  }
  
  void initial() {
    kanjiQuestionEntityManager.clear();
    Toast.makeText(getActivity(), kanjiQuestionEntityManager.createEntityListFromLessons((LessonEntityManager) getArguments().getSerializable(KEY_LIST_LESSON_SELECTED))
        , Toast.LENGTH_SHORT).show();
    Collections.shuffle(kanjiQuestionEntityManager);
    listTotalOn = KanjiQuestionEntityManager.createListTotalOn((LessonEntityManager) getArguments().getSerializable(KEY_LIST_LESSON_SELECTED));
    listTotalKun = KanjiQuestionEntityManager.createListTotalKun((LessonEntityManager) getArguments().getSerializable(KEY_LIST_LESSON_SELECTED));
    index = 0;
    tvQuestionSum.setText(Integer.toString(kanjiQuestionEntityManager.size()));
    tvQuestionAnswered.setText("0");
    tvQuestionTrue.setText("0");
    
    LanguageEntity languageEntity = new LanguageEntity("Japanese", PATH_LANGUAGE_FUNNY + "/Japanese");
    questionEntities.createAllQuestion(new LearningTypeEntity("Vocabulary", languageEntity.getPath() + "/Vocabulary", languageEntity));
    
    display();
  }
  
  void setButtons(Button[] buttons) {
    for (int i = 0; i < buttons.length; i++) {
      buttons[i].setText(buttonTexts.get(i));
      buttons[i].setTextColor(Color.WHITE);
      buttons[i].setBackgroundResource(R.drawable.custom_button_default);
      buttons[i].setActivated(false);
      buttons[i].setEnabled(true);
    }
  }
  
  void createKunButtons(LinearLayout layoutButtons, Button[] buttons) {
    ArrayList<Button> listTemp = new ArrayList<>();
    int[] widthButton = new int[buttons.length];
    for (int i = 0; i < buttons.length; i++) {
      widthButton[i] = buttons[i].getWidth();
    }
    layoutButtons.removeAllViews();
    for (int i = 0; i < buttons.length; i++) {
      layoutButtons.removeView(buttons[i]);
    }
    layoutButtons.setOrientation(LinearLayout.VERTICAL);
    int widthSum = layoutButtons.getWidth();
    int width = 0;
    for (int i = 0; i < widthButton.length; i++) {
      Log.i("onCreateView: " + i, "onCreateView: " + widthButton[i]);
      width = width + widthButton[i] + 10;
      
      if (width > widthSum - 10) {
        LinearLayout linearLayout = new LinearLayout(getMainActivity());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(0, 0, 0, 5);
        
        for (int j = 0; j < listTemp.size(); j++) {
          linearLayout.addView(listTemp.get(j));
          Space space = new Space(getMainActivity());
          space.setLayoutParams(new LinearLayout.LayoutParams(10, 0));
          if (j < listTemp.size() - 1) {
            linearLayout.addView(space);
          }
        }
        
        layoutButtons.addView(linearLayout);
        width = widthButton[i] + 5;
        listTemp.clear();
        listTemp.add(buttons[i]);
      } else {
        listTemp.add(buttons[i]);
      }
    }
    
    if (width > 0) {
      LinearLayout linearLayout = new LinearLayout(getMainActivity());
      linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
      linearLayout.setOrientation(LinearLayout.HORIZONTAL);
      linearLayout.setPadding(0, 0, 0, 5);
      for (int j = 0; j < listTemp.size(); j++) {
        linearLayout.addView(listTemp.get(j));
        Space space = new Space(getMainActivity());
        space.setLayoutParams(new LinearLayout.LayoutParams(10, 0));
        if (j < listTemp.size() - 1) {
          linearLayout.addView(space);
        }
      }
      layoutButtons.addView(linearLayout);
    }
  }
  
  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_add:
        addQuestion();
        btnAdd.setEnabled(false);
        break;
      case R.id.btn_change_level:
        break;
      case R.id.btn_conirm_next:
        if (btnConirmNext.getText().equals(NEXT_BUTTON)) {
          executeButtonNext();
        } else {
          if (isKanjiSampleQuestion) {
            setDisplayControllAfterSelected();
            executeSelected(false);
          } else {
            setDisplayControllAfterSelected();
          }
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
  
  void setDisplayControllAfterSelected() {
    if (isKanjiSampleQuestion) {
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
      
    } else {
      ArrayList<String> list = new ArrayList<>(((KanjiQuestionEntity) currentQuestionEntity).getListOnyomi());
      boolean check = checkSelected(list, onButtons);
      list = new ArrayList<>(((KanjiQuestionEntity) currentQuestionEntity).getListKunyomi());
      check = checkSelected(list, kunButtons) && check;
      executeSelected(check);
    }
    tvDisplay.setText(currentQuestionEntity.getDisplay()
        + "_" + currentQuestionEntity.getId());
    btnChangeLevel.setEnabled(true);
    btnConirmNext.setText(NEXT_BUTTON);
    
  }
  
  boolean checkSelected(ArrayList<String> list, Button[] buttons) {
    ArrayList<String> temp = new ArrayList<>(list);
    int count = 0;
    for (int i = 0; i < buttons.length; i++) {
      if (buttons[i].isActivated()) {
        count++;
      }
    }
    for (int i = 0; i < buttons.length; i++) {
      String s = buttons[i].getText().toString().trim();
      for (int j = 0; j < list.size(); j++) {
        if (s.equals(list.get(j).trim())) {
          if (buttons[i].isActivated()) {
            list.remove(j);
          }
          buttons[i].setTextColor(Color.RED);
          break;
        }
      }
      buttons[i].setEnabled(false);
    }
    if (count == temp.size() && list.isEmpty()) {
      return true;
    }
    return false;
  }
  
  void executeSelected(boolean isChoosed) {
    if (isKanjiSampleQuestion) {
      if (!isChoosed) {
        if (currentQuestionEntity.getNumberAgain() != MAX_NUMBER_AGAIN) {
          currentQuestionEntity.setNumberAgain(MAX_NUMBER_AGAIN);
          currentQuestionEntity.updateToFile();
        }
        
        listKanjiSample.add(listKanjiSample.get(indexKanjiSample));
      } else {
        if (currentQuestionEntity.getNumberAgain() > MIN_NUMBER_AGAIN) {
          currentQuestionEntity.setNumberAgain(currentQuestionEntity.getNumberAgain() - 1);
          currentQuestionEntity.updateToFile();
        }
      }
      Toast.makeText(getActivity(), "Number Again +" + currentQuestionEntity.getNumberAgain(), Toast.LENGTH_SHORT).show();
      return;
    }
    
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
      ((KanjiQuestionEntity) currentQuestionEntity).updateToFile();
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
  
  void displayKanjiSample() {
    // if index kanji sample >= list kanji Sample then display next kanji Question
    if (indexKanjiSample >= listKanjiSample.size()) {
      index++;
      display();
      return;
    }
    layoutKanjiQuestion.setVisibility(View.GONE);
    layoutKanjiQuestionSample.setVisibility(View.VISIBLE);
    isKanjiSampleQuestion = true;
    // if index kanji sample < list kanji Sample then display next kanji Sample Question
    currentQuestionEntity = listKanjiSample.get(indexKanjiSample);
    for (int i = 0; i < questionEntities.size(); i++) {
      if (questionEntities.get(i).getId().equals(currentQuestionEntity.getId())) {
        questionEntities.remove(i);
        break;
      }
    }
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
  
  void display() {
    if (index >= kanjiQuestionEntityManager.size()) {
      showDialog("You are finished those Lesson", "You are continue test");
      return;
    }
    layoutKanjiQuestion.setVisibility(View.VISIBLE);
    layoutKanjiQuestionSample.setVisibility(View.GONE);
    isKanjiSampleQuestion = false;
    indexKanjiSample = -1;
    KanjiQuestionEntity currentKanji = kanjiQuestionEntityManager.get(index);
    
    currentKanji.createListSample(questionEntities);
    initialButton(onButtons, currentKanji.getListOnyomi(), listTotalOn);
    initialButton(kunButtons, currentKanji.getListKunyomi(), listTotalKun);
    tvConten.setText(currentKanji.getContent() + " (" + currentKanji.getListOnyomi().size()
        + ", " + currentKanji.getListKunyomi().size() + ")");
    
    listKanjiSample.clear();
    listKanjiSample.addAll(currentKanji.getListSample());
    Collections.shuffle(listKanjiSample);
    currentQuestionEntity = currentKanji;
    
    ArrayList<KanjiQuestionEntity> listTemp = new ArrayList<>(kanjiQuestionEntityManager);
    Collections.shuffle(listTemp);
    
    tvQuestionSum.setText(Integer.toString(kanjiQuestionEntityManager.size()));
    btnChangeLevel.setEnabled(false);
    tvDisplay.setText("");
    btnAdd.setEnabled(false);
  }
  
  void initialButton(Button[] buttons, ArrayList<String> listOnKun, ArrayList<String> listTotal) {
    buttonTexts.clear();
    for (String s : listOnKun) {
      buttonTexts.add(s);
    }
    for (String s : listTotal) {
      if (buttonTexts.size() < buttons.length) {
        boolean check = true;
        for (String text : buttonTexts) {
          if (s.trim().equals(text.trim())) {
            check = false;
            break;
          }
        }
        if (check) {
          buttonTexts.add(s.trim());
        }
      } else {
        break;
      }
    }
    Collections.shuffle(buttonTexts);
    setButtons(buttons);
  }
  
  void addQuestion() {
    if (isKanjiSampleQuestion) {
      listKanjiSample.add((KanjiSampleQuestionEntity) currentQuestionEntity);
      return;
    }
    
    kanjiQuestionEntityManager.add((KanjiQuestionEntity) currentQuestionEntity);
    tvQuestionSum.setText(Integer.toString(kanjiQuestionEntityManager.size()));
    listKanjiSample.clear();
    
    if (currentQuestionEntity.getNumberAgain() != MAX_NUMBER_AGAIN) {
      currentQuestionEntity.setNumberAgain(MAX_NUMBER_AGAIN);
      currentQuestionEntity.setIsSave(SAVE);
      ((KanjiQuestionEntity) currentQuestionEntity).updateToFile();
    }
    Toast.makeText(getActivity(), "Number Again +" + currentQuestionEntity.getNumberAgain(), Toast.LENGTH_SHORT).show();
  }
  
  void showDialog(String title, String content) {
    String lessonSelecteds = getArguments().getString(KEY_STRING_LESSON_SELECTEDS);
    if (lessonSelecteds != null && !"".equals(lessonSelecteds)) {
      if (getArguments().getBoolean(KEY_ISAPPEND)) {
        FileCommon.writeFile(kanjiQuestionEntityManager.get(0).getLessonEntity().getLearningTypeEntity().getPath() + FILE_LESSON_LEARNED, lessonSelecteds, true);
      } else {
        FileCommon.writeFile(kanjiQuestionEntityManager.get(0).getLessonEntity().getLearningTypeEntity().getPath() + FILE_LESSON_LEARNED, lessonSelecteds, false);
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
  
  boolean clickButton(int numberClick) {
    setDisplayControllAfterSelected();
    if (listChoice.get(numberClick).isTrue) {
      executeSelected(true);
      return true;
    }
    executeSelected(false);
    return false;
  }
  
  void executeButtonNext() {
    indexKanjiSample++;
    btnConirmNext.setText(CONFIRM_BUTTON);
    displayKanjiSample();
  }
  
  void setDisplayControllBeforeSelect() {
    if (isKanjiSampleQuestion) {
      for (int i = listChoice.size(); i < 4; i++) {
        listChoice.add(new Choice("choice sample", false));
      }
      Collections.shuffle(listChoice);
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
    }
    
    btnChangeLevel.setEnabled(false);
    tvDisplay.setText("");
    btnAdd.setEnabled(false);
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
  
  void openEditDialogKanji() {
    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
    // Get the layout inflater
    final KanjiQuestionEntity entity = (KanjiQuestionEntity) currentQuestionEntity;
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View view = inflater.inflate(R.layout.dialog_kanji_update, null);
    final EditText editContent = (EditText) view.findViewById(R.id.content_input);
    final EditText editDisplay = (EditText) view.findViewById(R.id.display_input);
    final EditText editOnyomi = (EditText) view.findViewById(R.id.on_input);
    final EditText editKunyomi = (EditText) view.findViewById(R.id.kun_input);
    editContent.setText(currentQuestionEntity.getContent());
    editDisplay.setText(currentQuestionEntity.getDisplay());
    editOnyomi.setText(entity.createStringFromArray(entity.getListOnyomi()));
    editKunyomi.setText(entity.createStringFromArray(entity.getListKunyomi()));
    builder.setView(view)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            String content = String.valueOf(editContent.getText()).trim();
            String display = String.valueOf(editDisplay.getText()).trim();
            String onyomi = String.valueOf(editOnyomi.getText()).trim();
            String kunyomi = String.valueOf(editKunyomi.getText()).trim();
            if (!"".equals(content)) {
              entity.setContent(content);
            }
            if (!"".equals(display)) {
              entity.setDisplay(display);
            }
            entity.createListOnyomi(onyomi);
            entity.createListKunyomi(kunyomi);
            entity.updateToFile();
            Toast.makeText(getActivity(), "Update Success", Toast.LENGTH_SHORT).show();
            tvConten.setText(currentQuestionEntity.getContent());
            if (!"".equals(tvDisplay.getText())) {
              tvDisplay.setText(currentQuestionEntity.getDisplay());
            }
          }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        dialog.cancel();
      }
    });
    builder.create().show();
  }
  
  private void openDeleteDialog(String title, String content) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
    builder.setTitle(title);
    builder.setMessage(content);
    builder.setCancelable(false);
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        currentQuestionEntity.deleteQuestionToFile();
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
}
