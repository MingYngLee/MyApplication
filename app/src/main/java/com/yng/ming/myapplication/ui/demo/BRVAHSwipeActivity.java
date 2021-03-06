package com.yng.ming.myapplication.ui.demo;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.base.BaseActivity;
import com.yng.ming.myapplication.util.log.Logcat;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 滑动删除/item拖拽
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * <p>
 * BRVAH基本使用：
 * 没有特殊需求的情况下，Adapter继承BaseQuickAdapter即可；
 * 需要滑动/拖拽功能时，Adapter需要继承BaseItemDraggableAdapter
 * <p>
 * 滑动/拖拽的使用：
 * 1.创建ItemDragAndSwipeCallback
 * 2.创建ItemTouchHelper
 * 3.调用ItemTouchHelper的attachToRecyclerView方法连接RecyclerView
 * 4.启用滑动/拖拽：enableSwipeItem()/enableDragItem(...)
 * 5.设置滑动/拖拽监听：setOnItemSwipeListener()/setOnItemDragListener()
 * <p>
 * 文档：
 * http://www.jianshu.com/p/b343fcff51b0
 * <p>
 * 需要注意notifyDataSetChanged方法一般情况下不需要使用
 * <p>
 * 现在存在的问题：
 * 刷新后，侧滑删除的功能失效
 * --> 已解决，因为自己对RecyclerView不熟。。详细看代码
 */
public class BRVAHSwipeActivity extends BaseActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.easySwipeRefresh)
    SmartRefreshLayout easySwipeRefresh;

    List<String> list;
    BRVAHSwipeAdapter adapter;
    ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_swipe);
        setToolbar();
        init();
    }

    private void setToolbar() {
        setTitleText("滑动删除/item拖拽");
    }

    private void init() {
        initView();
        setDate();
        easySwipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                easySwipeRefresh.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // refresh时最好的做法应该只更新数据
                        setDate();
                        easySwipeRefresh.finishRefresh();
                    }
                }, 2000);
            }
        });
    }

    private void initView() {
        adapter = new BRVAHSwipeAdapter(R.layout.easy_swipe_item, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        /**
         * 在往rv添加一个新的itemTouchHelper前，需要先将之前的itemTouchHelper去掉，即执行
         * itemTouchHelper.attachToRecyclerView(null);，不然后续的操作扔会在之前的adapter中进行
         */
        if (itemTouchHelper != null) {
            itemTouchHelper.attachToRecyclerView(null);
        }
        ItemDragAndSwipeCallback callback = new ItemDragAndSwipeCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        adapter.setOnItemDragListener(onItemDragListener);
        adapter.setOnItemSwipeListener(onItemSwipeListener);
        adapter.enableDragItem(itemTouchHelper, R.id.easyContentLayout, true);
        adapter.enableSwipeItem();
    }

    private void setDate() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("item " + i);
        }
        adapter.setNewData(list);
    }

    /**
     * 这里需要使用BaseItemDraggableAdapter
     */
    public class BRVAHSwipeAdapter extends BaseItemDraggableAdapter<String, BaseViewHolder> {

        public BRVAHSwipeAdapter(int layoutResId, List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.easyContent, item);
        }

    }

    /**
     * 侧滑功能
     */
    OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
        @Override
        public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
            Logcat.i("--------- onItemSwipeStart ---------");
        }

        @Override
        public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
            Logcat.i("--------- clearItemSwipe ---------");
        }

        @Override
        public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
            Logcat.i("--------- onItemSwiped ---------");
        }

        @Override
        public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
            Logcat.i("--------- onItemSwipeMoving ---------");
        }
    };

    /**
     * 拖拽
     */
    OnItemDragListener onItemDragListener = new OnItemDragListener() {
        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
            Logcat.i("--------- onItemDragStart ---------");
        }

        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
            Logcat.i("--------- onItemDragMoving ---------");
        }

        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            Logcat.i("--------- onItemDragEnd ---------");
        }
    };

}
