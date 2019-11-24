/**
 * Created by laivantrach1190@gmail.com
 * Copyright (c) 2019 . All rights reserved.
 */
package com.trach.herobanner

import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.trach.herobannerlib.services.ImageLoadingService


class PicassoImageLoadingService : ImageLoadingService {

    override fun loadImage(url: String, imageView: ImageView) {
        Picasso.get().load(url).into(imageView)
    }

    override fun loadImage(resource: Int, imageView: ImageView) {
        Picasso.get().load(resource).into(imageView)
    }

    override fun loadImage(url: String, placeHolder: Int, errorDrawable: Int, imageView: ImageView) {
        Picasso.get().load(url).placeholder(placeHolder).error(errorDrawable).into(imageView)
    }
}