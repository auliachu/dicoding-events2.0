import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mydicodingevents.data.database.FavoriteEvent
import com.example.mydicodingevents.data.response.DicodingDetailEventResponse
import com.example.mydicodingevents.data.response.Event
import com.example.mydicodingevents.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel(){
    private val _event = MutableLiveData<Event>()
    val event : LiveData<Event> = _event

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading


    companion object{
        private const val TAG = "DetailViewModel"

    }

   fun findEvent(eventId: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailEvent(eventId)
        client.enqueue(object : Callback<DicodingDetailEventResponse> {
            override fun onResponse(
                call: Call<DicodingDetailEventResponse>,
                response: Response<DicodingDetailEventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _event.value = response.body()?.event
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DicodingDetailEventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private val mFavoriteEventRepository: FavoriteEventRepository = FavoriteEventRepository(application)

    fun insert(event: FavoriteEvent){
        mFavoriteEventRepository.insert(event)
    }

    fun update(event: FavoriteEvent){
        mFavoriteEventRepository.updateEvent(event)
    }

    fun delete(event: FavoriteEvent){
        mFavoriteEventRepository.delete(event)
    }

    fun isFavoriteEventAdded(event: FavoriteEvent){
        mFavoriteEventRepository.isFavoriteEventAdded(event)
    }

}