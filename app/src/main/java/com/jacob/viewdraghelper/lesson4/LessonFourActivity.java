package com.jacob.viewdraghelper.lesson4;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jacob.viewdraghelper.R;

/**
 * Created by jacob-wj on 2015/4/14.
 */
public class LessonFourActivity extends FragmentActivity implements View.OnClickListener {

    private Button mButtonHidden;
    private Button mButtonQueen;
    private DragLayoutFour mDragLayoutFour;
    private LinearLayout mLinearMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_four);

        mButtonHidden = (Button) findViewById(R.id.button_hidden);
        mButtonQueen =  (Button) findViewById(R.id.button_queen);

        mButtonHidden.setOnClickListener(this);
        mButtonQueen.setOnClickListener(this);

        mDragLayoutFour = (DragLayoutFour) findViewById(R.id.drag_layout_four);
        mLinearMain = (LinearLayout) findViewById(R.id.linear_main);
        mLinearMain.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mDragLayoutFour.isMoving()){
                    v.setTop(oldTop);
                    v.setBottom(oldBottom);
                    v.setLeft(oldLeft);
                    v.setRight(oldRight);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        Log.e("OnClick:",button.getText().toString());

    }
}
