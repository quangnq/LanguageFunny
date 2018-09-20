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
import quangnq.co.languagefunny.entity.EnglishVocabuaryEntity;
import quangnq.co.languagefunny.entity.LearningTypeEntity;
import quangnq.co.languagefunny.entity.LessonEntity;
import quangnq.co.languagefunny.manager.LessonEntityManager;
import quangnq.co.languagefunny.manager.EnglishVocabuaryEntityManager;

/**
 * Created by quang on 03/03/2018.
 */

public class LessonFragment extends BaseFragment implements LessonAdapter.OnItemAction {
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
    String path = FileCommon.getListFilePath(learningTypeEntity.getPath()).get(0);
    ArrayList<String> entityStrings = FileCommon.readFile(path);
    int numberLesson = entityStrings.size()/50;
    if (entityStrings.size()%50 > 1) {
      numberLesson++;
    }
    LessonEntity entity;
    for (int i = 0; i < numberLesson; i++) {
      entity = new LessonEntity(i, path, learningTypeEntity);
      lessonEntities.add(entity);
    }
    adapter = new LessonAdapter(getActivity(), lessonEntities);
    listView.setAdapter(adapter);
    adapter.setOnListViewAction(this);
    
    
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
      if (entity.getId() == lessonEntities.get(i).getId()) {
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
  
    EnglishVocabuaryEntityManager entities = new EnglishVocabuaryEntityManager();
    ArrayList<String> entityStrings = FileCommon.readFile(PATH_ENGLISH_VOCABULARY + "vocabulary.txt");
    EnglishVocabuaryEntityManager allEntities = new EnglishVocabuaryEntityManager(entityStrings);
    for (LessonEntity lessonEntity : lessonEntities) {
      int position = lessonEntity.getId();
      for (int i = position*50; i < allEntities.size() && i < position*50 + 50; i++) {
        allEntities.get(i).setLessonEntity(lessonEntity);
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
    
    Bundle bundle = new Bundle();
    bundle.putSerializable(KEY_QUESTION_SELECTED, entities);
    
    if (lessonEntities.get(0).getPath().contains("/English/Vocabulary/")) {
      forward(new EnglishVocabularyFragment(), bundle);
    }
  }
}
