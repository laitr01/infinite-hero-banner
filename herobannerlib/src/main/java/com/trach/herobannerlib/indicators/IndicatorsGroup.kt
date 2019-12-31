/**
 * Created by laivantrach1190@gmail.com
 * Copyright (c) 2019 . All rights reserved.
 */
package com.trach.herobannerlib.indicators

import android.annotation.SuppressLint
import android.view.Gravity
import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.os.Build
import android.graphics.drawable.Drawable
import android.util.Log
import com.trach.herobannerlib.HeroBanner
import com.trach.herobannerlib.R

@SuppressLint("ViewConstructor")
class IndicatorsGroup(
    context: Context,
    private val selectedSlideIndicator: Drawable?,
    private val unselectedSlideIndicator: Drawable?,
    private val indicatorSize: Int
) : LinearLayout(context), HeroBanner.IndicatorChangeListener {

    private val indicatorShapes = ArrayList<HeroIndicator>()

    init {
        orientation = HORIZONTAL
        val layoutParams =
            FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        val margin = resources.getDimensionPixelSize(R.dimen.default_indicator_margin) * 2
        layoutParams.setMargins(0, 0, 0, margin)
        setLayoutParams(layoutParams)
    }

    fun setIndicators(count: Int) {
        removeAllViews()
        indicatorShapes.clear()
        for (i in 0 until count) {
            addIndicator()
        }
    }

    @Suppress("DEPRECATION")
    private fun addIndicator() {
        val indicatorShape: HeroIndicator
        if (selectedSlideIndicator != null && unselectedSlideIndicator != null) {
            indicatorShape = object : HeroIndicator(context, indicatorSize) {
                override fun onCheckedChange(isChecked: Boolean) {
                    val drawableId = if (isChecked) selectedSlideIndicator else unselectedSlideIndicator
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        background = drawableId
                    } else {
                        setBackgroundDrawable(drawableId)
                    }
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                indicatorShape.setBackground(unselectedSlideIndicator)
            } else {
                indicatorShape.setBackgroundDrawable(unselectedSlideIndicator)
            }
            indicatorShapes.add(indicatorShape)
            addView(indicatorShape)

        } else {
            indicatorShape = CircleIndicator(context, indicatorSize)
            indicatorShapes.add(indicatorShape)
            addView(indicatorShape)
        }
    }

    override fun onIndicatorChange(position: Int) {
        Log.i(TAG, "onBannerChange: $position")
        for (i in indicatorShapes.indices) {
            if (i == position) {
                indicatorShapes[i].onCheckedChange(true)
            } else {
                indicatorShapes[i].onCheckedChange(false)
            }
        }
    }

    companion object {
        private const val TAG = "IndicatorsGroup"
    }

}