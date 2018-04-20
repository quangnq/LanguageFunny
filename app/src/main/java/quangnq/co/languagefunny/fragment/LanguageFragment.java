package quangnq.co.languagefunny.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import quangnq.co.languagefunny.R;
import quangnq.co.languagefunny.adapter.BaseAdapter;
import quangnq.co.languagefunny.adapter.LanguageAdapter;
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
    LanguageAdapter adapter = new LanguageAdapter(getActivity(), createListEntity(ARRAY_LANGUAGE));
    listView.setAdapter(adapter);
    adapter.setOnListViewAction(this);
  }
  
  @Override
  public void onItemShortClick(LanguageEntity languageEntity) {
  
  }
  
  protected ArrayList<LanguageEntity> createListEntity (String[] arrId) {
    ArrayList<LanguageEntity> list = new ArrayList<>();
    LanguageEntity entity;
    for (String id : arrId) {
      entity = new LanguageEntity(id);
      list.add(entity);
    }
    return list;
  }
}
