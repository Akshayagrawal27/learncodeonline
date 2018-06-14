package com.akshay.learncodeonline.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akshay.learncodeonline.R;
import com.akshay.learncodeonline.model.DataStructure;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    DataStructureListClickListener mOnClickListener;
    List<DataStructure> dataStructureList;

    public RecyclerViewAdapter(ArrayList<DataStructure> dataStructures, DataStructureListClickListener mOnClickListener) {
        this.dataStructureList  = dataStructures;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_structure_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        DataStructure dataStructure = dataStructureList.get(position);
        holder.tvQuestion.setText(dataStructure.getQuestions());

    }

    @Override
    public int getItemCount() {
        return dataStructureList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvQuestion;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvQuestion = (TextView) itemView.findViewById(R.id.question);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onDataStructureListItemClick(getAdapterPosition(), (ArrayList<DataStructure>) dataStructureList);
        }
    }

    public interface DataStructureListClickListener {
        void onDataStructureListItemClick(int clickedItemIndex, ArrayList<DataStructure> dataStructures);
    }
}
