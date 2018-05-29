package quangnq.co.languagefunny.entity;

import java.util.ArrayList;
import java.util.Collections;

import quangnq.co.languagefunny.common.FileCommon;
import quangnq.co.languagefunny.common.JICommon;

/**
 * Created by quang on 5/19/2018.
 */

public class KanjiQuestionEntity extends QuestionEntity implements JICommon {
  
  private ArrayList<String> listKunyomi = new ArrayList<>();
  private ArrayList<String> listOnyomi = new ArrayList<>();
  
  private ArrayList<KanjiSampleQuestionEntity> listSample = new ArrayList<>();
  
  public KanjiQuestionEntity(String id, String content, String onyomi, String kunyomi, String display,
      int isDifficult, int isSave, int numberAgain, String path, LessonEntity lessonEntity) {
    super(id, content, "", display, isDifficult, isSave, numberAgain, path, lessonEntity);
    createListKunyomi(kunyomi);
    createListOnyomi(onyomi);
  }

  public void createListSample(ArrayList<String> list) {
    listSample.clear();
    String[] arrs;
    for (String string : list) {
      arrs = string.split("_");
      if (arrs[0].length() == LENGTH_ID_KANJI_SAMPLE_QUESTION) {
        if (this.getId().equals(arrs[0].substring(0, LENGTH_ID_QUESTION))) {

          listSample.add(new KanjiSampleQuestionEntity(arrs[0].trim(), arrs[1].trim(), arrs[2].trim(),
                  arrs[3].trim(), Integer.parseInt(arrs[4].trim()), Integer.parseInt(arrs[5].trim()),
                  Integer.parseInt(arrs[6].trim()), this.getPath() + "/" + arrs[0].trim(), this.getLessonEntity(), this.getId()));
        }
      }
    }
  }

  public void createListSample(QuestionEntityManager questionEntities) {
    listSample.clear();
    KanjiSampleQuestionEntity kanjiSampleQuestionEntity;
    for (QuestionEntity entity : questionEntities) {
      if (entity.getContent().contains(this.getContent().trim()) || entity.getAnswer().contains(this.getContent().trim())) {
        kanjiSampleQuestionEntity = new KanjiSampleQuestionEntity(entity, this.getId());
        listSample.add(kanjiSampleQuestionEntity);
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

  public String createString () {
    return this.getId() + "_" + this.getContent() + "_" + createStringFromArray(this.listOnyomi)
    + "_" + createStringFromArray(this.listKunyomi) + "_" + this.getDisplay() + "_" + this.getIsDifficult() + "_" + this.getIsSave() + "_" + this.getNumberAgain();
  }

  public String createStringFromArray(ArrayList<String> list) {
    String total = "";
    for (int i = 0; i < list.size(); i++ ) {
      if (!"".equals(list.get(i).trim())) {
        total = total + list.get(i).trim();
        if (i < list.size() - 1) {
          total = total + ";";
        }
      }
    }
    return total;
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

    public void setListKunyomi(ArrayList<String> listKunyomi) {
        this.listKunyomi = listKunyomi;
    }

    public void setListOnyomi(ArrayList<String> listOnyomi) {
        this.listOnyomi = listOnyomi;
    }
}
