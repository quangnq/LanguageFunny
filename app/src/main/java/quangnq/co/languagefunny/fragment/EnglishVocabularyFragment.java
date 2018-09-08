package quangnq.co.languagefunny.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import quangnq.co.languagefunny.entity.Choice;

/**
 * Created by quang on 9/8/2018.
 */

public class EnglishVocabularyFragment extends BaseFragment implements View.OnClickListener  {
  
  Button btnSound, btnAdd, btnConfirmNext, btnOne, btnTwo, btnThree, btnFour;
  TextView tvClock, tvQuestionTrue, tvQuestionAnswered, tvQuestionSum, tvConten, tvDisplay;
  ArrayList<Choice> listChoice = new ArrayList<>();
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
  }
}
