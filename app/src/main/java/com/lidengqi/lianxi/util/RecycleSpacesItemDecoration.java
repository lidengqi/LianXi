package com.lidengqi.lianxi.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;

/**
 * @author dengqi.li
 * @date 2020/5/8
 */
public class RecycleSpacesItemDecoration extends ItemDecoration {
    private int leftSpace;
    private int rightSpace;
    private int topSpace;
    private int bottomSpace;

    public RecycleSpacesItemDecoration(int leftSpace, int rightSpace, int topSpace) {
        this.leftSpace = leftSpace;
        this.rightSpace = rightSpace;
        this.topSpace = topSpace;
    }

    public RecycleSpacesItemDecoration(int rightSpace) {
        this.rightSpace = rightSpace;
    }

    public RecycleSpacesItemDecoration(int rightSpace, int bottomSpace) {
        this.rightSpace = rightSpace;
        this.bottomSpace = bottomSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int childPosition = parent.getChildAdapterPosition(view);
        if (bottomSpace != 0) {
            outRect.bottom = bottomSpace;
        }
        int itemCount = parent.getAdapter().getItemCount();
        if (childPosition == itemCount - 1) {
            // 最后一个设置PaddingRight
            outRect.right = 0;
            //if (bottomSpace != 0) {
           // outRect.bottom = 0;
            //}
        } else {
            outRect.right = rightSpace;
        }

    }
}
