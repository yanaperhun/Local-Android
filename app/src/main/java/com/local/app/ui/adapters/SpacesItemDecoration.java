package com.local.app.ui.adapters;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int dimen;

    public SpacesItemDecoration(@DimenRes int dimen) {
        this.dimen = dimen;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        outRect.bottom = parent.getResources().getDimensionPixelSize(dimen);

        // Add top margin only for the first item to avoid double space between items
//    if (parent.getChildLayoutPosition(view) == 0) {
//        outRect.top = dimen;
//    } else {
//        outRect.top = 0;
//    }
    }
}