package quangnq.co.languagefunny.entity;

import java.util.ArrayList;

import quangnq.co.languagefunny.common.FileCommon;
import quangnq.co.languagefunny.common.JICommon;

/**
 * Created by QuangNQ on 2018/04/23.
 */

public class QuestionEntityManager extends ArrayList<QuestionEntity> implements JICommon {
    
    public String createEntityListFromLessons(ArrayList<LessonEntity> lessonEntities, int isDifficult) {
        ArrayList<String> list;
        String[] arrs;
        String pathLesson;
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
                    boolean isNotExist = true;
                    for (QuestionEntity q : this) {
                        if (questionEntity.getId().equals(q.getId())) {
                            isNotExist = false;
                            break;
                        }
                    }
                    if (isNotExist) {
                        if ((lessonEntity.isChecked() && questionEntity.getIsDifficult() == isDifficult)
                            || questionEntity.getIsSave() == SAVE
                            || questionEntity.getNumberAgain() > MIN_NUMBER_AGAIN )
                            
                        this.add(questionEntity);
                    }
                }
            }
        }
        for (QuestionEntity q : this)
        if (this.isEmpty()) {
            return "empty : please choose lesson";
        }
        return "success : execute test";
    }
}
