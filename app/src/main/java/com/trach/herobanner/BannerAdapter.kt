/**
 * Created by laivantrach1190@gmail.com
 * Copyright (c) 2019 . All rights reserved.
 */
package com.trach.herobanner

import androidx.recyclerview.widget.RecyclerView
import com.trach.herobannerlib.adapters.HeroBannerAdapter
import com.trach.herobannerlib.viewholders.HeroBannerViewHolder

class BannerAdapter: HeroBannerAdapter<Banner>() {

    override fun bindData(holder: RecyclerView.ViewHolder, data: Banner) {
        val viewHolder = holder as? HeroBannerViewHolder ?: return
        viewHolder.bindImage(data.url)
    }

}