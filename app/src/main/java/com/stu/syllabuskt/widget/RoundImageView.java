package com.stu.syllabuskt.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.stu.syllabuskt.R;

/**
 * yuan
 * 2020/9/6
 **/
public class RoundImageView extends AppCompatImageView {

    private RectF mRect;
    private Path mPath;
    private float mRadius;

    public RoundImageView(@NonNull Context context) {
        this(context, null);
    }

    public RoundImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RoundImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context, attrs);
        initView(context);
    }

    private void getAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        mRadius = ta.getDimension(R.styleable.RoundImageView_radius, -1);
        ta.recycle();
    }

    private void initView(Context context) {
        mRect = new RectF();
        mPath = new Path();
        setLayerType(LAYER_TYPE_SOFTWARE, null); //禁用硬件加速
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mRadius < 0) clipCircle(w, h);
        else clipRoundRect(w, h);
    }

    /**
     * 圆角图
     * @param width
     * @param height
     */
    private void clipRoundRect(int width, int height) {
        mRect.left = 0;
        mRect.top = 0;
        mRect.right = width;
        mRect.bottom = height;
        mPath.addRoundRect(mRect, mRadius, mRadius, Path.Direction.CW);
    }

    /**
     * 圆形图
     * @param width
     * @param height
     */
    private void clipCircle(int width, int height) {
        int radius = Math.min(width, height) / 2;
        mPath.addCircle(radius, radius, radius, Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.clipPath(mPath);
        super.onDraw(canvas);
    }
}
