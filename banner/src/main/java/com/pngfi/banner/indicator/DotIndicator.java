package com.pngfi.banner.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pngfi.banner.R;

import java.util.List;

/**
 * Created by pngfi on 2018/3/21.
 *
 * dot attr设置如下的drawable
 *
 * <selector xmlns:android="http://schemas.android.com/apk/res/android">
 *  <item android:state_selected="false">
 *      <shape android:shape="oval">
 *          <solid android:color="#D8D8D8" />
 *          <size android:width="5dp" android:height="5dp" />
 *      </shape>
 *  </item>
 *  <item android:state_selected="true">
 *      <shape  android:shape="oval">
 *          <solid android:color="@android:color/white" />
 *          <stroke android:width="0.5dp" android:color="#000000" />
 *          <size android:width="5dp" android:height="5dp" />
 *      </shape>
 *  </item>
 * </selector>
 *
 * dotMargin设置点之间的距离
 */

public class DotIndicator extends LinearLayout implements Indicator {


    private int dotRes;
    private int dotMargin;

    private int selectedPosition = 0;


    public DotIndicator(Context context) {
        this(context, null);
    }

    public DotIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DotIndicator);
        dotRes = ta.getResourceId(R.styleable.DotIndicator_dot, R.drawable.bg_dot_view);
        dotMargin = (int) ta.getDimension(R.styleable.DotIndicator_dotMargin, dp2px(12));
        ta.recycle();
    }


    private int dp2px(float f) {
        int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, f, getResources().getDisplayMetrics());
        return i;
    }



    @Override
    public  void setCount(int  count) {
        if (count<=0)
            return;
        if (count<= 1)
            setVisibility(View.INVISIBLE);
        else
            setVisibility(View.VISIBLE);
        removeAllViews();
        selectedPosition = 0;
        for (int i = 0; i < count; i++) {
            final ImageView imageView = new ImageView(getContext());
            LayoutParams lp=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.rightMargin= (i == count ? 0 : dotMargin);
            imageView.setLayoutParams(lp);
            imageView.setImageResource(dotRes);
            addView(imageView);
            if (i == selectedPosition) {
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setSelected(true);
                    }
                });
            }
        }
    }

    @Override
    public void setSelected(int position) {
        int last = selectedPosition;
        selectedPosition = position;
        getChildAt(position).setSelected(true);
        getChildAt(last).setSelected(false);
    }
}
