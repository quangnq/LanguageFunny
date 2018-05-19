package quangnq.co.languagefunny.entity;

import java.util.ArrayList;

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
    private String pathLesson;
    
    public QuestionEntity(String id, String content, String answer, String display, int isDifficult, int isSave, int numberAgain, String pathLesson) {
        super(id);
        this.content = content;
        this.answer = answer;
        this.display = display;
        this.isDifficult = isDifficult;
        this.isSave = isSave;
        this.numberAgain = numberAgain;
        this.pathLesson = pathLesson;
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
    
    public String getPathLesson() {
        return pathLesson;
    }
    
    public void setPathLesson(String pathLesson) {
        this.pathLesson = pathLesson;
    }
    
    public void updateToFile() {
        ArrayList<String> list = FileCommon.readFile(this.getPathLesson());
        ArrayList<String> listClone = new ArrayList<>(list);
        for (int i = 0; i < list.size(); i++) {
        
        }
    }
    
    public String createString () {
        return this.getId() + "_" + this.content + "_" + this.answer + "_" + this.display + "_" + this.isDifficult + "_" + this.isSave + "_" + this.numberAgain;
    }
}
