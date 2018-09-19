package quangnq.co.languagefunny.entity;

import java.util.ArrayList;
import java.util.Collections;
import quangnq.co.languagefunny.common.FileCommon;

public abstract class AQuestionEntity extends BaseEntity {
  
  private LessonEntity lessonEntity;
  private int isSave;
  private int numberAgain;
  
  public abstract String createString();
  
  public void updateToFile() {
    ArrayList<String> list = FileCommon.readFile(this.getLessonEntity().getPath());
    for (int i = 0; i < list.size(); i++) {
      String id = list.get(i).split("_")[0];
      if (this.getId().equals(id)) {
        list.remove(i);
        list.add(this.createString());
        Collections.sort(list);
        FileCommon.writeFile(this.getLessonEntity().getPath(), list, false);
        return;
      }
    }
  }
  
  public void deleteQuestionToFile() {
    ArrayList<String> list = FileCommon.readFile(this.getLessonEntity().getPath());
    for (int i = 0; i < list.size(); i++) {
      String id = list.get(i).split("_")[0];
      if (this.getId().equals(id)) {
        list.remove(i);
        Collections.sort(list);
        FileCommon.writeFile(this.getLessonEntity().getPath(), list, false);
        return;
      }
    }
  }
  
  
  public LessonEntity getLessonEntity() {
    return lessonEntity;
  }
  
  public void setLessonEntity(LessonEntity lessonEntity) {
    this.lessonEntity = lessonEntity;
  }
  
  public int getIsSave() {
    return isSave;
  }
  
  public void setIsSave(int isSave) {
    this.isSave = isSave;
  }
  
  public int getNumberAgain() {
    return numberAgain;
  }
  
  public void setNumberAgain(int numberAgain) {
    this.numberAgain = numberAgain;
  }
  
}
