package com.example.mydicodingevents.ui.adapter
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mydicodingevents.data.response.ListEventsItem
import com.example.mydicodingevents.databinding.SwipeEventItemBinding
import com.example.mydicodingevents.ui.detail.DetailActivity

class SwipeEventAdapter : ListAdapter<ListEventsItem ,SwipeEventAdapter.SwipeEventViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SwipeEventViewHolder {
        val binding = SwipeEventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SwipeEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SwipeEventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("EVENT_DETAIL", event)
            holder.itemView.context.startActivity(intentDetail)
        }
    }


    class SwipeEventViewHolder(private val binding : SwipeEventItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(event : ListEventsItem) {
            binding.apply {
                Glide.with(root.context)
                    .load(event.mediaCover)
                    .into(imageEvent)
                titleEvent.text = event.name
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