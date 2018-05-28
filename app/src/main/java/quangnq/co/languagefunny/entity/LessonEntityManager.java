package quangnq.co.languagefunny.entity;

import java.io.Serializable;
import java.util.ArrayList;

import quangnq.co.languagefunny.common.FileCommon;
import quangnq.co.languagefunny.common.JICommon;

/**
 * Created by QuangNQ on 2018/04/27.
 */

public class LessonEntityManager extends ArrayList<LessonEntity> implements JICommon, Serializable {

    public static LessonEntityManager createListEntity(LearningTypeEntity learningTypeEntity) {
        LessonEntityManager lessonEntities = new LessonEntityManager();
        ArrayList<String> list = FileCommon.getListFolderName(learningTypeEntity.getPath());
        LessonEntity entity;
        for (String id : list) {
            entity = new LessonEntity(id, learningTypeEntity.getPath() + "/" + id, learningTypeEntity);
            lessonEntities.add(entity);
        }
        return lessonEntities;
    }
}
