package quangnq.co.languagefunny.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import quangnq.co.languagefunny.R;

/**
 * Created by quang on 4/21/2018.
 */

public class QuestionFragment extends BaseFragment implements View.OnClickListener {
  
  Button btnChangeLevel, btnAdd, btnConirmNext, btnOne, btnTwo, btnThree, btnFour;
  TextView tvClock, tvQuestionTrue, tvQuestionAnswered, tvQuestionSum, tvConten, tvDisplay;
  EditText edtContent, edtDisplay, edtAnswer;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_question, container, false);
    btnChangeLevel = (Button) view.findViewById(R.id.btn_change_level);
    btnAdd = (Button) view.findViewById(R.id.btn_add);
    btnConirmNext = (Button) view.findViewById(R.id.btn_conirm_next);
    tvClock = (TextView) view.findViewById(R.id.tv_clock);
    tvQuestionTrue = (TextView) view.findViewById(R.id.tv_question_true);
    tvQuestionAnswered = (TextView) view.findViewById(R.id.tv_question_answered);
    tvQuestionSum = (TextView) view.findViewById(R.id.tv_question_sum);
    tvConten = (TextView) view.findViewById(R.id.tv_content);
    tvDisplay = (TextView) view.findViewById(R.id.tv_display);
    btnOne = (Button) view.findViewById(R.id.btn_one);
    btnTwo = (Button) view.findViewById(R.id.btn_two);
    btnThree = (Button) view.findViewById(R.id.btn_three);
    btnFour = (Button) view.findViewById(R.id.btn_four);
    
    btnChangeLevel.setOnClickListener(this);
    btnAdd.setOnClickListener(this);
    btnConirmNext.setOnClickListener(this);
    btnOne.setOnClickListener(this);
    btnTwo.setOnClickListener(this);
    btnThree.setOnClickListener(this);
    btnFour.setOnClickListener(this);
    
    return view;
  }
  
  private void initial() {
  
  }
  
  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }
  
  @Override
  public void onClick(View v) {
  
  }
}
