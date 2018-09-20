package quangnq.co.languagefunny.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import quangnq.co.languagefunny.R;
import quangnq.co.languagefunny.entity.BaseEntity;
import quangnq.co.languagefunny.entity.LessonEntity;

/**
 * Created by quang on 3/5/2018.
 */

public class LessonAdapter extends ArrayAdapter<LessonEntity> {
  public OnItemAction onItemLessonAction;
  // View lookup cache
  private static class ViewHolder {
    TextView id;
    CheckBox checkBox;
  }
  
  public LessonAdapter(@NonNull Context context, ArrayList data) {
    super(context, R.layout.item_lesson, data);
  }
  
  public interface OnItemAction {
    void onItemShortClick(LessonEntity entity, boolean isChecked);
  }
  
  public void setOnListViewAction(OnItemAction listener) {
    this.onItemLessonAction = listener;
  }
  
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder;
    if (convertView == null) {
      viewHolder = new ViewHolder();
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson, parent, false);
      viewHolder.id = (TextView) convertView.findViewById(R.id.textview);
      viewHolder.checkBox = (CheckBox)  convertView.findViewById(R.id.checkbox);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }
  
    final LessonEntity entity = getItem(position);
    convertView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ViewHolder view = (ViewHolder) v.getTag();
        view.checkBox.setChecked(!view.checkBox.isChecked());
        onItemLessonAction.onItemShortClick(entity, view.checkBox.isChecked());
      }
    });
  
    viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        onItemLessonAction.onItemShortClick(entity, isChecked);
      }
    });
    
    viewHolder.id.setText("Lesson " + (entity.getId() + 1));
    viewHolder.checkBox.setChecked(entity.isChecked());
    return convertView;
  }
}
