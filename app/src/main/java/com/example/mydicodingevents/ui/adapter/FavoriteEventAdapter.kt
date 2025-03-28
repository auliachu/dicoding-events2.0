import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mydicodingevents.data.response.ListEventsItem
import com.example.mydicodingevents.databinding.ItemEventBinding

class FavoriteEventAdapter (private val listEvent: List<ListEventsItem>) : ListAdapter<ListEventsItem, FavoriteEventAdapter.MyEventHolder>(DIFF_CALLBACK){
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class MyEventHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(event:ListEventsItem){
            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .into(binding.eventImage)

            binding.eventTitle.text = event.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyEventHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyEventHolder(binding)
    }

    override fun onBindViewHolder(holder: MyEventHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)

        holder.itemView.setOnClickListener{onItemClickCallback.onItemCallback(listEvent[position])}
    }

    interface OnItemClickCallback{
        fun onItemCallback(data:ListEventsItem)
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>(){
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