package quangnq.co.languagefunny;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import quangnq.co.languagefunny.fragment.BaseFragment;
import quangnq.co.languagefunny.fragment.LanguageFragment;

public class MainActivity extends Activity {
  BaseFragment backFragment;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    onAttachFragment(new LanguageFragment());
    
  }
  
  @Override
  public void onAttachFragment(Fragment fragment){
    super.onAttachFragment(fragment);
    getFragmentManager().
        beginTransaction()
        .replace(R.id.fragment_switch, fragment)
        .commit();
  }
  
  public void setBackFragment(BaseFragment baseFragment) {
    backFragment = baseFragment;
  }
  
  public BaseFragment getBackFragment() {
    return backFragment;
  }
  
  @Override
  public void onBackPressed() {
    if (backFragment == null) {
      finishAndRemoveTask();
      return;
    }
    onAttachFragment(backFragment);
  }
}
