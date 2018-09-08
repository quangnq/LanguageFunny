package quangnq.co.languagefunny.entity;

/**
 * Created by quang on 9/8/2018.
 */

public class EnglishVocabuaryEntity extends BaseEntity {
  
  private String content;
  private String kindword;
  private String answer;
  private String display;
  private int isSave;
  private int numberAgain;
  private String soundID;
  private LessonEntity lessonEntity;
  
  public EnglishVocabuaryEntity(String question, String path, LessonEntity lessonEntity){
  
  }

}
