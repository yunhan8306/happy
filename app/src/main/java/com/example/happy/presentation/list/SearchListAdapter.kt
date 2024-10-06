package com.example.happy.presentation.list

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.happy.common.base.BaseDiffUtilAdapter
import com.example.happy.common.base.BaseViewHolder
import com.example.happy.common.util.layoutInflater
import com.example.happy.common.util.setFirstClickEvent
import com.example.happy.common.util.setImage
import com.example.happy.databinding.ItemCollectionListBinding
import com.example.happy.model.CollectionData

class SearchListAdapter(
    private val context: Context,
    private val owner: LifecycleOwner,
    private val onClickCollection: (CollectionData) -> Unit,
) : BaseDiffUtilAdapter<ViewDataBinding, CollectionData>(owner.lifecycleScope) {

    var refresh = false

    override fun areItemsTheSame(oldItem: CollectionData, newItem: CollectionData): Boolean =
        oldItem.thumbUri == newItem.thumbUri && !refresh

    override fun areContentsTheSame(oldItem: CollectionData, newItem: CollectionData): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: CollectionData, newItem: CollectionData) = null

    override fun getBinding(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewDataBinding> =
        CollectionViewHolder(ItemCollectionListBinding.inflate(context.layoutInflater, parent, false))

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewDataBinding>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.bind(position)
    }

    inner class CollectionViewHolder(
        override val binding: ItemCollectionListBinding
    ): BaseViewHolder<ItemCollectionListBinding>(binding) {
        override fun bind(position: Int) {
            val data = adapterList[position]

            setUi(data)
            clickListener(data)
        }

        @SuppressLint("SetTextI18n")
        private fun setUi(data: CollectionData) = with(binding) {
            imgThumb.setImage(data.thumbUri)

            txtTitle.text = data.titleKor
            txtWriter.text = "${data.writerName} (${data.madeYear})"
            txtProduct.text = data.category
        }

        private fun clickListener(data: CollectionData) = with(binding) {
            clCollection.setFirstClickEvent(owner.lifecycleScope) {
                onClickCollection.invoke(data)
            }
        }
    }
}