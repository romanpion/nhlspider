package com.romao.nhlspider.ui.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.romao.nhlspider.ui.common.CustomFontHelper;

public class StyledText extends AppCompatTextView {

    public StyledText(Context context) {
        super(context);
    }

    public StyledText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            CustomFontHelper.setCustomFont(this, context, attrs);
        }
    }

    public StyledText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            CustomFontHelper.setCustomFont(this, context, attrs);
        }
    }
}
