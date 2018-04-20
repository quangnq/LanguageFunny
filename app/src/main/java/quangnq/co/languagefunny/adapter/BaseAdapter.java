package quangnq.co.languagefunny.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import quangnq.co.languagefunny.entity.BaseEntity;

/**
 * Created by quang on 4/20/2018.
 */

public class BaseAdapter<E extends BaseEntity> extends ArrayAdapter<E> {
  public OnItemAction onItemAction;
  
  public BaseAdapter(@NonNull Context context, @LayoutRes int resource,
      @NonNull ArrayList<E> objects) {
    super(context, resource, objects);
  }
  
  public interface OnItemAction<E extends BaseEntity> {
    void onItemShortClick(E e);
  }
  
  public void setOnListViewAction(OnItemAction listener) {
    this.onItemAction = listener;
  }
}
