/**
 * Created by laivantrach1190@gmail.com
 * Copyright (c) 2019 . All rights reserved.
 */
package com.trach.herobannerlib.adapters

import android.content.ContentValues.TAG
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.trach.herobannerlib.viewholders.HeroBannerViewHolder
import android.os.Looper.loop
import android.util.Log


abstract class HeroBannerAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var isInfinite = true
    var canInfinite = true
    var imageViewLayoutParams: ViewGroup.LayoutParams? = null

    private val itemList = mutableListOf<T>()

    fun getListCount() = itemList.size

    fun setItemTouchListener(onTouchListener: View.OnTouchListener) {

    }

    fun setItemList(items: List<T>) {
        itemList.clear()
        itemList.addAll(items)
        canInfinite = itemList.size > 1
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (isInfinite && canInfinite) {
            itemList.size + 2
        } else {
            itemList.size
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val imageView = ImageView(parent.context)
        imageViewLayoutParams?.let { imageView.layoutParams = it }
        imageView.adjustViewBounds = true
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return HeroBannerViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (!isInfinite) {
            bindData(holder, itemList[position])
        } else {
            when (position) {
                0 -> bindData(holder, itemList[itemList.size - 1])
                itemCount - 1 -> bindData(holder, itemList[0])
                else -> bindData(holder, itemList[position - 1])
            }
        }

    }


    fun getRealSlidePosition(position: Int): Int {
        return if (!isInfinite) {
            position
        } else {
            if (position in 0..(itemCount - 1)) {
                position + 1
            } else {
                Log.e(TAG, "setSelectedSlide: Invalid Item Position")
                1
            }
        }
    }
    abstract fun bindData(holder: RecyclerView.ViewHolder, data: T)

    fun setItemLayoutParams(params: ViewGroup.LayoutParams) {
        imageViewLayoutParams = params
    }

    fun getListPosition(position: Int): Int {
        if (!(isInfinite && canInfinite)) return position
        return when {
            position == 0 -> itemCount - 1 - 2 //First item is a dummy of last item
            position > itemCount - 2 -> 0 //Last item is a dummy of first item
            else -> position - 1
        }
    }

    fun getLastItemPosition(): Int {
        return if (isInfinite) {
            itemList.size
        } else {
            itemList.size - 1
        }
    }
}