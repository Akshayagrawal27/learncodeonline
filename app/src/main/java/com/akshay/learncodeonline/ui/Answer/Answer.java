package com.akshay.learncodeonline.ui.Answer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akshay.learncodeonline.R;
import com.akshay.learncodeonline.model.DataStructure;
import com.akshay.learncodeonline.util.Constants;

import java.util.ArrayList;

public class Answer extends Fragment {

    View view;
    public static final String LOG_TAG = "fragment";

    private ArrayList<DataStructure> dataStructureList;
    private int clickedItemIndex;

    TextView tvQuestion, tvAnswer, tvPrev, tvNext;
    ImageView ivPrev, ivNext;

    public Answer() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null) {
            dataStructureList = savedInstanceState.getParcelableArrayList(Constants.KEY_DATA_STRUSTURE_LIST);
            clickedItemIndex = savedInstanceState.getInt(Constants.KEY_CLICKED_ITEM_INDEX);
        }

        view = inflater.inflate(R.layout.fragment_answer, container, false);

        tvQuestion = (TextView) view.findViewById(R.id.tv_answer_question);
        tvAnswer = (TextView) view.findViewById(R.id.tv_answer_answer);
        tvNext = (TextView) view.findViewById(R.id.next);
        tvPrev = (TextView) view.findViewById(R.id.previous);
        ivNext = (ImageView) view.findViewById(R.id.next_image);
        ivPrev = (ImageView) view.findViewById(R.id.prev_image);

        setViewWithData(clickedItemIndex);

        return view;
    }

    public void setDataStructureList(ArrayList<DataStructure> dataStructureList) {
        this.dataStructureList = dataStructureList;
    }

    public void setClickedItemIndex(int clickedItemIndex) {
        this.clickedItemIndex = clickedItemIndex;
    }

    public void setViewWithData(int clickedItemIndex) {
        tvQuestion.setText(dataStructureList.get(clickedItemIndex).getQuestions());
        tvAnswer.setText(dataStructureList.get(clickedItemIndex).getAnswer());
    }

    public boolean updateClickedItemIndexCounter(int clickedButton) {

        if (clickedButton == 0 && clickedItemIndex != 0) {
            setViewWithData(--clickedItemIndex);
        } else if (clickedButton == 1 && clickedItemIndex < dataStructureList.size()-1) {
            setViewWithData(++clickedItemIndex);
        }

        if (clickedButton == 0 && clickedItemIndex == 0){
            return true;
        } else if (clickedButton == 1 && clickedItemIndex == dataStructureList.size()-1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(Constants.KEY_DATA_STRUSTURE_LIST, dataStructureList);
        outState.putInt(Constants.KEY_CLICKED_ITEM_INDEX, clickedItemIndex);
    }
}
