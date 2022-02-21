package com.etoos.commons.ui.base

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter<T, VH : RecyclerView.ViewHolder>(
    itemsTheSame: (T, T) -> Boolean,
    contentsTheSame: (T, T) -> Boolean
) : ListAdapter<T, VH>(object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(old: T, new: T): Boolean = itemsTheSame(old, new)
        override fun areContentsTheSame(old: T, new: T): Boolean = contentsTheSame(old, new) }) {

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH
    abstract override fun onBindViewHolder(holder: VH, position: Int)
}