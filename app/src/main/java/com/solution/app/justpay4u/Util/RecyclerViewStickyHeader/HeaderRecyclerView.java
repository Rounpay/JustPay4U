package com.solution.app.justpay4u.Util.RecyclerViewStickyHeader;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class HeaderRecyclerView extends RecyclerView {
    public HeaderRecyclerView(@NonNull Context context) {
        super(context);
    }

    public HeaderRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
        if (getAdapter() instanceof HeaderStickyListener) {
            setStickyItemDecoration();
        }
    }

    private void setStickyItemDecoration() {
        HeaderItemDecoration itemDecoration = new HeaderItemDecoration((HeaderStickyListener) getAdapter());
        this.addItemDecoration(itemDecoration);
    }

}
