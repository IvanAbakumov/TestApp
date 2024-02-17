package com.world.test.presentation.screen_1


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.world.domain.models.HistoryItemModel
import com.world.test.R
import com.world.test.BR
import com.world.test.databinding.LinkInfoItemBinding

class HistoryAdapter(
    private val listener: HistoryItemListener,
) : RecyclerView.Adapter<HistoryAdapter.PropertyTypeHolder>() {

    private var list = listOf<HistoryItemModel>()

    fun setData(data: List<HistoryItemModel>) {
        list = data
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PropertyTypeHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.link_info_item,
            parent,
            false
        ), listener
    )

    override fun onBindViewHolder(holder: PropertyTypeHolder, position: Int) =
        holder.bind(list[position])

    inner class PropertyTypeHolder(
        var binding: LinkInfoItemBinding,
        private val listener: HistoryItemListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HistoryItemModel) {
            binding.setVariable(BR.model, item)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }
    }

    interface HistoryItemListener {
        fun setSelectedItem(item: HistoryItemModel)
    }
}