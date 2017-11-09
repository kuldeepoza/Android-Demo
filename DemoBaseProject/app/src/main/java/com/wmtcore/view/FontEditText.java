package com.wmtcore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.wmtcore.util.FontManager;

/**
 * EditText which can use a custom font with the "font" attribute
 */

public class FontEditText extends EditText {

    public FontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        FontManager.getInstance().applyFont(this, attrs);
    }

    public FontEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        FontManager.getInstance().applyFont(this, attrs);
    }
}
