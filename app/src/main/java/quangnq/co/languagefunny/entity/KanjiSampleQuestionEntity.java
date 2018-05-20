package quangnq.co.languagefunny.entity;

import quangnq.co.languagefunny.common.JICommon;

/**
 * Created by quang on 5/19/2018.
 */

public class KanjiSampleQuestionEntity extends QuestionEntity implements JICommon {
  
  String idKanjiQuestion;
  
  public KanjiSampleQuestionEntity(String id, String content, String answer, String display,
      int isDifficult, int isSave, int numberAgain, String path, LessonEntity lessonEntity, String idKanjiQuestion) {
    
    super(id, content, answer, display, isDifficult, isSave, numberAgain, path, lessonEntity);
    this.idKanjiQuestion = idKanjiQuestion;
  }
  
  public KanjiSampleQuestionEntity(QuestionEntity questionEntity) {
    super(questionEntity.getId(),
        questionEntity.getContent(),
        questionEntity.getAnswer(),
        questionEntity.getDisplay(),
        questionEntity.getIsDifficult(),
        questionEntity.getIsSave(),
        questionEntity.getNumberAgain(),
        questionEntity.getPath(),
        questionEntity.getLessonEntity());
    this.idKanjiQuestion = questionEntity.getId().substring(0, LENGTH_ID_QUESTION);
  }
  
  public String getIdKanjiQuestion() {
    return idKanjiQuestion;
  }
  
  public void setIdKanjiQuestion(String idKanjiQuestion) {
    this.idKanjiQuestion = idKanjiQuestion;
  }
}
