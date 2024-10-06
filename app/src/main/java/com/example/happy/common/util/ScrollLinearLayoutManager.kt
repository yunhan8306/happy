package com.example.happy.common.util

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScrollLinearLayoutManager(
    context: Context,
    orientation: Int = RecyclerView.VERTICAL
) : LinearLayoutManager(context, orientation, false) {

    var isScrollEnable = true

    override fun canScrollVertically(): Boolean {
        return isScrollEnable && super.canScrollVertically()
    }

    override fun canScrollHorizontally(): Boolean {
        return isScrollEnable && super.canScrollHorizontally()
    }
}