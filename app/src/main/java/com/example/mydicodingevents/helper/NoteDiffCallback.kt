import androidx.recyclerview.widget.DiffUtil
import com.example.mydicodingevents.data.database.FavoriteEvent

class NoteDiffCallback (private val oldEventList: List<FavoriteEvent>, private val newEventList: List<FavoriteEvent>) : DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldEventList.size
    override fun getNewListSize(): Int = newEventList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldEventList[oldItemPosition].id == newEventList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEvent = oldEventList[oldItemPosition]
        val newEvent = newEventList[newItemPosition]
        return oldEvent.name == newEvent.name && oldEvent.mediaCover == newEvent.mediaCover
    }
}