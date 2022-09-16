package com.in.kistec.SettingsActivity;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.in.kistec.R;

public class SandeepEditText extends AppCompatEditText {
    public SandeepEditText(@NonNull Context context) {
        super(context);
        init();
    }

    public SandeepEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SandeepEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        SpannableStringBuilder  builder = new SpannableStringBuilder();
        SpannableString string = new SpannableString(getHint()!=null ? getHint() : "");
        string.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.hint)),0, (getHint()!=null ? getHint() : "").length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableString string2 = new SpannableString(" *");
        string2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.white)),0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(string).append(string2);
        setHint(builder);
    }

}
