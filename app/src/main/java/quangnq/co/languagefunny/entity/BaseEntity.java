package quangnq.co.languagefunny.entity;

/**
 * Created by quang on 4/20/2018.
 */

public class BaseEntity {
  private String id;
  
  public BaseEntity() {
  }
  
  public BaseEntity(String id) {
    this.id = id;
  }
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
}
