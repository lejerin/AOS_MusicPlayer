package com.happy.commons.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter<T, VH: RecyclerView.ViewHolder, VB: ViewDataBinding>(
    itemsTheSame: ((T, T) -> Boolean) = { old: T, new : T -> old === new },
    contentsTheSame: (T, T) -> Boolean
) : ListAdapter<T, VH>(object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(old: T, new: T): Boolean = itemsTheSame(old, new)
        override fun areContentsTheSame(old: T, new: T): Boolean = contentsTheSame(old, new) }) {

    abstract val layoutResourceId: Int
    abstract val action: (VB) -> VH

    abstract inner class BaseViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        abstract fun onBind(item: Any?)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        try {
            (holder as? BaseListAdapter<*, *, *>.BaseViewHolder)?.onBind(currentList[position])
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        DataBindingUtil.inflate<VB>(
            LayoutInflater.from(parent.context),
            layoutResourceId,
            parent,
            false
        ).let { action.invoke(it) }
}