package quangnq.co.languagefunny.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by quang on 4/20/2018.
 */

public interface JICommon {
  String KEY_PATH = "path";
  String KEY_PARENT = "parent";
  
  String PATH_LANGUAGE_FUNNY = "/sdcard/LanguageFunny";
  
  String KEY_LIST_QUESTION = "list_question_entity";
  
  /* japanese */
//  String[] ARRAY_JAPANESE_LEARNING_TYPE = {"vocabulary", "kanji", "listening"};
//  String PATH_JAPANESE = PATH_LANGUAGE_FRIENDLY + ARRAY_LANGUAGE[0] + SLASH;
//  String PATH_VOCABULARY = PATH_JAPANESE + ARRAY_JAPANESE_LEARNING_TYPE[0] + SLASH;
//  String PATH_KANJI = PATH_JAPANESE + ARRAY_JAPANESE_LEARNING_TYPE[1] + SLASH;
//  String PATH_LISTENING = PATH_JAPANESE + ARRAY_JAPANESE_LEARNING_TYPE[2] + SLASH;
  String FOLDER_LISTENING_QUESTION = "Question";
  String FOLDER_LISTENING_ANSWER = "Answer";
  String FILE_LISTENING_TEXTSCRIPT = "Textscript.txt";
  
  int SAVE = 1;
  int NOT_SAVE = 0;
  
  int DIFFICULT = 1;
  int EASY = 0;
  
  int MAX_NUMBER_AGAIN = 6;
  int MIN_NUMBER_AGAIN = 1;
}
