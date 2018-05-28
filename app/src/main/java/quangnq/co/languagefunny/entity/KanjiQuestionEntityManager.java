package quangnq.co.languagefunny.entity;

import android.util.Log;

import java.util.ArrayList;

import quangnq.co.languagefunny.common.FileCommon;
import quangnq.co.languagefunny.common.JICommon;

/**
 * Created by quang on 5/19/2018.
 */

public class KanjiQuestionEntityManager extends ArrayList<KanjiQuestionEntity> implements JICommon {
  
  public String createEntityListFromLessons(LessonEntityManager lessonEntities) {
    ArrayList<String> list;
    String[] arrs;
    KanjiQuestionEntity questionEntity;
    for (LessonEntity lessonEntity : lessonEntities) {
      list = FileCommon.readFile(lessonEntity.getPath());
      for (String s : list) {
        arrs = s.split("_");
        if (arrs.length == LENGTH_ARR_KANJI_QUESTION && arrs[0].length() == LENGTH_ID_QUESTION) {
          questionEntity = new KanjiQuestionEntity(arrs[0].trim(), arrs[1].trim(), arrs[2].trim(),
                  arrs[3].trim(), arrs[4].trim(), Integer.parseInt(arrs[5].trim()), Integer.parseInt(arrs[6].trim()),
                  Integer.parseInt(arrs[7].trim()), lessonEntity.getPath() + "/" + arrs[0].trim(), lessonEntity);
          if (this.isContain(questionEntity) == -1) {
            if (lessonEntity.isChecked() || questionEntity.getIsSave() == SAVE || questionEntity.getNumberAgain() > MIN_NUMBER_AGAIN) {
//              questionEntity.createListSample(list);
              this.add(questionEntity);
            }
          }
        } else if (arrs.length == LENGTH_ARR_VOCABULARY_QUESTION && arrs[0].length() == LENGTH_ID_KANJI_SAMPLE_QUESTION) {

        } else {
          return "Error " + lessonEntity.getId() + " : " + arrs[0].trim();
        }
      }
    }
    if (this.isEmpty()) {
      return "empty : please choose lesson";
    }
    return "success : execute test";
  }

  public static ArrayList<String> createListTotalKun(LessonEntityManager lessonEntities) {
    ArrayList<String> list;
    String[] arrs;
    ArrayList<String> listTotalKun = new ArrayList<>();
    for (LessonEntity lessonEntity : lessonEntities) {
      list = FileCommon.readFile(lessonEntity.getPath());
      for (String s : list) {
        arrs = s.split("_");
        if (arrs.length == LENGTH_ARR_KANJI_QUESTION && arrs[0].length() == LENGTH_ID_QUESTION) {
          arrs = arrs[3].split(";");
          for (int i = 0; i < arrs.length; i++) {
            if (!"".equals(arrs[i].trim())) {
              listTotalKun.add(arrs[i].trim());
              Log.i("createListTotalKun: ", "createListTotalKun: " + arrs[i].trim());
            }
          }
        }
      }
    }
    return listTotalKun;
  }

  public static ArrayList<String> createListTotalOn(LessonEntityManager lessonEntities) {
    ArrayList<String> list;
    String[] arrs;
    ArrayList<String> listTotal = new ArrayList<>();
    for (LessonEntity lessonEntity : lessonEntities) {
      list = FileCommon.readFile(lessonEntity.getPath());
      for (String s : list) {
        arrs = s.split("_");
        if (arrs.length == LENGTH_ARR_KANJI_QUESTION && arrs[0].length() == LENGTH_ID_QUESTION) {
          arrs = arrs[2].split(";");
          for (int i = 0; i < arrs.length; i++) {
            if (!"".equals(arrs[i].trim())) {
              listTotal.add(arrs[i].trim());
              Log.i("createListTotalOn: ", "createListTotalOn: " + arrs[i].trim());
            }
          }
        }
      }
    }
    return listTotal;
  }
  
  public void createEntityListFromLessonEntity(LessonEntity lessonEntity) {
    ArrayList<String> list = FileCommon.readFile(lessonEntity.getPath());
    KanjiQuestionEntity questionEntity;
    String[] arrs;
    for (String s : list) {
      arrs = s.split("_");
      questionEntity = new KanjiQuestionEntity(arrs[0].trim(), arrs[1].trim(), arrs[2].trim(),
          arrs[3].trim(), arrs[4].trim(), Integer.parseInt(arrs[5].trim()), Integer.parseInt(arrs[6].trim()),
          Integer.parseInt(arrs[7].trim()), lessonEntity.getPath() + "/" + arrs[0].trim(), lessonEntity);
      if (this.isContain(questionEntity) == -1) {
        this.add(questionEntity);
      }
    }
  }
  
  public ArrayList<String> createStringList() {
    ArrayList<String> list = new ArrayList<>();
    for (KanjiQuestionEntity entity : this) {
      list.add(entity.createString());
    }
    return list;
  }
  
  public int isContain(KanjiQuestionEntity entity) {
    for (int i = 0; i < this.size(); i++) {
      if (entity.getId().equals(this.get(i).getId())) {
        return i;
      }
    }
    return -1;
  }
}
