package com.stu.syllabuskt.anim;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.stu.syllabuskt.R;

/**
 * Create by yuan on 2020/12/4
 */
public class LoadingAnimationUtil {
    public static void startLoading(Context ctx, ViewGroup viewGroup) {
        viewGroup.setVisibility(View.VISIBLE);
        ImageView iv = (ImageView) viewGroup.getChildAt(0);
        Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.rotate_anim);
        LinearInterpolator li = new LinearInterpolator();
        animation.setInterpolator(li);
        iv.startAnimation(animation);
    }

    public static void stopLoading(ViewGroup viewGroup) {
        viewGroup.setVisibility(View.GONE);
        ImageView iv = (ImageView) viewGroup.getChildAt(0);
        iv.clearAnimation();
    }
}
