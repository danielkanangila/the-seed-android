package org.acreglise.theseed.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.acreglise.theseed.R;

public class StyleUtil {

    public static void applyFontForToolbarTitle(Toolbar toolbar, Context context){

        for(int i = 0; i < toolbar.getChildCount(); i++){
            View view = toolbar.getChildAt(i);
            if(view instanceof TextView){
                TextView tv = (TextView) view;
                Typeface titleFont = Typeface.createFromAsset(context.getAssets(), "fonts/ELEPHNT.TTF");
                if(tv.getText().equals(toolbar.getTitle())){
                    tv.setTypeface(titleFont);
                    break;
                }
            }
        }
    }
}
