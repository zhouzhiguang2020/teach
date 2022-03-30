package com.future_education.assistant.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by .
 * User: ASUS
 * Date: 2021/11/27
 * Time: 11:22
 */
public class EvaluationIndicatorItemBean implements Parcelable {
    private String ids;

    public String getIds() {
        return ids;
    }


    public void setIds(String ids) {
        this.ids = ids;
    }

    public EvaluationIndicatorItemBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ids);
    }

    protected EvaluationIndicatorItemBean(Parcel in) {
        this.ids = in.readString();
    }

    public static final Parcelable.Creator<EvaluationIndicatorItemBean> CREATOR = new Parcelable.Creator<EvaluationIndicatorItemBean>() {
        @Override
        public EvaluationIndicatorItemBean createFromParcel(Parcel source) {
            return new EvaluationIndicatorItemBean(source);
        }

        @Override
        public EvaluationIndicatorItemBean[] newArray(int size) {
            return new EvaluationIndicatorItemBean[size];
        }
    };
}
