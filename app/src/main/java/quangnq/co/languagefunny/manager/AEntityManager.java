package quangnq.co.languagefunny.manager;

import java.util.ArrayList;

import quangnq.co.languagefunny.entity.AQuestionEntity;
public abstract class AEntityManager<E extends AQuestionEntity> extends ArrayList<E> {
  
  public int getPosition(String id) {
    for (int i = 0; i < this.size(); i++) {
      if (this.get(i).getId().equals(id)) {
        return i;
      }
    }
    return -1;
  }
  
}
