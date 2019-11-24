/**
 * Created by laivantrach1190@gmail.com
 * Copyright (c) 2019 . All rights reserved.
 */
package com.trach.herobanner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.trach.herobannerlib.viewholders.HeroBannerViewHolder


class ViewPagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = (holder as? ViewHolder) ?: return
        when (position) {
            0 -> viewHolder.bind("https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg")
            1 -> viewHolder.bind("https://assets.materialup.com/uploads/20ded50d-cc85-4e72-9ce3-452671cf7a6d/preview.jpg")
            2 -> viewHolder.bind("https://assets.materialup.com/uploads/76d63bbc-54a1-450a-a462-d90056be881b/preview.png")
        }
    }

    var isInfinite = false
    var canInfinite = true
    var imageViewLayoutParams: ViewGroup.LayoutParams? = null


    fun getCount(): Int {
        return if (isInfinite && canInfinite) {
            itemCount + 2
        } else {
            itemCount
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.imageview, parent, false) as ImageView)
    }

    class ViewHolder(val imageView: ImageView): RecyclerView.ViewHolder(imageView) {
        fun bind(url: String){
           // Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView)
            Glide.with(imageView.context).load("http://i.imgur.com/DvpvklR.png").into(imageView)
        }
    }
}