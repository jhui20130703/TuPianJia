package com.icon.image.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icon.image.R;


/**
 * Created by chaohx on 2017/7/20.
 */

public class MyOneLineView extends LinearLayout {

    /**
     * 上下分割线，默认隐藏上面分割线
     */
    private View dividerTop, dividerBottom;

    /**
     * 最外层容器
     */
    private LinearLayout llRoot;
    /**
     * 最左边的Icon
     */
    private ImageView ivLeftIcon;
    /**
     * 中间的文字内容
     */
    private TextView tvTextContent;

    /**
     * 右边的文字
     */
    private TextView tvRightText;

    /**
     * 右边的icon 通常是箭头
     */
    private ImageView ivRightIcon;


    /**
     * 整个一行被点击
     */
    public interface OnRootClickListener {
        void onRootClick(View view);
    }

    public MyOneLineView(Context context) {
        super(context);
    }

    public MyOneLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * 初始化各个控件
     */
    public MyOneLineView init(Context context, AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_my_one_line, this, true);
        llRoot = findViewById(R.id.ll_root);
        //分割线
        dividerTop = findViewById(R.id.divider_top);
        dividerBottom = findViewById(R.id.divider_bottom);

        //内容
        ivLeftIcon = findViewById(R.id.iv_left_icon);
        tvTextContent = findViewById(R.id.tv_text_content);
        tvRightText = findViewById(R.id.tv_right_text);
        ivRightIcon = findViewById(R.id.iv_right_icon);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.layout);
        int leftIcon = ta.getResourceId(R.styleable.layout_left_icon, -1);
        String textContent = ta.getString(R.styleable.layout_text_content);
        String rightText = ta.getString(R.styleable.layout_right_text);
        int rightIcon = ta.getResourceId(R.styleable.layout_right_icon, -1);
        boolean leftIconDisplay = ta.getBoolean(R.styleable.layout_left_icon_display, true);
        boolean rightIconDisplay = ta.getBoolean(R.styleable.layout_right_icon_display, true);
        boolean dividerTopDisplay = ta.getBoolean(R.styleable.layout_divider_top_display, true);
        boolean dividerBottomDisplay = ta.getBoolean(R.styleable.layout_divider_buttom_display, true);

        int textContentColor = ta.getInteger(R.styleable.layout_text_content_color,-1);
        int textRightColor = ta.getInteger(R.styleable.layout_right_text_color,-1);

        ta.recycle();

        if (leftIcon != -1) ivLeftIcon.setBackgroundResource(leftIcon);
        tvTextContent.setText(textContent);
        tvRightText.setText(rightText);
        if (rightIcon != -1) ivRightIcon.setBackgroundResource(rightIcon);
        if (textContentColor != -1) tvTextContent.setTextColor(textContentColor);
        if (textRightColor != -1) tvRightText.setTextColor(textRightColor);

        ivLeftIcon.setVisibility(leftIconDisplay ? VISIBLE : GONE);
        ivRightIcon.setVisibility(rightIconDisplay ? VISIBLE : INVISIBLE);
        dividerTop.setVisibility(dividerTopDisplay ? VISIBLE : GONE);
        dividerBottom.setVisibility(dividerBottomDisplay ? VISIBLE : GONE);

        return this;
    }

    /**
     * 获取左边内容文本
     * @return
     */
    public String getLeftContent(){
        return tvTextContent.getText().toString();
    }

    /**
     * 获取右边内容文本
     * @return
     */
    public String getRightText(){
        return tvRightText.getText().toString();
    }

    /**
     * 设置上下分割线的显示情况
     *
     * @return
     */
    public MyOneLineView showDivider(Boolean showDividerTop, Boolean showDivderBottom) {
        if (showDividerTop) {
            dividerTop.setVisibility(VISIBLE);
        } else {
            dividerTop.setVisibility(GONE);
        }
        if (showDivderBottom) {
            dividerBottom.setVisibility(VISIBLE);
        } else {
            dividerBottom.setVisibility(GONE);
        }
        return this;
    }


    /**
     * 设置左边Icon
     *
     * @param iconRes
     */
    public MyOneLineView setLeftIcon(int iconRes) {
        ivLeftIcon.setImageResource(iconRes);
        return this;
    }

    /**
     * 设置左边Icon显示与否
     *
     * @param showLeftIcon
     */
    public MyOneLineView showLeftIcon(boolean showLeftIcon) {
        if (showLeftIcon) {
            ivLeftIcon.setVisibility(VISIBLE);
        } else {
            ivLeftIcon.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 设置中间的文字内容
     *
     * @param textContent
     * @return
     */
    public MyOneLineView setTextContent(String textContent) {
        tvTextContent.setText(textContent);
        return this;
    }

    /**
     * 设置中间的文字颜色
     *
     * @return
     */
    public MyOneLineView setTextContentColor(int colorRes) {
        tvTextContent.setTextColor(getResources().getColor(colorRes));
        return this;
    }

    /**
     * 设置中间的文字大小
     *
     * @return
     */
    public MyOneLineView setTextContentSize(int textSizeSp) {
        tvTextContent.setTextSize(textSizeSp);
        return this;
    }

    /**
     * 设置右边文字内容
     *
     * @return
     */
    public MyOneLineView setRightText(String rightText) {
        tvRightText.setText(rightText);
        return this;
    }


    /**
     * 设置右边文字颜色
     *
     * @return
     */
    public MyOneLineView setRightTextColor(int colorRes) {
        tvRightText.setTextColor(getResources().getColor(colorRes));
        return this;
    }

    /**
     * 设置右边文字大小
     *
     * @return
     */
    public MyOneLineView setRightTextSize(int textSize) {
        tvRightText.setTextSize(textSize);
        return this;
    }

    /**
     * 设置右箭头的显示与不显示
     *
     * @param showArrow
     */
    public MyOneLineView showArrow(boolean showArrow) {
        if (showArrow) {
            ivRightIcon.setVisibility(VISIBLE);
        } else {
            ivRightIcon.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 获取右边icon
     */
    public MyOneLineView setIvRightIcon(int iconRes) {

        ivRightIcon.setImageResource(iconRes);

        return this;
    }

    /**
     * 获取右边icon
     */
    public ImageView getIvRightIcon() {

        return ivRightIcon;
    }

    //----------------------下面是一些点击事件

    public MyOneLineView setOnRootClickListener(final OnRootClickListener onRootClickListener, final int tag) {
        llRoot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                llRoot.setTag(tag);
                onRootClickListener.onRootClick(llRoot);
            }
        });
        return this;
    }

}
