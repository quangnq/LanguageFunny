package quangnq.co.languagefunny.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import quangnq.co.languagefunny.MainActivity;
import quangnq.co.languagefunny.common.JICommon;
import quangnq.co.languagefunny.entity.BaseEntity;

/**
 * Created by quang on 4/20/2018.
 */

public class BaseFragment<E extends BaseEntity> extends Fragment implements JICommon {
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      getParentEntity();
    }
  }
  
  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }
  
  protected void getParentEntity(){};
  
  /**
   * Forward to next fragment
   *
   * @param fragment
   */
  protected void forward(BaseFragment fragment, Bundle bundle) {
    fragment.setArguments(bundle);
    forward(fragment);
  }
  
  /**
   * Forward to next fragment
   *
   * @param fragment
   */
  protected void forward(BaseFragment fragment) {
    getActivity().onAttachFragment(fragment);
  }
  
  MainActivity getMainActivity() {
    return (MainActivity) getActivity();
  }
  
  void setBackFragment(BaseFragment fragment){
    getMainActivity().setBackFragment(fragment);
  }
  
  BaseFragment getBackFragment() {
    return getMainActivity().getBackFragment();
  }
}
