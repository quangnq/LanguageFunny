package quangnq.co.languagefunny.entity;

import java.io.Serializable;

/**
 * Created by quang on 3/18/2018.
 */

public class LessonEntity extends BaseEntity implements Serializable {
  private boolean isChecked;
  
  private String path;
  
  public LessonEntity() {
    super();
  }
  
  public LessonEntity(String id) {
    super(id);
  }
  
  public boolean isChecked() {
    return isChecked;
  }
  
  public void setChecked(boolean checked) {
    isChecked = checked;
  }
  
  public String getPath() {
    return path;
  }
  
  public void setPath(String path) {
    this.path = path;
  }
}
