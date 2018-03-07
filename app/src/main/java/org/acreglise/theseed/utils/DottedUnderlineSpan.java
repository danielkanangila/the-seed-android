package org.acreglise.theseed.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ReplacementSpan;

import org.acreglise.theseed.R;


public class DottedUnderlineSpan extends ReplacementSpan {

    private Paint p;
    private int mWidth;
    private String mSpan;

    private float mSpanLength;
    private float mStrokeWidth;
    private float mDashPathEffect;
    private float mOffsetY;
    private boolean mLengthIsCached = false;

    public DottedUnderlineSpan(int _color, String _spannedText, Context context){
        p = new Paint();
        p.setColor(_color);
        p.setStyle(Paint.Style.STROKE);
        p.setPathEffect(new DashPathEffect(new float[]{mDashPathEffect, mDashPathEffect}, 0));
        p.setStrokeWidth(mStrokeWidth);
        mSpan = _spannedText;

        mStrokeWidth = context.getResources().getDimension(R.dimen.stroke_dith);
        mDashPathEffect = context.getResources().getDimension(R.dimen.dash_path_effet);
        mOffsetY = context.getResources().getDimension(R.dimen.offset_y);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        mWidth = (int) paint.measureText(text, start, end);
        return mWidth;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        canvas.drawText(text, start, end, x, y, paint);
        if(!mLengthIsCached)
            mSpanLength = paint.measureText(mSpan);

        Path path = new Path();
        path.moveTo(x, y + mOffsetY);
        path.lineTo(x + mSpanLength, y + mOffsetY);
        canvas.drawPath(path, this.p);
    }
}
