package quangnq.co.languagefunny.entity;

import java.util.ArrayList;
import java.util.Collections;

import quangnq.co.languagefunny.common.FileCommon;

/**
 * Created by quang on 4/21/2018.
 */

public class QuestionEntity extends BaseEntity {
    private String content;
    private String answer;
    private String display;
    private int isDifficult;
    private int isSave;
    private int numberAgain;
    
    private LessonEntity lessonEntity;
    
    public QuestionEntity(String id, String content, String answer, String display,
        int isDifficult, int isSave, int numberAgain, String path, LessonEntity lessonEntity) {
        super(id, path);
        this.content = content;
        this.answer = answer;
        this.display = display;
        this.isDifficult = isDifficult;
        this.isSave = isSave;
        this.numberAgain = numberAgain;
        this.lessonEntity = lessonEntity;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getAnswer() {
        return answer;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    public String getDisplay() {
        return display;
    }
    
    public void setDisplay(String display) {
        this.display = display;
    }
    
    public int getIsDifficult() {
        return isDifficult;
    }
    
    public void setIsDifficult(int isDifficult) {
        this.isDifficult = isDifficult;
    }
    
    public int getIsSave() {
        return isSave;
    }
    
    public void setIsSave(int isSave) {
        this.isSave = isSave;
    }
    
    public int getNumberAgain() {
        return numberAgain;
    }
    
    public void setNumberAgain(int numberAgain) {
        this.numberAgain = numberAgain;
    }
    
    public LessonEntity getLessonEntity() {
        return lessonEntity;
    }
    
    public void setLessonEntity(LessonEntity lessonEntity) {
        this.lessonEntity = lessonEntity;
    }
    
    public void updateToFile() {
        ArrayList<String> list = FileCommon.readFile(this.getLessonEntity().getPath());
        for (int i = 0; i < list.size(); i++) {
            String id = list.get(i).split("_")[0];
            if (this.getId().equals(id)) {
                list.remove(i);
                list.add(this.createString());
                Collections.sort(list);
                FileCommon.writeFile(this.getLessonEntity().getPath(), list, false);
                return;
            }
        }
    }
    
    public String createString () {
        return this.getId() + "_" + this.content + "_" + this.answer + "_" + this.display + "_" + this.isDifficult + "_" + this.isSave + "_" + this.numberAgain;
    }
}
