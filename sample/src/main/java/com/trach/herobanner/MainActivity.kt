package com.trach.herobanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.trach.herobannerlib.HeroBanner
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        HeroBanner.init(PicassoImageLoadingService())
        val adapter = BannerAdapter().apply {
            setItemList(
                listOf(
                    Banner("https://static.chotot.com.vn/storage/admin-centre/buyer_collection_y_homepage_banner/buyer_collection_y_homepage_banner_1577353107451.jpg"),
                    Banner("https://static.chotot.com.vn/storage/admin-centre/buyer_collection_y_homepage_banner/buyer_collection_y_homepage_banner_1577355774976.jpg"),
                    Banner("https://static.chotot.com.vn/storage/admin-centre/buyer_collection_y_homepage_banner/buyer_collection_y_homepage_banner_1577353202973.jpg")
                )
            )
        }
        heroBanner.setAdapter(adapter)

        setupSettingsControls()
    }

    override fun onPause() {
        super.onPause()
        heroBanner.stopTimer()
    }

    override fun onResume() {
        super.onResume()
        heroBanner.startTimer()
    }

    private fun setupSettingsControls() {
        seekBarInterval.max = 10000
        seekBarInterval.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, time: Int, changed: Boolean) {
                if (changed) heroBanner.setInterval(time.toLong())

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        seekBarIndicatorSize.max = resources.getDimensionPixelSize(R.dimen.max_indicator_size)
        seekBarIndicatorSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, size: Int, changed: Boolean) {
                if (changed) heroBanner.setIndicatorSize(size)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        isInfinite.isChecked = true
        isInfinite.setOnCheckedChangeListener { _, isChecked -> heroBanner.isInfinite(isChecked) }

        isAutoScroll.isChecked = true
        isAutoScroll.setOnCheckedChangeListener { _, isChecked -> heroBanner.isAutoScroll(isChecked) }

        hideIndicatorsSwitch.setOnCheckedChangeListener { _, isChecked -> heroBanner.showIndicators(isChecked) }
    }
}
