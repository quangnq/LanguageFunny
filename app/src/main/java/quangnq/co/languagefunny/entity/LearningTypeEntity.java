package quangnq.co.languagefunny.entity;

/**
 * Created by quang on 3/18/2018.
 */

public class LearningTypeEntity extends BaseEntity {
  private LanguageEntity languageEntity;
  
  public LearningTypeEntity() {
    super();
  }
  
  public LearningTypeEntity(String id, String path, LanguageEntity languageEntity) {
    super(id, path);
    this.languageEntity = languageEntity;
  }
  
  public LanguageEntity getLanguageEntity() {
    return languageEntity;
  }
  
  public void setLanguageEntity(LanguageEntity languageEntity) {
    this.languageEntity = languageEntity;
  }
}
