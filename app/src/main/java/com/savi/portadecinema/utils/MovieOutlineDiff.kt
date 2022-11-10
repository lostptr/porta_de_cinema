package com.savi.portadecinema.utils

import androidx.recyclerview.widget.DiffUtil
import com.savi.portadecinema.models.MovieOutline

class MovieOutlineDiff(
    private val old: List<MovieOutline>,
    private val new: List<MovieOutline>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == new[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].id == new[newItemPosition].id
    }
}