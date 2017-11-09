package com.wmtcore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.wmtcore.util.FontManager;

/**
 * RadioButton which can use a custom font with the "font" attribute
 */

public class FontRadioButton extends RadioButton {

    public FontRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        FontManager.getInstance().applyFont(this, attrs);
    }

    public FontRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        FontManager.getInstance().applyFont(this, attrs);
    }
}
