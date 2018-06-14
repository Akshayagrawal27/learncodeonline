package com.akshay.learncodeonline.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DataStructure implements Parcelable {

    private String questions;
    private String answer;

    public DataStructure() {
    }

    public DataStructure(String questions, String answer) {
        this.questions = questions;
        this.answer = answer;
    }

    protected DataStructure(Parcel in) {
        questions = in.readString();
        answer = in.readString();
    }

    public static final Creator<DataStructure> CREATOR = new Creator<DataStructure>() {
        @Override
        public DataStructure createFromParcel(Parcel in) {
            return new DataStructure(in);
        }

        @Override
        public DataStructure[] newArray(int size) {
            return new DataStructure[size];
        }
    };

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(questions);
        dest.writeString(answer);
    }
}
