package com.example.mydicodingevents.ui.detail

import DetailViewModel
import ViewModelFactory
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mydicodingevents.R
import com.example.mydicodingevents.data.database.FavoriteEvent
import com.example.mydicodingevents.data.response.Event
import com.example.mydicodingevents.data.response.ListEventsItem
import com.example.mydicodingevents.databinding.ActivityDetailBinding
import com.example.mydicodingevents.ui.favorite.FavoriteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class DetailActivity : AppCompatActivity() {
    private var binding: ActivityDetailBinding? = null
    private lateinit var favoriteEventViewModel: FavoriteViewModel
    private var isFavorite = false
    private val detailViewModel : DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //favoriteEventViewModel
        favoriteEventViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(application))[FavoriteViewModel::class.java]

        //ambil event
        val event = if(Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra<ListEventsItem>("EVENT_DETAIL", ListEventsItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<ListEventsItem>("EVENT_DETAIL")
        }

        if (event != null){
            showLoading(true)

            detailViewModel.findEvent(event.id.toString())

            detailViewModel.event.observe(this) { event ->
                Log.d("DetailActivity", "observer : $event")
                showLoading(false)
                if (event != null){
                    setEventData(event)
                    favoriteEventViewModel.getFavoriteEventById(event.id).observe(this){favorite ->
                        isFavorite = favorite !=null
                        updateFavoriteButton()
                    }
                } else {
                    Log.e("DetailEvent", "Data tidak ditemukan")
                }
            }
        }

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }
    }

    private fun updateFavoriteButton(){
        if (isFavorite){
            binding?.favButton?.setImageResource(R.drawable.ic_favorite_items)
        } else {
            binding?.favButton?.setImageResource(R.drawable.ic_favorite_empty)
        }
    }

    private fun setEventData(event: Event) {
        val totalQuota = (event.quota) - (event.registrants )
        binding?.apply {
            Glide.with(this@DetailActivity)
                .load(event.mediaCover)
                .into(imageEvent)
            descEvent.text = HtmlCompat.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
            titleEvent.text = event.name
            sisaQuota.text = totalQuota.toString()
            summaryEvent.text = event.summary
            textCategory.text = event.category
            ownerName.text = event.ownerName
            cityName.text = event.cityName
            registrant.text = event.registrants.toString()
            quota.text = event.quota.toString()
            beginTime.text = event.beginTime
            endTime.text = event.endTime

            buttonLink.setOnClickListener{
                val eventLink = event.link
                if(!eventLink.isNullOrEmpty()){
                    openLink(eventLink)
                } else{
                    Toast.makeText(this@DetailActivity, "URL tidak ada atau tidak valid", Toast.LENGTH_SHORT).show()
                }
            }

            favButton?.setOnClickListener{
                if (isFavorite){
                    favoriteEventViewModel.delete(FavoriteEvent(event.id, event.name, event.mediaCover, event.imageLogo, event.summary, event.ownerName, event.quota.toString(), event.description, event.registrants, event.beginTime, event.link))
                } else{
                    favoriteEventViewModel.insert(FavoriteEvent(event.id, event.name, event.mediaCover, event.imageLogo, event.summary, event.ownerName, event.quota.toString(), event.description, event.registrants, event.beginTime, event.link))
                }
            }
        }
    }

    private fun openLink(link: String){
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(link)
        }
        try {
            startActivity(intent)
        } catch (e: Exception){
            Toast.makeText(this, "Gagal membuka link", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "onFailure: ${e.message}")
        }
    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading){
            binding?.progressBar?.visibility = View.VISIBLE
        } else{
            binding?.progressBar?.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        private const val TAG = "DetailActivity"
        const val EXTRA_EVENT_ID = "extra_event_id"
    }
}