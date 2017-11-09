package com.wmtcore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ToggleButton;

import com.wmtcore.util.FontManager;

/**
 * ToggleButton which can use a custom font with the "font" attribute
 */

public class FontToggleButton extends ToggleButton {

    public FontToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        FontManager.getInstance().applyFont(this, attrs);
    }

    public FontToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        FontManager.getInstance().applyFont(this, attrs);
    }
}
