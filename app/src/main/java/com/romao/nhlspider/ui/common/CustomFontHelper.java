package com.romao.nhlspider.ui.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.romao.nhlspider.R;

public class CustomFontHelper {

    /**
     * Sets a font on a textview based on the custom com.my.package:font attribute
     * If the custom font attribute isn't found in the attributes nothing happens.
     */
    public static void setCustomFont(TextView textview, Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomFont);
        if (a == null)
            throw new IllegalStateException("Could not obtain styled attributes");

        String font = a.getString(R.styleable.CustomFont_font);
        setCustomFont(textview, font, context);
        if (font != null) {
            setCustomFont(textview, font, context);
        }
        a.recycle();
    }

    /**
     * Sets a font on a textview
     */
    public static void setCustomFont(TextView textview, String font, Context context) {
        if (font == null) {
            return;
        }
        Typeface tf = FontCache.get(font, context);
        if (tf != null) {
            textview.setTypeface(tf);
            // For making the font anti-aliased.
            textview.setPaintFlags(textview.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
    }
}
