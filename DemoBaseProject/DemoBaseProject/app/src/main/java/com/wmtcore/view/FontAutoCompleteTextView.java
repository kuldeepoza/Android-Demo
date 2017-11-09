package com.wmtcore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

import com.wmtcore.util.FontManager;

/**
 * AutoCompleteTextView which can use a custom font with the "font" attribute
 */

public class FontAutoCompleteTextView extends AutoCompleteTextView {

    public FontAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        FontManager.getInstance().applyFont(this, attrs);
    }

    public FontAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        FontManager.getInstance().applyFont(this, attrs);
    }
}
