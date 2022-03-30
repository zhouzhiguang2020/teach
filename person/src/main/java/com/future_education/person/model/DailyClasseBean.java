package com.future_education.person.model;

import androidx.annotation.Keep;

/**
 * Created by .
 * User: ASUS
 * Date: 2021/11/24
 * Time: 14:53
 * 老师每天课程实体类
 */
@Keep
public class DailyClasseBean {
    private String subject;//科目
    private String grade;//年级
    private boolean islunchbreak;//午休时间
    private String classtimeperiod;//上课时间段
    private String  Sortingclass;//上课排序
    public DailyClasseBean() {
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public boolean isIslunchbreak() {
        return islunchbreak;
    }

    public void setIslunchbreak(boolean islunchbreak) {
        this.islunchbreak = islunchbreak;
    }

    public String getClasstimeperiod() {
        return classtimeperiod;
    }

    public void setClasstimeperiod(String classtimeperiod) {
        this.classtimeperiod = classtimeperiod;
    }

    public String getSortingclass() {
        return Sortingclass;
    }

    public void setSortingclass(String sortingclass) {
        Sortingclass = sortingclass;
    }
}
