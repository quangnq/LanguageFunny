package quangnq.co.languagefunny.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import quangnq.co.languagefunny.R;
import quangnq.co.languagefunny.adapter.LessonAdapter;
import quangnq.co.languagefunny.common.FileCommon;
import quangnq.co.languagefunny.entity.LearningTypeEntity;
import quangnq.co.languagefunny.entity.LessonEntity;
import quangnq.co.languagefunny.entity.LessonEntityManager;

/**
 * Created by quang on 03/03/2018.
 */

public class EnglishLessonFragment extends BaseFragment implements LessonAdapter.OnItemAction {
  LessonEntityManager lessonEntities = new LessonEntityManager();
  LearningTypeEntity learningTypeEntity;
  TextView tvLessonLearned;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle bundle = new Bundle();
    bundle.putSerializable(KEY_ENTITY, learningTypeEntity.getLanguageEntity());
    LearningTypeFragment learningTypeFragment = new LearningTypeFragment();
    learningTypeFragment.setArguments(bundle);
    setBackFragment(learningTypeFragment);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_lesson, container, false);
    TextView title = view.findViewById(R.id.screen_title);
    title.setText(learningTypeEntity.getId() + " " + title.getText());
    tvLessonLearned = view.findViewById(R.id.tv_lesson_learned);
    ArrayList<String> listLessonLearned = FileCommon.readFile(learningTypeEntity.getPath() + FILE_LESSON_LEARNED);
    String lessonLearned = "";
    for (String s : listLessonLearned) {
      lessonLearned = lessonLearned + s + "\n";
    }
    lessonLearned = tvLessonLearned.getText().toString() + lessonLearned;
    tvLessonLearned.setText(lessonLearned);
    return view;
  }
  
  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ListView listView = (ListView) view.findViewById(R.id.listview);
    Button btnEnter = (Button) view.findViewById(R.id.btn_enter);
    
    LessonAdapter adapter;
    if (learningTypeEntity.getPath().contains("/English/Vocabulary/")) {
      String path = learningTypeEntity.getPath() + "vocabulary.txt";
      ArrayList<String> entityStrings = FileCommon.readFile(path);
      int numderLesson = entityStrings.size()/50;
      if (entityStrings.size()%50 > 1) {
        numderLesson++;
      }
      LessonEntity entity;
      for (int i = 0; i < numderLesson; i++) {
        entity = new LessonEntity(String.valueOf(i), path, learningTypeEntity);
        lessonEntities.add(entity);
      }
      adapter = new LessonAdapter(getActivity(), lessonEntities);
      listView.setAdapter(adapter);
      adapter.setOnListViewAction(this);
    }
    
    btnEnter.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showDialog("Confirm", "You want to delete lesson saved???");
      }
    });
  }
  
  @Override
  protected void getParentEntity() {
    learningTypeEntity = (LearningTypeEntity) getArguments().getSerializable(KEY_ENTITY);
  }
  
  public void onItemShortClick(LessonEntity entity, boolean isChecked) {
    for (int i = 0; i < lessonEntities.size(); i++) {
      if (entity.getId().equals(lessonEntities.get(i).getId())) {
        lessonEntities.get(i).setChecked(isChecked);
        return;
      }
    }
  }
  
  private void showDialog(String title, String content) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle(title);
    builder.setMessage(content);
    builder.setCancelable(false);
    builder.setPositiveButton("Append", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        forward(true);
      }
    });
    builder.setNegativeButton("Not Append", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        forward(false);
      }
    });
    AlertDialog alertDialog = builder.create();
    alertDialog.setCanceledOnTouchOutside(true);
    alertDialog.show();
  }
  
  private void forward(boolean isAppend) {
    
    String lessonSelected = "";
    for (LessonEntity entity : lessonEntities) {
      if (entity.isChecked()) {
        lessonSelected = lessonSelected + entity.getId() + ",";
      }
    }
    
    Bundle bundle = new Bundle();
    bundle.putSerializable(KEY_LIST_LESSON_SELECTED, lessonEntities);
    bundle.putBoolean(KEY_ISAPPEND, isAppend);
    bundle.putString(KEY_STRING_LESSON_SELECTEDS, lessonSelected);
    
    if (lessonEntities.get(0).getPath().contains("/English/Vocabulary/")) {
      forward(new EnglishVocabularyFragment(), bundle);
    }
  }
  
  private ArrayList<LessonEntity> createListEntity(ArrayList<String> list) {
    LessonEntity entity;
    for (String id : list) {
      entity = new LessonEntity(id, learningTypeEntity.getPath() + "/" + id, learningTypeEntity);
      lessonEntities.add(entity);
    }
    return lessonEntities;
  }
  
}
