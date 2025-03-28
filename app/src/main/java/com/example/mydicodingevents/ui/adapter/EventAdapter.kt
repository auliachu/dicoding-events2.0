package com.example.mydicodingevents.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mydicodingevents.data.response.ListEventsItem
import com.example.mydicodingevents.databinding.ItemEventBinding

class EventAdapter (private val onClick: (String) -> Unit) : ListAdapter<ListEventsItem ,EventAdapter.EventViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }


    class EventViewHolder(private val binding: ItemEventBinding, private val onClick: (String) -> Unit) : RecyclerView.ViewHolder(binding.root){
        fun bind(event : ListEventsItem) {
            binding.apply {
                Glide.with(root.context)
                    .load(event.mediaCover)
                    .into(eventImage)
                eventTitle.text = event.name
                val eventId = event.id
                root.setOnClickListener{
                    onClick(eventId.toString())
                }
            }
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}