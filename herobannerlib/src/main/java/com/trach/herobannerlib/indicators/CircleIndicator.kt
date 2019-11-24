/**
 * Created by laivantrach1190@gmail.com
 * Copyright (c) 2019 . All rights reserved.
 */
package com.trach.herobannerlib.indicators

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.core.content.res.ResourcesCompat
import com.trach.herobannerlib.R


@SuppressLint("ViewConstructor")
class CircleIndicator(context: Context, indicatorSize: Int) : HeroIndicator(context, indicatorSize) {
    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            background = ResourcesCompat.getDrawable(resources, R.drawable.indicator_circle_unselected, null)
        } else {
            setBackgroundDrawable(resources.getDrawable(R.drawable.indicator_circle_unselected))
        }
    }

    override fun onCheckedChange(isChecked: Boolean) {
        val drawableId = if (isChecked) R.drawable.indicator_circle_selected else R.drawable.indicator_circle_unselected
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            background = ResourcesCompat.getDrawable(resources, drawableId, null)
        } else {
            setBackgroundDrawable(resources.getDrawable(drawableId))
        }
    }

}