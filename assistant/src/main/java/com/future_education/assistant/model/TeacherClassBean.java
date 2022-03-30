package com.future_education.assistant.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.Objects;

public class TeacherClassBean implements Parcelable {

    private int classRoleTotal;
    private int classStudentTotal;
    private String class_code;
    private String class_group_uid;
    private String class_levelView;
    private String class_name;
    private String class_uid;
    private int level_value;
    private String uni_id;
    private String unit_session_uid;

    public TeacherClassBean() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TeacherClassBean that = (TeacherClassBean) o;
        return TextUtils.equals(class_code, that.class_code) &&
                TextUtils.equals(class_name, that.class_name) &&
                TextUtils.equals(class_uid, that.class_uid) &&
                TextUtils.equals(uni_id, that.uni_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(class_code, class_name, class_uid, uni_id);
    }

    @Override
    public String toString() {
        return "TeacherClassBean{" +
                "classRoleTotal=" + classRoleTotal +
                ", classStudentTotal=" + classStudentTotal +
                ", class_code='" + class_code + '\'' +
                ", class_group_uid='" + class_group_uid + '\'' +
                ", class_levelView='" + class_levelView + '\'' +
                ", class_name='" + class_name + '\'' +
                ", class_uid='" + class_uid + '\'' +
                ", level_value=" + level_value +
                ", uni_id='" + uni_id + '\'' +
                ", unit_session_uid='" + unit_session_uid + '\'' +
                '}';
    }

    public int getClassRoleTotal() {
        return classRoleTotal;
    }

    public void setClassRoleTotal(int classRoleTotal) {
        this.classRoleTotal = classRoleTotal;
    }

    public int getClassStudentTotal() {
        return classStudentTotal;
    }

    public void setClassStudentTotal(int classStudentTotal) {
        this.classStudentTotal = classStudentTotal;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public String getClass_group_uid() {
        return class_group_uid;
    }

    public void setClass_group_uid(String class_group_uid) {
        this.class_group_uid = class_group_uid;
    }

    public String getClass_levelView() {
        return class_levelView;
    }

    public void setClass_levelView(String class_levelView) {
        this.class_levelView = class_levelView;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getClass_uid() {
        return class_uid;
    }

    public void setClass_uid(String class_uid) {
        this.class_uid = class_uid;
    }

    public int getLevel_value() {
        return level_value;
    }

    public void setLevel_value(int level_value) {
        this.level_value = level_value;
    }

    public String getUni_id() {
        return uni_id;
    }

    public void setUni_id(String uni_id) {
        this.uni_id = uni_id;
    }

    public String getUnit_session_uid() {
        return unit_session_uid;
    }

    public void setUnit_session_uid(String unit_session_uid) {
        this.unit_session_uid = unit_session_uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.classRoleTotal);
        dest.writeInt(this.classStudentTotal);
        dest.writeString(this.class_code);
        dest.writeString(this.class_group_uid);
        dest.writeString(this.class_levelView);
        dest.writeString(this.class_name);
        dest.writeString(this.class_uid);
        dest.writeInt(this.level_value);
        dest.writeString(this.uni_id);
        dest.writeString(this.unit_session_uid);
    }

    protected TeacherClassBean(Parcel in) {
        this.classRoleTotal = in.readInt();
        this.classStudentTotal = in.readInt();
        this.class_code = in.readString();
        this.class_group_uid = in.readString();
        this.class_levelView = in.readString();
        this.class_name = in.readString();
        this.class_uid = in.readString();
        this.level_value = in.readInt();
        this.uni_id = in.readString();
        this.unit_session_uid = in.readString();
    }

    public static final Creator<TeacherClassBean> CREATOR = new Creator<TeacherClassBean>() {
        @Override
        public TeacherClassBean createFromParcel(Parcel source) {
            return new TeacherClassBean(source);
        }

        @Override
        public TeacherClassBean[] newArray(int size) {
            return new TeacherClassBean[size];
        }
    };

}
