package com.wmtcore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.wmtcore.util.FontManager;

/**
 * TextView which can use a custom font with the "font" attribute
 */

public class FontTextView extends TextView {

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        FontManager.getInstance().applyFont(this, attrs);   // add this call
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        FontManager.getInstance().applyFont(this, attrs);   // add this call
    }
}
