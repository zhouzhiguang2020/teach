package com.futureeducation.commonmodule.view;

import android.content.Context;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.futureeducation.commonmodule.R;

/**
 * 点击跟多收起更多按钮
 */
public class ReadMoreTextView extends AppCompatTextView {

    private String readMoreText; //= "...更多";
    private String readLessText;// = "...收起";
    private int _maxLines = 2;
    private Context context;
    private CharSequence originalText = null;
    private boolean isTextExpandable = false;

    public ReadMoreTextView(Context context) {
        super(context);
        init(context);
    }

    public ReadMoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ReadMoreTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        readMoreText = context.getString(R.string.more);
        readLessText = context.getString(R.string.pack_up);
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);

                truncateText();
            }
        });
    }

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        super.setText(text, type);

        if (originalText == null) {
            originalText = text;
        }
    }

    @Override
    public int getMaxLines() {
        return _maxLines;
    }

    @Override
    public void setMaxLines(int maxLines) {
        _maxLines = maxLines;
    }

    public void truncateText() {

        int maxLines = _maxLines;
        String text = getText().toString();

        setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        if (getLineCount() > maxLines) {
            isTextExpandable = true;

            int lineEndIndex = getLayout().getLineEnd(maxLines - 1);

            String truncatedText = getText().subSequence(0, lineEndIndex - readMoreText.length() + 1).toString();
            setText(truncatedText);

            SpannableString spannableString = makeLinkSpan(readMoreText, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expandText();
                }
            });
            append(spannableString);
            makeLinksFocusable(this);

        }
    }

    public void expandText() {
        setText(originalText);
        SpannableString spannableString = makeLinkSpan(readLessText, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMaxLines(_maxLines);
                truncateText();
                Log.d("less", "clicked");
            }
        });
        append(spannableString);
        super.setMaxLines(1000);
    }

    public void reset() {
        originalText = null;
    }

    public boolean isTextExpandable() {
        return isTextExpandable;
    }

    private SpannableString makeLinkSpan(CharSequence text, View.OnClickListener listener) {
        SpannableString link = new SpannableString(text);
        link.setSpan(new ClickableString(listener), 0, text.length(),
                SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        return link;
    }

    private void makeLinksFocusable(TextView tv) {
        MovementMethod m = tv.getMovementMethod();
        if ((m == null) || !(m instanceof LinkMovementMethod)) {
            if (tv.getLinksClickable()) {
                tv.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
    }

    private class ClickableString extends ClickableSpan {
        private View.OnClickListener mListener;

        public ClickableString(View.OnClickListener listener) {
            mListener = listener;
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ContextCompat.getColor(context, R.color.more_text_color));
            ds.setUnderlineText(false);
        }


    }

}
