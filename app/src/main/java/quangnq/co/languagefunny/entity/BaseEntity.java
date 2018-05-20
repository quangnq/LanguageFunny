package quangnq.co.languagefunny.entity;

import java.io.Serializable;

/**
 * Created by quang on 4/20/2018.
 */

public class BaseEntity implements Serializable{
  private String id;
  
  private String path;
  
  public BaseEntity() {
  }
  
  public BaseEntity(String id, String path) {
    this.id = id;
    this.path = path;
  }
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getPath() {
    return path;
  }
  
  public void setPath(String path) {
    this.path = path;
  }
}
