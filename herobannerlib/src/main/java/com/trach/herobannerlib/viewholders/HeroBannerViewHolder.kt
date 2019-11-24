/**
 * Created by laivantrach1190@gmail.com
 * Copyright (c) 2019 . All rights reserved.
 */
package com.trach.herobannerlib.viewholders

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.trach.herobannerlib.HeroBanner


class HeroBannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageView: ImageView = itemView as ImageView

    fun bindImage(imageUrl: String?) {
        if (imageUrl != null) {
            HeroBanner.getImageLoadingService().loadImage(imageUrl, imageView)
        }
    }

    fun bindImage(@DrawableRes imageResourceId: Int) {
        HeroBanner.getImageLoadingService().loadImage(imageResourceId, imageView)
    }

    fun bindImage(url: String, @DrawableRes placeHolder: Int, @DrawableRes error: Int) {
        HeroBanner.getImageLoadingService().loadImage(url, placeHolder, error, imageView)
    }

}