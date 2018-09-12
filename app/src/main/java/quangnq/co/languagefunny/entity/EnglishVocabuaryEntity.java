package quangnq.co.languagefunny.entity;

import java.util.ArrayList;
import java.util.Collections;

import quangnq.co.languagefunny.common.FileCommon;
/**
 * Created by quang on 9/8/2018.
 */
public class EnglishVocabuaryEntity extends BaseEntity {
  
  private String englishWord;
  private String kindWord;
  private String pronounceWord;
  private String vietnamWord;
  private int isSave;
  private int numberAgain;
  private String soundID;
  private LessonEntity lessonEntity;
  
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
  
  public String createString () {
    return this.getId() + "_" + this.englishWord + "_" + this.kindWord + "_" + this.pronounceWord + "_"
        + this.vietnamWord + "_" + this.isSave + "_" + this.numberAgain + "_" + this.soundID;
  }
  
  public String getEnglishWord() {
    return englishWord;
  }
  
  public void setEnglishWord(String englishWord) {
    this.englishWord = englishWord;
  }
  
  public String getKindWord() {
    return kindWord;
  }
  
  public void setKindWord(String kindWord) {
    this.kindWord = kindWord;
  }
  
  public String getPronounceWord() {
    return pronounceWord;
  }
  
  public void setPronounceWord(String pronounceWord) {
    this.pronounceWord = pronounceWord;
  }
  
  public String getVietnamWord() {
    return vietnamWord;
  }
  
  public void setVietnamWord(String vietnamWord) {
    this.vietnamWord = vietnamWord;
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
  
  public String getSoundID() {
    return soundID;
  }
  
  public void setSoundID(String soundID) {
    this.soundID = soundID;
  }
  
  public LessonEntity getLessonEntity() {
    return lessonEntity;
  }
  
  public void setLessonEntity(LessonEntity lessonEntity) {
    this.lessonEntity = lessonEntity;
  }
}
