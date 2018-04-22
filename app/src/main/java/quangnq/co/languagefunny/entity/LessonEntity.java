package quangnq.co.languagefunny.entity;

/**
 * Created by quang on 3/18/2018.
 */

public class LessonEntity extends BaseEntity {
  private boolean isChecked;
  
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
}
