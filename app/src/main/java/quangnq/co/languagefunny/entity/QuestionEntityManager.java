package quangnq.co.languagefunny.entity;

import java.io.Serializable;
import java.util.ArrayList;

import quangnq.co.languagefunny.common.FileCommon;
import quangnq.co.languagefunny.common.JICommon;

/**
 * Created by QuangNQ on 2018/04/23.
 */

public class QuestionEntityManager extends ArrayList<QuestionEntity> implements JICommon, Serializable {
    
    public String createEntityListFromLessons(ArrayList<LessonEntity> lessonEntities) {
        ArrayList<String> list;
        String[] arrs;
        QuestionEntity questionEntity;
        for (LessonEntity lessonEntity : lessonEntities) {
            list = FileCommon.readFile(lessonEntity.getPath());
            for (String s : list) {
                arrs = s.split("_");
                if (arrs.length != 7) {
                    return "Error " + lessonEntity.getId() + " : " + arrs[0].trim();
                } else {
                    questionEntity = new QuestionEntity(arrs[0].trim(), arrs[1].trim(),
                        arrs[2].trim(), arrs[3].trim(), Integer.parseInt(arrs[4].trim()), Integer.parseInt(arrs[5].trim()), Integer.parseInt(arrs[6].trim()), lessonEntity.getPath());
                    if (this.isContain(questionEntity) == -1) {
                        if (lessonEntity.isChecked() || questionEntity.getIsSave() == SAVE || questionEntity.getNumberAgain() > MIN_NUMBER_AGAIN ) {
                            this.add(questionEntity);
                        }
                    }
                }
            }
        }
        if (this.isEmpty()) {
            return "empty : please choose lesson";
        }
        return "success : execute test";
    }
    
    public ArrayList<String> createStringList() {
        ArrayList<String> list = new ArrayList<>();
        for (QuestionEntity entity : this) {
            list.add(entity.createString());
        }
        return list;
    }
    
    public int isContain(QuestionEntity entity) {
        for (int i = 0; i < this.size(); i++ ) {
            if(entity.getId().equals(this.get(i).getId())) {
                return i;
            }
        }
        return -1;
    }
}
