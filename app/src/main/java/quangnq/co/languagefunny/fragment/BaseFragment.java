package quangnq.co.languagefunny.fragment;


import android.app.Fragment;
import android.os.Bundle;

import java.util.ArrayList;

import quangnq.co.languagefunny.common.JICommon;
import quangnq.co.languagefunny.entity.BaseEntity;

/**
 * Created by quang on 4/20/2018.
 */

public class BaseFragment<E extends BaseEntity> extends Fragment implements JICommon {
  protected String path;
  protected String parent;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      path = getArguments().getString(KEY_PATH);
      parent = getArguments().getString(KEY_PARENT);
    }
  }
  
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
  
}
