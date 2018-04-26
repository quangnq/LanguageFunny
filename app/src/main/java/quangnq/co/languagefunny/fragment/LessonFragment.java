package quangnq.co.languagefunny.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import quangnq.co.languagefunny.R;
import quangnq.co.languagefunny.adapter.LessonAdapter;
import quangnq.co.languagefunny.common.FileCommon;
import quangnq.co.languagefunny.entity.LessonEntity;
import quangnq.co.languagefunny.entity.QuestionEntity;

/**
 * Created by quang on 03/03/2018.
 */

public class LessonFragment extends BaseFragment<LessonEntity> implements LessonAdapter.OnItemAction {
  ArrayList<LessonEntity> listEntity = new ArrayList<>();
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_lesson, container, false);
    TextView title = view.findViewById(R.id.screen_title);
    title.setText(parent + " " + title.getText());
    return view;
  }
  
  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ListView listView = (ListView) view.findViewById(R.id.listview);
    Button btnEnter = (Button) view.findViewById(R.id.btn_enter);
    
    LessonAdapter adapter;
    if (path.contains("/Listen")) {
      adapter = new LessonAdapter(getActivity(), createListEntity(FileCommon.getListFolderName(path)));
    } else {
      adapter = new LessonAdapter(getActivity(), createListEntity(FileCommon.getListFileName(path)));
    }
    listView.setAdapter(adapter);
    adapter.setOnListViewAction(this);
  
    btnEnter.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View v) {
        ArrayList<LessonEntity> list = new ArrayList<>();
        for (LessonEntity entity : listEntity) {
          if (entity.isChecked()) {
            entity.setPath(path + "/" + entity.getId());
            list.add(entity);
          }
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("list_entity", list);
        forward(new QuestionFragment(), bundle);
      }
    });
  }
  
  private void createQuestion () {
    ArrayList<QuestionEntity> questionEntities = new ArrayList<>();
    QuestionEntity questionEntity;
    ArrayList<String> list = new ArrayList<>();
    for (LessonEntity entity : listEntity) {
      list.addAll(FileCommon.readFile(path + "/" + entity.getId()));
    }
    
  }
  
  public void onItemShortClick(LessonEntity entity, boolean isChecked) {
    for (int i = 0; i < listEntity.size(); i++) {
      if (entity.getId().equals(listEntity.get(i).getId())) {
        listEntity.get(i).setChecked(isChecked);
        return;
      }
    }
  }
  
  private ArrayList<LessonEntity> createListEntity(ArrayList<String> list) {
    LessonEntity entity;
    for (String item : list) {
      entity = new LessonEntity(item);
      entity.setPath(path + "/" + entity.getId());
      listEntity.add(entity);
    }
    return listEntity;
  }
  
}
