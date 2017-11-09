package com.wmtcore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.wmtcore.util.FontManager;

/**
 * CheckBox which can use a custom font with the "font" attribute
 */

public class FontCheckBox extends CheckBox {

    public FontCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        FontManager.getInstance().applyFont(this, attrs);
    }

    public FontCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        FontManager.getInstance().applyFont(this, attrs);
    }
}
