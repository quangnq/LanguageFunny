package quangnq.co.languagefunny.entity;

import java.util.ArrayList;

import quangnq.co.languagefunny.common.JICommon;

/**
 * Created by quang on 5/19/2018.
 */

public class KanjiQuestionEntity extends QuestionEntity implements JICommon {
  
  ArrayList<KanjiSampleQuestionEntity> listSample = new ArrayList<>();
  
  public KanjiQuestionEntity(String id, String content, String answer, String display,
      int isDifficult, int isSave, int numberAgain, String path, LessonEntity lessonEntity) {
    super(id, content, answer, display, isDifficult, isSave, numberAgain, path, lessonEntity);
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
  
  public ArrayList<KanjiSampleQuestionEntity> getListSample() {
    return listSample;
  }
  
  public void setListSample(ArrayList<KanjiSampleQuestionEntity> listSample) {
    this.listSample = listSample;
  }
}
