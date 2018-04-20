package quangnq.co.languagefunny;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import quangnq.co.languagefunny.fragment.LanguageFragment;

public class MainActivity extends Activity {
  
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
}
