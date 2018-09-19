package quangnq.co.languagefunny.entity;

import java.io.Serializable;

/**
 * Created by quang on 3/18/2018.
 */

public class LessonEntity {
  private int id;
  
  private String path;
  
  private boolean isChecked;
  
  private LearningTypeEntity learningTypeEntity;
  
  public LessonEntity() {}
  
  public LessonEntity(int id, String path, LearningTypeEntity learningTypeEntity) {
    this.id = id;
    this.path = path;
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
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public String getPath() {
    return path;
  }
  
  public void setPath(String path) {
    this.path = path;
  }
  
}
