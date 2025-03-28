package com.example.mydicodingevents.ui.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mydicodingevents.data.response.ListEventsItem
import com.example.mydicodingevents.databinding.SwipeEventItemBinding

class SwipeEventAdapter (private val onClick: (String) -> Unit) : ListAdapter<ListEventsItem ,SwipeEventAdapter.SwipeEventViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SwipeEventViewHolder {
        val binding = SwipeEventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SwipeEventViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: SwipeEventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }


    class SwipeEventViewHolder(private val binding : SwipeEventItemBinding, private val onClick: (String) -> Unit) : RecyclerView.ViewHolder(binding.root){
        fun bind(event : ListEventsItem) {
            binding.apply {
                Glide.with(root.context)
                    .load(event.mediaCover)
                    .into(imageEvent)
                titleEvent.text = event.name
                val eventId = event.id.toString()
                root.setOnClickListener{
                    onClick(eventId)
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