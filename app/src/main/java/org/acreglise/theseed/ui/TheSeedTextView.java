package org.acreglise.theseed.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

public class TheSeedTextView extends TextView {
    public TheSeedTextView(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto_Regular.ttf");
        setTypeface(face);
    }

    public TheSeedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto_Regular.ttf");
        setTypeface(face);
    }

    public TheSeedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto_Regular.ttf");
        setTypeface(face);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TheSeedTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto_Regular.ttf");
        setTypeface(face);
    }
}
