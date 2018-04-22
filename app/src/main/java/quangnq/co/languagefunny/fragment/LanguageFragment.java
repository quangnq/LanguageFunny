package quangnq.co.languagefunny.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import quangnq.co.languagefunny.R;
import quangnq.co.languagefunny.adapter.BaseAdapter;
import quangnq.co.languagefunny.adapter.LanguageAdapter;
import quangnq.co.languagefunny.common.FileCommon;
import quangnq.co.languagefunny.entity.LanguageEntity;

/**
 * Created by quang on 4/20/2018.
 */

public class LanguageFragment extends BaseFragment<LanguageEntity> implements BaseAdapter.OnItemAction<LanguageEntity> {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_language, container, false);
    return view;
  }
  
  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    
    ListView listView = (ListView) view.findViewById(R.id.listview);
    LanguageAdapter adapter = new LanguageAdapter(getActivity(), createListEntity(FileCommon.getListFolderName(PATH_LANGUAGE_FUNNY)));
    listView.setAdapter(adapter);
    adapter.setOnListViewAction(this);
  }
  
  @Override
  public void onItemShortClick(LanguageEntity entity) {
    String path = PATH_LANGUAGE_FUNNY + "/" + entity.getId();
    if (FileCommon.getListFolderName(path).isEmpty()) {
      Toast.makeText(getActivity(),entity.getId() + " is empty",Toast.LENGTH_SHORT).show();
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putString(KEY_PATH, path);
    bundle.putString(KEY_PARENT, entity.getId());
    forward(new LearningTypeFragment(), bundle);
  }
  
  private ArrayList<LanguageEntity> createListEntity(ArrayList<String> listId) {
    ArrayList<LanguageEntity> list = new ArrayList<>();
    LanguageEntity entity;
    for (String id : listId) {
      entity = new LanguageEntity(id);
      list.add(entity);
    }
    return list;
  }
}
