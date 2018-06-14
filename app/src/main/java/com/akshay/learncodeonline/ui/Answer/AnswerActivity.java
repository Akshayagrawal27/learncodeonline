package com.akshay.learncodeonline.ui.Answer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akshay.learncodeonline.R;
import com.akshay.learncodeonline.model.DataStructure;
import com.akshay.learncodeonline.util.Constants;

import java.util.ArrayList;

import static com.akshay.learncodeonline.R.color.disabled;

public class AnswerActivity extends AppCompatActivity {

    public static final String LOG_TAG = "fragment";

    private static final int PREVIOUS_BUTTON_CLICKED = 0;
    private static final int NEXT_BUTTON_CLICKED = 1;

    private LinearLayout mPrevious, mNext;
    private View adBanner;
    private ImageView ivPrev, ivNext;
    private TextView tvPrev, tvNext;

    ArrayList<DataStructure> dataStructureList;
    int clickedItemIndex;


    Answer answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mPrevious = (LinearLayout) findViewById(R.id.layout_previous);
        mNext = (LinearLayout) findViewById(R.id.layout_next);
        adBanner = (View) findViewById(R.id.answer_ad_banner);
        tvNext = (TextView) findViewById(R.id.next);
        tvPrev = (TextView) findViewById(R.id.previous);
        ivNext = (ImageView) findViewById(R.id.next_image);
        ivPrev = (ImageView) findViewById(R.id.prev_image);

        Intent intent = this.getIntent();
        dataStructureList = intent.getParcelableArrayListExtra(Constants.KEY_DATA_STRUSTURE_LIST);
        clickedItemIndex = intent.getIntExtra(Constants.KEY_CLICKED_ITEM_INDEX, 0);

        if (clickedItemIndex == 0){
            disabledColor(tvPrev, ivPrev);
        }

        if (clickedItemIndex == dataStructureList.size() - 1){
            disabledColor(tvNext, ivNext);
        }

        answer = new Answer();
        answer.setDataStructureList(dataStructureList);
        answer.setClickedItemIndex(clickedItemIndex);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_answers, answer)
                .commit();

        adBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://learncodeonline.in/"));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getBaseContext(), "No application can handle this request."
                            + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void previousAnswers(View view) {

        boolean status = answer.updateClickedItemIndexCounter(PREVIOUS_BUTTON_CLICKED);

        if (status){
            mPrevious.setEnabled(false);
            mNext.setEnabled(true);
            disabledColor(tvPrev, ivPrev);
        } else{
            enabledColor(tvNext, ivNext);
            mPrevious.setEnabled(true);
            mNext.setEnabled(true);
        }
    }

    public void nextAnswers(View view) {
        boolean status = answer.updateClickedItemIndexCounter(NEXT_BUTTON_CLICKED);

        if (status){
            mPrevious.setEnabled(true);
            mNext.setEnabled(false);
            disabledColor(tvNext, ivNext);
        } else{
            enabledColor(tvPrev, ivPrev);
            mPrevious.setEnabled(true);
            mNext.setEnabled(true);
        }
    }

    private void disabledColor(TextView textView, ImageView imageView){
        textView.setTextColor(ContextCompat.getColor(this, R.color.disabled));
        imageView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.disabled)));
    }

    private void enabledColor(TextView textView, ImageView imageView){
        textView.setTextColor(Color.BLACK);
        imageView.setColorFilter(Color.BLACK);
    }
}
