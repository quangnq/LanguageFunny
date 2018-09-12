package quangnq.co.languagefunny.manager;

import java.util.ArrayList;
import quangnq.co.languagefunny.entity.EnglishVocabuaryEntity;

public class EnglishVocabuaryEntityManager extends ArrayList<EnglishVocabuaryEntity> {
  
  public void createListFromArrayString(ArrayList<String> strings){
    String[] arr;
    EnglishVocabuaryEntity entity;
    for (String string : strings) {
      arr = string.split("_");
      entity = new EnglishVocabuaryEntity();
      entity.setId(arr[0]);
      entity.setEnglishWord(arr[1]);
      entity.setKindWord(arr[2]);
      entity.setPronounceWord(arr[3]);
      entity.setVietnamWord(arr[4]);
      entity.setIsSave(Integer.parseInt(arr[5]));
      entity.setNumberAgain(Integer.parseInt(arr[6]));
      entity.setSoundID(arr[7]);
      this.add(entity);
    }
  }
}
