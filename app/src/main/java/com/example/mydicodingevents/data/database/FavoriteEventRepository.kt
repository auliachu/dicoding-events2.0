import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mydicodingevents.data.database.EventRoomDatabase
import com.example.mydicodingevents.data.database.FavoriteEvent
import com.example.mydicodingevents.data.database.FavoriteEventDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteEventRepository(application: Application){
    private val mFavoriteEventDao : FavoriteEventDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    //buat database nya
    init {
        val db = EventRoomDatabase.getDatabase(application)
        mFavoriteEventDao = db.favDao()
    }

    fun getAllFavoriteEvents() : LiveData<List<FavoriteEvent>> = mFavoriteEventDao.getFavorite()

    fun insert(event: FavoriteEvent){
        executorService.execute{mFavoriteEventDao.insert(event)}
    }

    fun delete(event: FavoriteEvent){
        executorService.execute{mFavoriteEventDao.deleteFavoriteEvent(event.id)}
    }

    fun updateEvent(event: FavoriteEvent){
        executorService.execute{mFavoriteEventDao.updateEvent(event)}
    }

    fun isFavoriteEventAdded(event: FavoriteEvent){
        mFavoriteEventDao.isFavoriteEventAdd(event.id)
    }

    fun getDataEventFavorite(event: FavoriteEvent) : LiveData<FavoriteEvent> = mFavoriteEventDao.getData(event.id)
}