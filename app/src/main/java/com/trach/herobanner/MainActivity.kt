package com.trach.herobanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.trach.herobannerlib.HeroBanner
import com.trach.herobannerlib.services.ImageLoadingService
import kotlinx.android.synthetic.main.activity_main.*

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
    }

    override fun onPause() {
        super.onPause()
        heroBanner.stopTimer()
    }

    override fun onResume() {
        super.onResume()
        heroBanner.startTimer()
    }
}
