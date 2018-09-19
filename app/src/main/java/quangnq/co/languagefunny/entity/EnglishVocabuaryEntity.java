package quangnq.co.languagefunny.entity;

import java.util.ArrayList;
import java.util.Collections;

import quangnq.co.languagefunny.common.FileCommon;
import quangnq.co.languagefunny.fragment.AQuestionFragment;
/**
 * Created by quang on 9/8/2018.
 */
public class EnglishVocabuaryEntity extends AQuestionEntity {
  
  private String englishWord;
  private String kindWord;
  private String pronounceWord;
  private String vietnamWord;
  private String soundID;
  
  @Override
  public String createString () {
    return this.getId() + "_" + this.englishWord + "_" + this.kindWord + "_" + this.pronounceWord + "_"
        + this.vietnamWord + "_" + this.getIsSave() + "_" + this.getNumberAgain() + "_" + this.soundID;
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
  
  public String getSoundID() {
    return soundID;
  }
  
  public void setSoundID(String soundID) {
    this.soundID = soundID;
  }
  
}
