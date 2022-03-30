package com.futureeducation.commonmodule.model;

import java.util.ArrayList;

/**
 * @ClassName AnswerBean
 * @Description TODO
 * @Author ASUS
 * @Date 2020/5/24 14:54
 * @Version 1.0
 */
public class AnswerBean {
    private ArrayList<String> answer;

    public ArrayList<String> getAnswer() {
        return answer;
    }

    public void setAnswer(ArrayList<String> answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "AnswerBean{" +
                "answer=" + answer +
                '}';
    }
}
