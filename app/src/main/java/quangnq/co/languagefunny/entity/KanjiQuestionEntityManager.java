package quangnq.co.languagefunny.entity;

import android.util.Log;

import java.util.ArrayList;

import quangnq.co.languagefunny.common.JICommon;

/**
 * Created by quang on 5/19/2018.
 */

public class KanjiQuestionEntityManager extends ArrayList<KanjiQuestionEntity> implements JICommon {
  
  public void initial(QuestionEntityManager questionEntityManager) {
    QuestionEntityManager list;
    for (QuestionEntity entity : questionEntityManager) {
      KanjiQuestionEntity kanjiQuestionEntity;
      if (entity.getId().length() == LENGTH_ID_QUESTION) {
        kanjiQuestionEntity = new KanjiQuestionEntity(entity);
        list = new QuestionEntityManager();
        list.createEntityListFromLessonEntity(entity.getLessonEntity());
        kanjiQuestionEntity.createListSample(list);
        this.add(kanjiQuestionEntity);
      }
    }
  }
}
