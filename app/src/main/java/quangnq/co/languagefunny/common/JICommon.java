package quangnq.co.languagefunny.common;

/**
 * Created by quang on 4/20/2018.
 */

public interface JICommon {
  String KEY_ENTITY = "entity";
  String KEY_ISAPPEND = "isAppend";
  String KEY_STRING_LESSON_SELECTEDS = "lessonSelecteds";
  String KEY_LIST_LESSON_SELECTED = "list_lesson_selected";
  
  String PATH_LANGUAGE_FUNNY = "/sdcard/LanguageFunny";
  String FILE_LESSON_LEARNED = "/0/learned.txt";
  
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
  
  int LENGTH_ID_QUESTION = 7;
  int LENGTH_ID_KANJI_SAMPLE_QUESTION = 10;
  
  String CONFIRM_BUTTON = "Confirm";
  String NEXT_BUTTON = "Next";
}
