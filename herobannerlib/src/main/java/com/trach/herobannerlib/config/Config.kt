/**
 * Created by laivantrach1190@gmail.com
 * Copyright (c) 2019 . All rights reserved.
 */
package com.trach.herobannerlib.config

import android.content.Context
import com.trach.herobannerlib.R
import java.lang.IllegalStateException

data class Config(
    val isInfinite: Boolean,
    val isAutoScroll: Boolean,
    val isShowIndicator: Boolean,
    val indicatorSize: Int,
    val interval: Long
) {

    fun newBuilder(): Builder {
        return Builder()
            .infinite(isInfinite)
            .autoScroll(isAutoScroll)
            .indicatorSize(indicatorSize)
            .interval(interval)
            .showIndicator(isShowIndicator)
    }

    class Builder {
        private var isInfinite: Boolean = true
        private var isAutoScroll: Boolean = true
        private var isShowIndicator: Boolean = true
        private var indicatorSize: Int = -1
        private var interval: Long = 1500

        fun infinite(isInfinite: Boolean) = apply { this.isInfinite = isInfinite }
        fun autoScroll(isAutoScroll: Boolean) = apply { this.isAutoScroll = isAutoScroll }
        fun showIndicator(isShowIndicator: Boolean) = apply { this.isShowIndicator = isShowIndicator }
        fun indicatorSize(indicatorSize: Int) = apply { this.indicatorSize = indicatorSize }
        fun interval(interval: Long) = apply { this.interval = interval }

        fun build(context: Context?): Config {
            if (context == null) {
                throw IllegalStateException("context must be not null")
            }
            if (indicatorSize == -1) {
                indicatorSize = context.resources.getDimensionPixelSize(R.dimen.default_indicator_size)
            }
            return Config(
                isInfinite = isInfinite,
                isAutoScroll = isAutoScroll,
                isShowIndicator = isShowIndicator,
                indicatorSize = indicatorSize,
                interval = interval
            )
        }
    }

}