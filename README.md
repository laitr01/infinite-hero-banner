# infinite-hero-banner
A simple, lightweight, infiniting hero banner is using viewpager 2.

<img src="https://i.ibb.co/cLW0PXC/80842295-600981997348032-1933579098244775936-n.jpg" width="48">

# How to use?

## Add dependence in gradle

```
repositories {
	maven {
		url  "https://dl.bintray.com/laitr01/infinite-hero-banner"
	}
}
```

```
implementation 'com.trach.herobannerlib:herobannerlib:1.1'
```

## Implementation

- Create your own model content image link

`data class Banner(val url: String)`

- Create an adapter extends from HeroBannerAdapter

```
class BannerAdapter: HeroBannerAdapter<Banner>() {

    override fun bindData(holder: RecyclerView.ViewHolder, data: Banner) {
        val viewHolder = holder as? HeroBannerViewHolder ?: return
        viewHolder.bindImage(data.url)
    }

}
```

- Create a ImageLoadingService for handling image loading, it depences on your image loading library

```
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
```

- Put the infinite banner in your xml file

```
<com.trach.herobannerlib.HeroBanner
            android:id="@+id/heroBanner"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:is_auto_scroll="true"
            app:is_infinite="true"
            app:is_show_indicator="true"
            app:indicator_size="7dp"
            app:interval="3000"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toTopOf="parent"/>
```

- Init image service and setup the infinite banner in fragment or activity

```
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
    
}
```

# Customization

## Make the infinite banner is auto scrolling

`app:is_auto_scroll="true"`

## Infinite scrolling

`app:is_infinite="true"`

## Show or hide indicator

`app:is_show_indicator="true"`
     
## Set size of indicators

`app:indicator_size="7dp"`
   
## Duration for auto scrolling

`app:interval="3000"`
