/**
 * Created by laivantrach1190@gmail.com
 * Copyright (c) 2019 . All rights reserved.
 */
package com.trach.herobannerlib

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import com.trach.herobannerlib.config.Config
import java.util.*
import android.util.Log
import androidx.viewpager2.widget.ViewPager2.*
import com.trach.herobannerlib.adapters.HeroBannerAdapter
import com.trach.herobannerlib.services.ImageLoadingService
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.trach.herobannerlib.indicators.IndicatorsGroup

class HeroBanner : FrameLayout {

    private lateinit var config: Config
    private lateinit var viewPager2: ViewPager2
    private lateinit var timer: Timer

    private var adapter: HeroBannerAdapter<*>? = null
    private var indicatorsGroup: IndicatorsGroup? = null

    private var currentPagePosition = 0

    constructor(context: Context) : super(context) {
        initViews(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initViews(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initViews(attrs)
    }

    private fun initViews(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeroBanner)
        if (typedArray != null) {
            config = Config.Builder()
                .infinite(typedArray.getBoolean(R.styleable.HeroBanner_is_infinite, false))
                .autoScroll(typedArray.getBoolean(R.styleable.HeroBanner_is_auto_scroll, false))
                .indicatorSize(typedArray.getDimensionPixelSize(R.styleable.HeroBanner_indicator_size, 12))
                .showIndicator(typedArray.getBoolean(R.styleable.HeroBanner_is_show_indicator, false))
                .interval(typedArray.getInt(R.styleable.HeroBanner_interval, 1500).toLong())
                .build(context)
            typedArray.recycle()
        } else {
            config = Config.Builder().build(context)
        }
        setupViews()
    }

    private fun setupViews() {
        timer = Timer()
        if (config.isShowIndicator) {
            indicatorsGroup = IndicatorsGroup(
                context,
                resources.getDrawable(R.drawable.indicator_circle_selected),
                resources.getDrawable(R.drawable.indicator_circle_unselected),
                config.indicatorSize
            )
        }
        viewPager2 = ViewPager2(context)
        viewPager2.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrollStateChanged(state: Int) {
                if (state == SCROLL_STATE_IDLE) {
                    if (config.isInfinite) {
                        if (viewPager2.adapter == null) return
                        val itemCount = viewPager2.adapter?.itemCount ?: 0
                        if (itemCount < 2) {
                            return
                        }
                        val index = viewPager2.currentItem
                        if (index == 0) {
                            viewPager2.setCurrentItem(itemCount - 2, false)
                        } else if (index == itemCount - 1) {
                            viewPager2.setCurrentItem(1, false)
                        }
                    }
                }
            }

            override fun onPageSelected(position: Int) {
                onHeroBannerChange(position)
            }
        })
    }

    fun setAdapter(adapter: HeroBannerAdapter<*>) {
        if (::viewPager2.isInitialized) {
            this.adapter = adapter
            this.adapter?.imageViewLayoutParams = viewPager2.layoutParams

            if (indexOfChild(viewPager2) == -1) {
                addView(viewPager2)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                viewPager2.isNestedScrollingEnabled = false
            }

            adapter.setItemTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View, event: MotionEvent): Boolean {
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        stopTimer()
                    } else if (event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP) {
                        startTimer()
                    }
                    return false
                }
            })
            viewPager2.adapter = adapter

            if (config.isInfinite) {
                viewPager2.setCurrentItem(1, false)
            }

            if (indicatorsGroup != null && adapter.getListCount() > 1) {
                if (indexOfChild(indicatorsGroup) == -1) {
                    addView(indicatorsGroup)
                }
                indicatorsGroup!!.setIndicators(adapter.getListCount())
                indicatorsGroup!!.onIndicatorChange(0)
            }
        }
    }

    fun onHeroBannerChange(position: Int) {
        Log.d("HeroBanner", "onHeroBannerChange() --->>> position = [$position]")
        currentPagePosition = position
        val adapter = adapter as? HeroBannerAdapter ?: return
        val userSlidePosition = adapter.getListPosition(position)
        indicatorsGroup?.onIndicatorChange(userSlidePosition)
    }

    interface IndicatorChangeListener {
        fun onIndicatorChange(position: Int)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startTimer()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopTimer()
    }


    fun startTimer() {
        if (config.interval > 0) {
            stopTimer()
            timer = Timer()
            timer.schedule(SliderTimerTask(), config.interval, config.interval)
        }
    }

    private inner class SliderTimerTask : TimerTask() {
        override fun run() {
            (context as? Activity)?.runOnUiThread {
                val itemCount = viewPager2.adapter?.itemCount ?: 0
                if (viewPager2.adapter == null || !config.isAutoScroll ||
                    itemCount < 2 && HeroBanner.imageLoadingService != null
                ) return@runOnUiThread

                if (!config.isInfinite && itemCount - 1 == currentPagePosition) {
                    currentPagePosition = 0
                } else {
                    currentPagePosition++
                }
                viewPager2.setCurrentItem(currentPagePosition, true)
            }
        }
    }

    fun stopTimer() {
        if (::timer.isInitialized) {
            timer.cancel()
            timer.purge()
        }
    }

    fun setInterval(interval: Long) {
        config = config.newBuilder()
            .interval(interval)
            .build(context)
        resetAutoScroll()
    }

    private fun resetAutoScroll() {
        stopTimer()
        startTimer()
    }

    companion object {

        private var imageLoadingService: ImageLoadingService? = null

        fun init(imageLoadingService: ImageLoadingService) {
            HeroBanner.imageLoadingService = imageLoadingService
        }

        fun getImageLoadingService(): ImageLoadingService {
            return imageLoadingService
                ?: throw IllegalStateException("ImageLoadingService must be not null, you should call init method first")
        }
    }
}