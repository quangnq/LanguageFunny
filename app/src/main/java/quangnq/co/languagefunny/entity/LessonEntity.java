package quangnq.co.languagefunny.entity;

import java.io.Serializable;

/**
 * Created by quang on 3/18/2018.
 */

public class LessonEntity extends BaseEntity {
  
  private boolean isChecked;
  
  private LearningTypeEntity learningTypeEntity;
  
  public LessonEntity() {}
  
  public LessonEntity(String id, String path, LearningTypeEntity learningTypeEntity) {
    super(id, path);
    this.learningTypeEntity = learningTypeEntity;
  }
  
  public boolean isChecked() {
    return isChecked;
  }
  
  public void setChecked(boolean checked) {
    isChecked = checked;
  }
  
  public LearningTypeEntity getLearningTypeEntity() {
    return learningTypeEntity;
  }
  
  
}
