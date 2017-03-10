package com.mel.seekraces.customsViews;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by void on 04/02/2017.
 */

public class SwipeRefreshLayoutWithEmpty extends SwipeRefreshLayout {
    private ViewGroup container;
    public SwipeRefreshLayoutWithEmpty(Context context) {
        super(context);
    }
    public SwipeRefreshLayoutWithEmpty(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        // The swipe refresh layout has 2 children; the circle refresh indicator
        // and the view container. The container is needed here
        ViewGroup container = getContainer();
        if (container == null) {
            return false;
        }

        // The container has 2 children; the empty view and the scrollable view.
        if (container.getChildCount() != 2) {
            return false;
        }

        // Use whichever one is visible and test that it can scroll
        View view = container.getChildAt(0);
        if (view.getVisibility() != View.VISIBLE) {
            view = container.getChildAt(1);
            view.setVisibility(View.VISIBLE);
        }else{
            container.getChildAt(1).setVisibility(View.GONE);
        }

        return ViewCompat.canScrollVertically(view, -1);
    }

    private ViewGroup getContainer() {
        // Cache this view
        if (container != null) {
            return container;
        }

        // The container may not be the first view. Need to iterate to find it
        for (int i=0; i<getChildCount(); i++) {
            if (getChildAt(i) instanceof ViewGroup) {
                container = (ViewGroup) getChildAt(i);
                break;
            }
        }

        if (container == null) {
            throw new RuntimeException("Container view not found");
        }

        return container;
    }
}
