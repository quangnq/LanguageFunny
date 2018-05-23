package quangnq.co.languagefunny.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.Toast;

import java.util.ArrayList;

import quangnq.co.languagefunny.R;

public class DemoFragment extends BaseFragment implements View.OnClickListener {
    Button[] kunButtons = new Button[9];
    Button[] onButtons = new Button[9];
    LinearLayout layoutButtonKun;
    LinearLayout layoutButtonOn;
    String[] kunStrings = {"demo 167899065663443", "demo 12", "demo 12367", "demo 1234", "demo 12345", "demo 123456", "demo 123456", "demo 123456", "demo 123456"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo, container, false);

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutButtonKun = view.findViewById(R.id.layout_buton_kun);
        layoutButtonOn = view.findViewById(R.id.layout_buton_on);

        initialButton(layoutButtonOn, onButtons, kunStrings);
        initialButton(layoutButtonKun, kunButtons, kunStrings);

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                createKunButtons(layoutButtonOn, onButtons);
                createKunButtons(layoutButtonKun, kunButtons);
            }
        });
    }

    void initialButton(LinearLayout layoutButtons,final Button[] buttons, String[] buttonTexts) {
        for (int i = 0; i < buttons.length; i++) {
            Button btn = new Button(getMainActivity());
            btn.setId(i);
            btn.setText(buttonTexts[i]);
            btn.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            btn.setTextColor(Color.WHITE);
            btn.setTextSize(14);
            btn.setBackgroundResource(R.drawable.custom_button_answer);
            btn.setTypeface(null, Typeface.BOLD);
            buttons[i] = btn;
            layoutButtons.addView(buttons[i]);
            final int j = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getMainActivity(), buttons[j].getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    void createKunButtons(LinearLayout layoutButtons, Button[] buttons) {
        ArrayList<Button> listTemp = new ArrayList<>();
        int[] widthButton = new int[buttons.length];
        for (int i = 0; i < buttons.length; i++) {
            widthButton[i] = buttons[i].getWidth();
        }
        layoutButtons.removeAllViews();
        for (int i = 0; i < buttons.length; i++) {
            layoutButtons.removeView(buttons[i]);
        }
        layoutButtons.setOrientation(LinearLayout.VERTICAL);
        int widthSum = layoutButtons.getWidth();
        int width = 0;
        for (int i = 0; i < widthButton.length; i++) {
            Log.i("onCreateView: " + i, "onCreateView: " + widthButton[i]);
            width = width + widthButton[i] + 10;

            if (width > widthSum - 10) {
                LinearLayout linearLayout = new LinearLayout(getMainActivity());
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setPadding(0, 0, 0, 5);

                for (int j = 0; j < listTemp.size(); j++) {
                    linearLayout.addView(listTemp.get(j));
                    Space space = new Space(getMainActivity());
                    space.setLayoutParams(new LinearLayout.LayoutParams(10, 0));
                    if (j < listTemp.size() - 1) {
                        linearLayout.addView(space);
                    }
                }

                layoutButtons.addView(linearLayout);
                width = widthButton[i] + 5;
                listTemp.clear();
                listTemp.add(buttons[i]);
            } else {
                listTemp.add(buttons[i]);
            }
        }

        if (width > 0) {
            LinearLayout linearLayout = new LinearLayout(getMainActivity());
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setPadding(0, 0, 0, 5);
            for (int j = 0; j < listTemp.size(); j++) {
                linearLayout.addView(listTemp.get(j));
                Space space = new Space(getMainActivity());
                space.setLayoutParams(new LinearLayout.LayoutParams(10, 0));
                if (j < listTemp.size() - 1) {
                    linearLayout.addView(space);
                }
            }
            layoutButtons.addView(linearLayout);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }
}
