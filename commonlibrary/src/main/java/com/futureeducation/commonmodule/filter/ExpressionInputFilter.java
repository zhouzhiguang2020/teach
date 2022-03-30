package com.futureeducation.commonmodule.filter;

import android.text.InputFilter;
import android.text.Spanned;

import com.apkfuns.logutils.LogUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表情过滤器
 */
public class ExpressionInputFilter implements InputFilter {


    Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
            Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher emojiMatcher = emoji.matcher(source);
        if (emojiMatcher.find()) {
            //                    Toast.makeText(MainActivity.this,"不支持输入表情", 0).show();
            LogUtils.e("不支持表情");
            return "";
        }
        return null;

    }
}
