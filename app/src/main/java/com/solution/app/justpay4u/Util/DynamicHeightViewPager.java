package com.solution.app.justpay4u.Util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

/**
 * Created by Vishnu Agarwal on 04/05/2022.
 */
public class DynamicHeightViewPager extends ViewPager {

    public DynamicHeightViewPager(Context context) {
        super(context);
        initPageChangeListener();
    }

    public DynamicHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPageChangeListener();
    }


    private void initPageChangeListener() {
        addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                requestLayout();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //View child = getChildAt(getCurrentItem());
        View child = getCurrentView(this);
        if (child != null) {
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    View getCurrentView(ViewPager viewPager) {
        try {
            final int currentItem = viewPager.getCurrentItem();
            for (int i = 0; i < viewPager.getChildCount(); i++) {
                final View child = viewPager.getChildAt(i);
                final LayoutParams layoutParams = (LayoutParams)
                        child.getLayoutParams();

                Field f = layoutParams.getClass().getDeclaredField("position");
                //NoSuchFieldException
                f.setAccessible(true);
                int position = (Integer) f.get(layoutParams); //IllegalAccessException

                if (!layoutParams.isDecor && currentItem == position) {
                    return child;
                }
            }
        } catch (NoSuchFieldException e) {
            e.fillInStackTrace();
        } catch (IllegalArgumentException e) {
            e.fillInStackTrace();
        } catch (IllegalAccessException e) {
            e.fillInStackTrace();
        }
        return null;
    }
}