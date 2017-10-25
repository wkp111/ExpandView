package com.wkp.expandview_lib.view;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wkp.expandview_lib.R;
import com.wkp.expandview_lib.util.ViewUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 2017/10/24.
 * 展开控件
 */

public class ExpandView extends GridLayout {
    private static final int DEFAULT_COLUMN_COUNT = 4;              //默认每行字段数
    private static final int DEFAULT_ROW_COUNT = 2;                 //默认初始显示行数
    private static final int DEFAULT_MORE_PIC = R.drawable.ic_more; //默认更多按钮图片
    private static final String DEFAULT_MORE_TEXT = "更多";         //默认更多按钮文本
    private static final int DEFAULT_MORE_BG = Color.WHITE;         //默认更多按钮背景
    private static final int DEFAULT_MORE_TEXT_COLOR = Color.BLACK; //默认更多按钮文本颜色
    private static final int DEFAULT_MORE_TEXT_SIZE = 16;           //默认更多按钮文本大小
    private static final int DEFAULT_VIEW_DURATION = 100;           //默认子条目的动画时长
    private static final int DEFAULT_VIEW_PADDING = 5;              //默认子条目的内间距
    private static final int DEFAULT_VIEW_MARGIN = 5;               //默认子条目的外间距
    private static final int DEFAULT_VIEW_HEIGHT = 120;             //默认子条目的高度
    private static final int TYPE_ONE = 1;                          //控件类型1
    private static final int TYPE_TWO = 2;                          //控件类型2

    /**
     * 每行字段数
     */
    private int mColumnCount = DEFAULT_COLUMN_COUNT;
    /**
     * 初始行数
     */
    private int mRowCount = DEFAULT_ROW_COUNT;
    /**
     * 更多按钮图片
     */
    @DrawableRes
    private int mMorePic = DEFAULT_MORE_PIC;
    /**
     * 更多按钮文本
     */
    private String mMoreText = DEFAULT_MORE_TEXT;
    /**
     * 更多按钮背景
     */
    @ColorInt
    private int mMoreBackground = DEFAULT_MORE_BG;
    /**
     * 更多按钮文本颜色
     */
    @ColorInt
    private int mMoreTextColor = DEFAULT_MORE_TEXT_COLOR;
    /**
     * 更多按钮字体大小
     */
    private int mMoreTextSize = DEFAULT_MORE_TEXT_SIZE;
    /**
     * 子条目动画时长
     */
    private int mViewDuration = DEFAULT_VIEW_DURATION;
    /**
     * 子条目内间距
     */
    private int mViewPadding = DEFAULT_VIEW_PADDING;
    /**
     * 子条目外间距
     */
    private int mViewMargin = DEFAULT_VIEW_MARGIN;
    /**
     * 子条目高度
     */
    private int mViewHeight = DEFAULT_VIEW_HEIGHT;
    /**
     * 更多按钮
     */
    private TextView mMoreButton;
    /**
     * 实际总行数
     */
    private int mRowTotal = 0;
    /**
     * 类型1的条目集合
     */
    private List<String> mItemsTypeOne = new ArrayList<>();
    /**
     * 类型2的条目集合
     */
    private List<View> mItemsTypeTwo = new ArrayList<>();
    /**
     * 当前控件类型
     */
    private int mCurrentType = TYPE_ONE;
    private Context mContext;
    private OnItemClickListener mListener;

    public ExpandView(Context context) {
        this(context, null);
    }

