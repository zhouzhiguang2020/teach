package com.futureeducation.commonmodule.utill;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.futureeducation.commonmodule.model.AnswerBean;

/**
 * @ClassName AnswerUtils
 * @Description TODO
 * @Author ASUS
 * @Date 2020/5/24 17:08
 * @Version 1.0
 */
public class AnswerUtils {
    /**
     *
     * @param new_answer 新标准答案
     * @param new_respond 新作答答案
     * @param answer  旧答案
     * @param respond 旧作答
     * @return
     */
    public static boolean getResult(String new_answer,String new_respond,String answer,String respond){
        if(!TextUtils.isEmpty(new_answer) && !TextUtils.isEmpty(new_respond)){
            try {
                AnswerBean bean1 = JSON.parseObject(new_answer,AnswerBean.class);
                AnswerBean bean2 = JSON.parseObject(new_respond,AnswerBean.class);
                return bean1.getAnswer().equals(bean2.getAnswer());
            }catch (Exception e){
                return false;
            }
        }else{
            if(!TextUtils.isEmpty(answer) && !TextUtils.isEmpty(respond)){
                if(answer.trim().equals(respond.trim())){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }

        }
    }
}
