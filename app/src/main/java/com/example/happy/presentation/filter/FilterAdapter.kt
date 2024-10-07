package com.example.happy.presentation.filter

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.happy.R
import com.example.happy.common.base.BaseDiffUtilAdapter
import com.example.happy.common.base.BaseViewHolder
import com.example.happy.common.util.layoutInflater
import com.example.happy.common.util.setFirstClickEvent
import com.example.happy.databinding.ItemFilterListBinding
import com.example.happy.model.FilterData

class FilterAdapter(
    private val context: Context,
    private val owner: LifecycleOwner,
    private val onClickFilter: (FilterData) -> Unit,
) : BaseDiffUtilAdapter<ViewDataBinding, FilterData>(owner.lifecycleScope) {
    override fun areItemsTheSame(oldItem: FilterData, newItem: FilterData): Boolean =
        oldItem.no == newItem.no

    override fun areContentsTheSame(oldItem: FilterData, newItem: FilterData): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: FilterData, newItem: FilterData) = null

    override fun getBinding(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewDataBinding> =
        FilterViewHolder(ItemFilterListBinding.inflate(context.layoutInflater, parent, false))

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewDataBinding>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.bind(position)
    }

    inner class FilterViewHolder(
        override val binding: ItemFilterListBinding
    ): BaseViewHolder<ItemFilterListBinding>(binding) {
        override fun bind(position: Int) {
            val data = adapterList[position]

            setUi(data)
            clickListener(data)
        }

        private fun setUi(data: FilterData) = with(binding) {
            txtFilter.text = data.name
            clFilter.setBackgroundResource(if(data.isSelect) R.color.black else R.color.teal_200)
        }

        private fun clickListener(data: FilterData) = with(binding) {
            clFilter.setFirstClickEvent(owner.lifecycleScope) {
                onClickFilter.invoke(data)
            }
        }
    }
}