package quangnq.co.languagefunny.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import quangnq.co.languagefunny.R;
import quangnq.co.languagefunny.entity.BaseEntity;
import quangnq.co.languagefunny.entity.LearningTypeEntity;

/**
 * Created by quang on 3/5/2018.
 */

public class LearningTypeAdapter extends ArrayAdapter<LearningTypeEntity> {
  // View lookup cache
  private static class ViewHolder {
    TextView id;
  }
  
  public LearningTypeAdapter(@NonNull Context context, ArrayList data) {
    super(context, R.layout.fragment_learning_type, data);
  }
  
  public OnItemAction onItemAction;
  
  public interface OnItemAction {
    void onItemShortClick(LearningTypeEntity entity);
  }
  
  public void setOnListViewAction(OnItemAction listener) {
    this.onItemAction = listener;
  }
  
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder;
    if (convertView == null) {
      viewHolder = new ViewHolder();
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learning_type, parent, false);
      viewHolder.id = (TextView) convertView.findViewById(R.id.textview);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }
    final LearningTypeEntity entity = getItem(position);
    viewHolder.id.setText(entity.getId());
    viewHolder.id.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onItemAction.onItemShortClick(entity);
      }
    });
    
    return convertView;
  }
}
