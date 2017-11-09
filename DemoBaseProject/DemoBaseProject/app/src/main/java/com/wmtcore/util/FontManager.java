package com.wmtcore.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.HashMap;

import com.wmt.android.demobaseproject.R;

/**
 * FontManager is a Singleton which creates and cache fonts (Typeface)
 */

public class FontManager {

    private static FontManager sInstance;
    private HashMap<String, Typeface> mFontCache = new HashMap<>(); // <path to the font file in the assets folder, cached Typeface>

    /**
     * Gets the FontManager singleton
     * @return the FontManager singleton
     */
    public static FontManager getInstance() {
        if (sInstance == null) {
            sInstance = new FontManager();
        }
        return sInstance;
    }

    /**
     * Gets a Typeface from the cache. If the Typeface does not exist, creates it, cache it and returns it.
     * @param context a Context
     * @param path Path to the font file in the assets folder. ie "fonts/MyCustomFont.ttf"
     * @return the corresponding Typeface (font)
     * @throws RuntimeException if the font asset is not found
     */
    public Typeface getTypeface(@NonNull Context context, @NonNull String path) throws RuntimeException {
        Typeface typeface = mFontCache.get(path);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), path);
            mFontCache.put(path, typeface);
        }
        return typeface;
    }

    /**
     * Gets a Typeface from the cache. If the Typeface does not exist, creates it, cache it and returns it.
     * @param context a Context
     * @param pathResId String resource id for the path to the font file in the assets folder. ie "fonts/MyCustomFont.ttf"
     * @return the corresponding Typeface (font)
     * @throws RuntimeException if the resource or the font asset is not found
     */
    public Typeface getTypeface(@NonNull Context context, int pathResId) throws RuntimeException {
        try {
            String path = context.getResources().getString(pathResId);
            return getTypeface(context, path);
        } catch (Resources.NotFoundException exception) {
            String message = "String resource id " + pathResId + " not found";
            throw new RuntimeException(message);
        }
    }

    /**
     * Applies a font to a TextView that uses the "font" attribute.
     * @param textView TextView when the font should apply
     * @param attrs Attributes that contain the "font" attribute with the path to the font file in the assets folder
     */
    public void applyFont(@NonNull TextView textView, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            Context context = textView.getContext();
            TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextView, 0, 0);
            String fontPath = styledAttributes.getString(R.styleable.TextView_font);
            if (!TextUtils.isEmpty(fontPath)) {
                Typeface typeface = getTypeface(context, fontPath);
                if (typeface != null) {
                    textView.setTypeface(typeface);
                }
            }
            styledAttributes.recycle();
        }
    }

}