    public ExpandView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttr(context, attrs);
        init();
    }

    /**
     * 初始化参数
     *
     * @param context
     * @param attrs
     */
    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandView);
        if (typedArray != null) {
            mColumnCount = typedArray.getInteger(R.styleable.ExpandView_wkp_column, DEFAULT_COLUMN_COUNT);
            mRowCount = typedArray.getInteger(R.styleable.ExpandView_wkp_rowMin, DEFAULT_ROW_COUNT);
            mViewMargin = typedArray.getDimensionPixelSize(R.styleable.ExpandView_wkp_space,DEFAULT_VIEW_MARGIN * 2) / 2;
            mViewDuration = typedArray.getInteger(R.styleable.ExpandView_wkp_itemDuration, DEFAULT_VIEW_DURATION);
            mViewHeight = typedArray.getDimensionPixelSize(R.styleable.ExpandView_wkp_itemHeight, DEFAULT_VIEW_HEIGHT);
            mMorePic = typedArray.getResourceId(R.styleable.ExpandView_wkp_moreButtonImg, R.drawable.ic_more);
            String moreText = typedArray.getString(R.styleable.ExpandView_wkp_moreButtonText);
            mMoreText = TextUtils.isEmpty(moreText) ? DEFAULT_MORE_TEXT : moreText;
            mMoreBackground = typedArray.getColor(R.styleable.ExpandView_wkp_textBgColor, DEFAULT_MORE_BG);
            mMoreTextColor = typedArray.getColor(R.styleable.ExpandView_wkp_textColor, DEFAULT_MORE_TEXT_COLOR);
            mMoreTextSize = typedArray.getDimensionPixelSize(R.styleable.ExpandView_wkp_textSize, DEFAULT_MORE_TEXT_SIZE);
            typedArray.recycle();
        }
    }

    /**
     * 初始化
     */
    private void init() {
        //设置每行字段数
        super.setColumnCount(1);
        //设置布局动画效果
        LayoutTransition transition = new LayoutTransition();
        transition.setDuration(mViewDuration);
        setLayoutTransition(transition);
        //创建 更多 按钮
        Drawable leftImg = getResources().getDrawable(mMorePic);
        mMoreButton = createTextView(mContext, mMoreText, mMoreTextSize, mMoreTextColor, leftImg, mMoreBackground, mViewPadding, mViewMargin);
        mMoreButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                expandItems();
            }
        });
    }

    /**
     * 创建TextView
     *
     * @param context   上下文
     * @param text      文本内容
     * @param textSize  文本大小
     * @param textColor 文本颜色
     * @param leftImg   左侧图片
     * @param bgColor   背景颜色
     * @param padding   内边距
     * @param margin    外边距
     * @return TextView
     */
    private TextView createTextView(Context context, String text, int textSize, int textColor, Drawable leftImg, int bgColor, int padding, int margin) {
        TextView view = new TextView(context);
        view.setText(text);
        view.setTextColor(textColor);
        view.setTextSize(ViewUtil.dspForPx(context, textSize));
        padding = ViewUtil.dspForPx(context, padding);
        margin = ViewUtil.dspForPx(context, margin);
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthPixels / mColumnCount - margin * 2, LinearLayout.LayoutParams.MATCH_PARENT);
        if (leftImg != null) {
            view.setPadding(3 * padding, padding, padding, padding);
            leftImg.setBounds(0, 0, leftImg.getMinimumWidth(), leftImg.getMinimumHeight());
            view.setCompoundDrawables(leftImg, null, null, null);
        } else {
            view.setPadding(padding, padding, padding, padding);
        }
        params.setMargins(margin, 0, margin, 0);
        view.setLayoutParams(params);
        view.setGravity(Gravity.CENTER);
        view.setBackgroundColor(bgColor);
        return view;
    }

    /**
     * 创建LinearLayout
     *
     * @param context
     * @param height
     * @return
     */
    private LinearLayout createLinearLayout(Context context, int height, int margin) {
        height = ViewUtil.dspForPx(context, height);
        margin = ViewUtil.dspForPx(context, margin);
        LinearLayout linearLayout = new LinearLayout(context);
        LayoutParams params = new LayoutParams();
        params.width = LayoutParams.MATCH_PARENT;
        params.height = height;
        params.setMargins(0, margin, 0, margin);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return linearLayout;
    }

    /**
     * 添加条目
     *
     * @param items
     */
    private <T> void addItems(@NonNull List<T> items) {
        removeAllViews();
        mRowTotal = items.size() / ((float) mColumnCount) > items.size() / mColumnCount ? items.size() / mColumnCount + 1 : items.size() / mColumnCount;
        for (int i = 0; i < mRowTotal; i++) {
            LinearLayout linearLayout = createLinearLayout(mContext, mViewHeight, mViewMargin);
            for (int j = i * mColumnCount; j < (i + 1) * mColumnCount && j < items.size(); j++) {
                View view;
                final int position = j;
                if (mCurrentType == TYPE_ONE) {
                    view = createTextView(mContext, (String) items.get(j), mMoreTextSize, mMoreTextColor, null, mMoreBackground, mViewPadding, mViewMargin);
                } else {
                    int widthPixels = getResources().getDisplayMetrics().widthPixels;
                    view = (View) items.get(j);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                    params.width = widthPixels / mColumnCount - params.leftMargin - params.rightMargin;
                    params.height = LinearLayout.LayoutParams.MATCH_PARENT;
                    int margin = ViewUtil.dspForPx(mContext, mViewMargin);
                    params.setMargins(margin, 0, margin, 0);
                    view.setLayoutParams(params);
                }
                linearLayout.addView(view);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onItemClick(v, ExpandView.this, position);
                        }
                    }
                });
            }
            addView(linearLayout);
        }
        if (items.size() > mColumnCount * mRowCount) {
            packUpItems();
        }
    }

    /**
     * 展开隐藏条目
     */
    private void expandItems() {
        ((LinearLayout) getChildAt(mRowCount - 1)).removeView(mMoreButton);
        for (int i = mRowCount; i < getChildCount(); i++) {
            getChildAt(i).setVisibility(VISIBLE);
        }
    }

    /**
     * 收起隐藏条目
     */
    public void packUpItems() {
        ((LinearLayout) getChildAt(mRowCount - 1)).addView(mMoreButton, mColumnCount - 1);
        for (int i = mRowCount; i < getChildCount(); i++) {
            getChildAt(i).setVisibility(GONE);
        }
    }

    /**
     * 设置条目数据
     *
     * @param items 条目数据
     * @return ExpandView 用于链式编程
     */
    public ExpandView setTextItems(@NonNull String[] items) {
        mCurrentType = TYPE_ONE;
        List<String> itemsOne = Arrays.asList(items);
        mItemsTypeOne = itemsOne;
        addItems(itemsOne);
        return this;
    }

    /**
     * 设置条目数据
     *
     * @param items 条目数据
     * @return ExpandView 用于链式编程
     */
    public ExpandView setTextItems(@NonNull List<String> items) {
        mCurrentType = TYPE_ONE;
        mItemsTypeOne = items;
        addItems(items);
        return this;
    }

    /**
     * 设置条目数据
     *
     * @param items 条目数据
     * @return ExpandView 用于链式编程
     */
    public ExpandView setViewItems(@NonNull View[] items) {
        mCurrentType = TYPE_TWO;
        List<View> itemsTwo = Arrays.asList(items);
        mItemsTypeTwo = itemsTwo;
        addItems(itemsTwo);
        return this;
    }

    /**
     * 设置条目数据
     *
     * @param items 条目数据
     * @return ExpandView 用于链式编程
     */
    public ExpandView setViewItems(@NonNull List<View> items) {
        mCurrentType = TYPE_TWO;
        mItemsTypeTwo = items;
        addItems(items);
        return this;
    }

    /**
     * 设置每行字段数
     *
     * @param columnCount 每行字段数
     * @return ExpandView 用于链式编程
     */
    public ExpandView setColumn(int columnCount) {
        columnCount = columnCount <= 0 ? 1 : columnCount;
        mColumnCount = columnCount;
        if (mItemsTypeOne.size() > 0 || mItemsTypeTwo.size() > 0) {
            addItems(mCurrentType == TYPE_ONE ? mItemsTypeOne : mItemsTypeTwo);
        }
        return this;
    }

    /**
     * 设置最少显示行数
     *
     * @param rowMin 最少显示行数
     * @return ExpandView 用于链式编程
     */
    public ExpandView setRowMin(int rowMin) {
        rowMin = rowMin <= 0 ? 1 : rowMin;
        mRowCount = rowMin;
        if (mItemsTypeOne.size() > 0 || mItemsTypeTwo.size() > 0) {
            addItems(mCurrentType == TYPE_ONE ? mItemsTypeOne : mItemsTypeTwo);
        }
        return this;
    }

    /**
     * 设置条目动画时长
     * @param duration 动画时长
     * @return ExpandView 用于链式编程
     */
    public ExpandView setItemDuration(int duration) {
        duration = duration <= 0 ? 1 : duration;
        mViewDuration = duration;
        init();
        return this;
    }

    /**
     * 设置条目间距
     *
     * @param itemSpace 条目间距
     * @return ExpandView 用于链式编程
     */
    public ExpandView setItemSpace(int itemSpace) {
        itemSpace = itemSpace < 0 ? 0 : itemSpace;
        mViewMargin = itemSpace / 2;
        if (mItemsTypeOne.size() > 0 || mItemsTypeTwo.size() > 0) {
            addItems(mCurrentType == TYPE_ONE ? mItemsTypeOne : mItemsTypeTwo);
        }
        return this;
    }

    /**
     * 设置条目高度
     *
     * @param itemHeight 条目高度
     * @return ExpandView 用于链式编程
     */
    public ExpandView setItemHeight(int itemHeight) {
        itemHeight = itemHeight <= 0 ? 1 : itemHeight;
        mViewHeight = itemHeight;
        if (mItemsTypeOne.size() > 0 || mItemsTypeTwo.size() > 0) {
            addItems(mCurrentType == TYPE_ONE ? mItemsTypeOne : mItemsTypeTwo);
        }
        return this;
    }

    /**
     * 设置更多按钮左侧图片
     *
     * @param moreImg 资源图片
     * @return ExpandView 用于链式编程
     */
    public ExpandView setMoreButtonImg(@DrawableRes int moreImg) {
        mMorePic = moreImg;
        Drawable leftImg = getResources().getDrawable(mMorePic);
        leftImg.setBounds(0, 0, leftImg.getMinimumWidth(), leftImg.getMinimumHeight());
        mMoreButton.setCompoundDrawables(leftImg, null, null, null);
        return this;
    }

    /**
     * 设置更多按钮文本
     *
     * @param moreText 按钮文本
     * @return ExpandView 用于链式编程
     */
    public ExpandView setMoreButtonText(String moreText) {
        mMoreText = moreText;
        mMoreButton.setText(mMoreText);
        return this;
    }

    /**
     * 设置文本模式时的条目背景色
     *
     * @param textBgColor 背景颜色
     * @return ExpandView 用于链式编程
     */
    public ExpandView setTextBgColor(@ColorInt int textBgColor) {
        mMoreBackground = textBgColor;
        mMoreButton.setBackgroundColor(mMoreBackground);
        if (mItemsTypeOne.size() > 0) {
            addItems(mItemsTypeOne);
        }
        return this;
    }

    /**
     * 设置文本模式时的文本颜色
     *
     * @param textColor 文本颜色
     * @return ExpandView 用于链式编程
     */
    public ExpandView setTextColor(@ColorInt int textColor) {
        mMoreTextColor = textColor;
        mMoreButton.setTextColor(mMoreTextColor);
        if (mItemsTypeOne.size() > 0) {
            addItems(mItemsTypeOne);
        }
        return this;
    }

    /**
     * 设置文本模式时的文本大小
     *
     * @param textSize 文本大小
     * @return ExpandView 用于链式编程
     */
    public ExpandView setTextSize(int textSize) {
        textSize = textSize <= 0 ? 1 : textSize;
        mMoreTextSize = textSize;
        mMoreButton.setTextSize(mMoreTextSize);
        if (mItemsTypeOne.size() > 0) {
            addItems(mItemsTypeOne);
        }
        return this;
    }

    /**
     * 获取总行数
     *
     * @return
     */
    public int getRowTotal() {
        return mRowTotal;
    }

    /**
     * 条目点击监听
     */
    public interface OnItemClickListener {
        void onItemClick(View view, ViewGroup parent, int position);
    }

    /**
     * 设置条目点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

}
