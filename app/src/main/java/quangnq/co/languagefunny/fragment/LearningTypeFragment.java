package quangnq.co.languagefunny.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import quangnq.co.languagefunny.R;
import quangnq.co.languagefunny.adapter.BaseAdapter;
import quangnq.co.languagefunny.adapter.LearningTypeAdapter;
import quangnq.co.languagefunny.common.FileCommon;
import quangnq.co.languagefunny.entity.LanguageEntity;
import quangnq.co.languagefunny.entity.LearningTypeEntity;

/**
 * Created by quang on 03/03/2018.
 */
public class LearningTypeFragment extends BaseFragment implements LearningTypeAdapter.OnItemAction {
  private LanguageEntity languageEntity;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setBackFragment(new LanguageFragment());
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_learning_type, container, false);
    TextView title = view.findViewById(R.id.screen_title);
    title.setText(languageEntity.getId() + " " + title.getText());
    return view;
  }
  
  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    
    ListView listView = (ListView) view.findViewById(R.id.listview);
    LearningTypeAdapter adapter = new LearningTypeAdapter(getActivity(),
        createListEntity(FileCommon.getListFolderName(languageEntity.getPath())));
    listView.setAdapter(adapter);
    adapter.setOnListViewAction(this);
  }
  
  @Override
  protected void getParentEntity() {
    languageEntity = (LanguageEntity)getArguments().getSerializable(KEY_ENTITY);
  }
  
  public void onItemShortClick(LearningTypeEntity entity) {
    if (FileCommon.getListFolderName(entity.getPath()).isEmpty() && FileCommon.getListFileName(entity.getPath()).isEmpty()) {
      Toast.makeText(getActivity(),languageEntity.getId() + " " + entity.getId() + " is empty",Toast.LENGTH_SHORT).show();
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putSerializable(KEY_ENTITY, entity);
    forward(new LessonFragment(), bundle);
  }
  
  private ArrayList<LearningTypeEntity> createListEntity(ArrayList<String> listId) {
    ArrayList<LearningTypeEntity> list = new ArrayList<>();
    LearningTypeEntity entity;
    for (String id : listId) {
      entity = new LearningTypeEntity(id, languageEntity.getPath() + "/" + id, languageEntity);
      list.add(entity);
    }
    return list;
  }
}
