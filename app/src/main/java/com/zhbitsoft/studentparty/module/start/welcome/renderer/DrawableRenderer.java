package com.zhbitsoft.studentparty.module.start.welcome.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.cleveroad.slidingtutorial.Renderer;
import com.zhbitsoft.studentparty.R;

/**
     * {@link Renderer} implementation for drawing indicators with bitmap.
     */
    @SuppressWarnings("WeakerAccess")
    public class DrawableRenderer implements Renderer {

        private Drawable mDrawableActive;
        private Drawable mDrawable;

        public static DrawableRenderer create(@NonNull Context context) {
            return new DrawableRenderer(context);
        }

        private DrawableRenderer(@NonNull Context context) {
            mDrawableActive = ContextCompat.getDrawable(context, R.drawable.vec_checkbox_fill_circle_outline);
            mDrawable = ContextCompat.getDrawable(context, R.drawable.vec_checkbox_blank_circle_outline);
        }

        @Override
        public void draw(@NonNull Canvas canvas, @NonNull RectF elementBounds, @NonNull Paint paint, boolean isActive) {
            Drawable drawable = isActive ? mDrawableActive : mDrawable;
            drawable.setBounds((int) elementBounds.left, (int) elementBounds.top,
                    (int) elementBounds.right, (int) elementBounds.bottom);
            drawable.draw(canvas);
        }
    }

