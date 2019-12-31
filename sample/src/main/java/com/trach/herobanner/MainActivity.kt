package com.trach.herobanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.trach.herobannerlib.HeroBanner
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.widget.SwitchCompat
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        HeroBanner.init(PicassoImageLoadingService())
        val adapter = BannerAdapter().apply {
            setItemList(listOf(
                Banner("https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg"),
                Banner("https://assets.materialup.com/uploads/20ded50d-cc85-4e72-9ce3-452671cf7a6d/preview.jpg"),
                Banner("https://assets.materialup.com/uploads/76d63bbc-54a1-450a-a462-d90056be881b/preview.png")
            ))
        }
        heroBanner.setAdapter(adapter)

        setupSettingsUi()
    }

    override fun onPause() {
        super.onPause()
        heroBanner.stopTimer()
    }

    override fun onResume() {
        super.onResume()
        heroBanner.startTimer()
    }

    private fun setupSettingsUi() {
        seekBarInterval.max = 10000
        seekBarInterval.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, time: Int, changed: Boolean) {
                if (changed) {
                    heroBanner.setInterval(time.toLong())
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) { }
            override fun onStopTrackingTouch(seekBar: SeekBar) { }
        })
        seekBarIndicatorSize.max = resources.getDimensionPixelSize(R.dimen.max_indicator_size)
        seekBarIndicatorSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, changed: Boolean) {
                if (changed) {
                    heroBanner.setIndicatorSize(i)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })

        isInfinite.isChecked = true
        isInfinite.setOnCheckedChangeListener { _, b -> heroBanner.isInfinite(b) }

        val hideIndicatorsSwitch = findViewById<SwitchCompat>(R.id.checkbox_hide_indicators)
        hideIndicatorsSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                heroBanner.hideIndicators()
            } else {
                heroBanner.showIndicators()
            }
        }
    }
}
