package com.yng.ming.myapplication.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;

import com.yng.ming.myapplication.R;

/**
 * Created by Bright Lee on 2017/9/7
 * 自定义Dialog 底部宽度全屏弹出
 * 需要有自定义的style:
 * <style name="MyDialog">
 * --无标题--
 * <item name="android:windowNoTitle">true</item>
 * --无边框--
 * <item name="android:windowFrame">@null</item>
 * --悬浮在Activity上--
 * <item name="android:windowIsFloating">true</item>
 * --阴影半透明--
 * <item name="android:windowIsTranslucent">true</item>
 * </style>
 */
public class BottomDialog extends AlertDialog {
    Context mContext;

    public BottomDialog(Context context) {
        super(context, R.style.BottomDialogStyle); // 自定义全屏style
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }
}