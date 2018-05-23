package quangnq.co.languagefunny.entity;

import java.util.ArrayList;

import quangnq.co.languagefunny.common.JICommon;

/**
 * Created by quang on 5/19/2018.
 */

public class KanjiQuestionEntity extends QuestionEntity implements JICommon {
  
  private ArrayList<String> listKunyomi = new ArrayList<>();
  private ArrayList<String> listOnyomi = new ArrayList<>();
  
  private ArrayList<KanjiSampleQuestionEntity> listSample = new ArrayList<>();
  
  public KanjiQuestionEntity(String id, String content, String answer, String display,
      int isDifficult, int isSave, int numberAgain, String path, LessonEntity lessonEntity) {
    super(id, content, answer, display, isDifficult, isSave, numberAgain, path, lessonEntity);
  }
  
  public KanjiQuestionEntity(String id, String content, String onyomi, String kunyomi, String display,
      int isDifficult, int isSave, int numberAgain, String path, LessonEntity lessonEntity) {
    
    super(id, content, "", display, isDifficult, isSave, numberAgain, path, lessonEntity);
    createListKunyomi(kunyomi);
    createListOnyomi(onyomi);
  }
  
  public KanjiQuestionEntity(QuestionEntity questionEntity) {
    super(questionEntity.getId(),
        questionEntity.getContent(),
        questionEntity.getAnswer(),
        questionEntity.getDisplay(),
        questionEntity.getIsDifficult(),
        questionEntity.getIsSave(),
        questionEntity.getNumberAgain(),
        questionEntity.getPath(),
        questionEntity.getLessonEntity());
  }
  
  public void createListSample(QuestionEntityManager list) {
    listSample.clear();
    for (QuestionEntity entity : list) {
      if (entity.getId().length() == LENGTH_ID_KANJI_SAMPLE_QUESTION) {
        if (this.getId().equals(entity.getId().substring(0, LENGTH_ID_QUESTION))) {
          listSample.add(new KanjiSampleQuestionEntity(entity));
        }
      }
    }
  }
  
  public void createListOnyomi(String onyomi) {
    listOnyomi.clear();
    if (onyomi == null || "".equals(onyomi)) {
      return;
    }
    
    String[] arr = onyomi.split(";");
    for (int i = 0; i < arr.length; i++) {
      if (!"".equals(arr[i].trim())) {
        listOnyomi.add(arr[i].trim());
      }
    }
  }
  
  public void createListKunyomi(String kunyomi) {
    listKunyomi.clear();
    if (kunyomi == null || "".equals(kunyomi)) {
      return;
    }
    
    String[] arr = kunyomi.split(";");
    for (int i = 0; i < arr.length; i++) {
      if (!"".equals(arr[i].trim())) {
        listKunyomi.add(arr[i].trim());
      }
    }
  }
  
  public ArrayList<KanjiSampleQuestionEntity> getListSample() {
    return listSample;
  }
  
  public void setListSample(ArrayList<KanjiSampleQuestionEntity> listSample) {
    this.listSample = listSample;
  }
  
  public ArrayList<String> getListKunyomi() {
    return listKunyomi;
  }
  
  public ArrayList<String> getListOnyomi() {
    return listOnyomi;
  }
}
