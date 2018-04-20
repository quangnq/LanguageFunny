package quangnq.co.languagefunny.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by quang on 4/20/2018.
 */

public interface JICommon {
  String SLASH = "/";
  String KEY_PATH = "path";
  
  String[] ARRAY_LANGUAGE = {"Japanese"};
  String LEVEL_DIFFICULTY = "Difficulty";
  String LEVEL_EASY = "Easy";
  String PATH_LANGUAGE_FUNNY = "/sdcard/LanguageFunny/";
  
  
  
  /* japanese */
//  String[] ARRAY_JAPANESE_LEARNING_TYPE = {"vocabulary", "kanji", "listening"};
//  String PATH_JAPANESE = PATH_LANGUAGE_FRIENDLY + ARRAY_LANGUAGE[0] + SLASH;
//  String PATH_VOCABULARY = PATH_JAPANESE + ARRAY_JAPANESE_LEARNING_TYPE[0] + SLASH;
//  String PATH_KANJI = PATH_JAPANESE + ARRAY_JAPANESE_LEARNING_TYPE[1] + SLASH;
//  String PATH_LISTENING = PATH_JAPANESE + ARRAY_JAPANESE_LEARNING_TYPE[2] + SLASH;
  String FOLDER_LISTENING_QUESTION = "Question";
  String FOLDER_LISTENING_ANSWER = "Answer";
  String FILE_LISTENING_TEXTSCRIPT = "Textscript.txt";
  
  HashMap<String, String[]> MAP_LEARNING_TYPE = new HashMap<String, String[]>(){{
    put(ARRAY_LANGUAGE[0], new String[]{"Vocabulary", "Kanji", "Listening"});
  }};
}
