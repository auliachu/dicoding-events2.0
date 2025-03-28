package com.example.mydicodingevents.ui.detail

import DetailViewModel
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mydicodingevents.R
import com.example.mydicodingevents.data.database.FavoriteEvent
import com.example.mydicodingevents.data.response.Event
import com.example.mydicodingevents.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {
    private var binding: ActivityDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //ambil eventId
        val eventId = intent.getStringExtra(EXTRA_EVENT_ID)

        if (eventId.isNullOrEmpty()){
            Toast.makeText(this, "EventID tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        // Observe loading status
        detailViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        // Fetch event detail
        detailViewModel.findEvent(eventId)

        // Observe event detail
        detailViewModel.event.observe(this) { event ->
            setEventData(event)
        }

    }

    private fun setEventData(event: Event?) {
        val totalQuota = (event?.quota ?:0) - (event?.registrants ?: 0)
        binding?.apply {
            Glide.with(this@DetailActivity)
                .load(event?.mediaCover)
                .into(imageEvent)
            descEvent.text = HtmlCompat.fromHtml(event?.description ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
            titleEvent.text = event?.name
            sisaQuota.text = totalQuota.toString()
            summaryEvent.text = event?.summary
            textCategory.text = event?.category
            ownerName.text = event?.ownerName
            cityName.text = event?.cityName
            registrant.text = event?.registrants.toString()
            quota.text = event?.quota.toString()
            beginTime.text = event?.beginTime
            endTime.text = event?.endTime

            buttonLink.setOnClickListener{
                val eventLink = event?.link
                if(!eventLink.isNullOrEmpty()){
                    openLink(eventLink)
                } else{
                    Toast.makeText(this@DetailActivity, "URL tidak ada atau tidak valid", Toast.LENGTH_SHORT).show()
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