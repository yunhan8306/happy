package com.example.happy.common.base

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil

abstract class BaseDiffUtilAdapter<B : ViewDataBinding, T : Any>(
    lifecycleScope: LifecycleCoroutineScope
) : BaseAdapter<B, T>(lifecycleScope) {

    protected abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean
    protected abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean
    protected open fun getChangePayload(oldItem: T, newItem: T): Any? = null

    private val itemCallback by lazy {
        object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
                this@BaseDiffUtilAdapter.areItemsTheSame(oldItem, newItem)

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
                this@BaseDiffUtilAdapter.areContentsTheSame(oldItem, newItem)

            override fun getChangePayload(oldItem: T, newItem: T): Any? =
                this@BaseDiffUtilAdapter.getChangePayload(oldItem, newItem)
        }
    }

    private val asyncAdapterList by lazy { AsyncListDiffer(this, itemCallback) }

    override val adapterList: MutableList<T>
        get() = asyncAdapterList.currentList

    open fun submit(list: List<T>, complete: () -> Unit = {}) {
        asyncAdapterList.submitList(ArrayList(list), complete)
    }

}