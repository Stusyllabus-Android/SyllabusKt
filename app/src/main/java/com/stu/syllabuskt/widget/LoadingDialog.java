package com.stu.syllabuskt.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.stu.syllabuskt.R;

/**
 * 通用加载dialog
 * 避免用户手动进行dismiss，只用事件处理完成并回调后才进行dismiss
 * 注意展示的持续时间
 * Create by yuan on 2020/12/4
 */
public class LoadingDialog extends Dialog {

    private final Context mContext;
    private final LinearLayout mCommonLoadingView;

    public LoadingDialog(@NonNull Context context, @Nullable String text) {
        super(context);
        mContext = context;
        mCommonLoadingView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.common_loading, null, false);
        if (text != null) ((TextView) mCommonLoadingView.findViewById(R.id.loadingTV)).setText(text);
        WindowManager wm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display=wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(point.x/3, ViewGroup.LayoutParams.WRAP_CONTENT);
        addContentView(mCommonLoadingView, lp);
    }

    @Override
    public void show() {
        mCommonLoadingView.setVisibility(View.VISIBLE);
        ImageView iv = (ImageView) mCommonLoadingView.getChildAt(0);
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.rotate_anim);
        LinearInterpolator li = new LinearInterpolator();
        animation.setInterpolator(li);
        iv.startAnimation(animation);
        super.show();
    }

    @Override
    public void dismiss() {}

    public void realDismiss() {
        mCommonLoadingView.setVisibility(View.GONE);
        ImageView iv = (ImageView) mCommonLoadingView.getChildAt(0);
        iv.clearAnimation();
        super.dismiss();
    }
}
