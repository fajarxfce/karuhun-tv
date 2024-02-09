package com.google.fajarpro.util;
/*
Author  : Nurul Fajar
Email   : cirebonredhat@gmail.com
*/


import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Tambahkan logic jika Anda ingin memberikan space pada item pertama atau item terakhir
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space;
        }

    }
}
