package com.wmtcore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.wmtcore.util.FontManager;

/**
 * Button which can use a custom font with the "font" attribute
 */

public class FontButton extends Button {
    public FontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        FontManager.getInstance().applyFont(this, attrs);
    }

    public FontButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        FontManager.getInstance().applyFont(this, attrs);
    }
}
