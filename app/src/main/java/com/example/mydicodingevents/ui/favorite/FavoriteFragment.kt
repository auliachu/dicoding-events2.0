package com.example.mydicodingevents.ui.favorite

import ViewModelFactory
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydicodingevents.data.response.ListEventsItem
import com.example.mydicodingevents.databinding.FragmentFavoriteBinding
import com.example.mydicodingevents.ui.adapter.EventAdapter
import com.example.mydicodingevents.ui.detail.DetailActivity

class FavoriteFragment : Fragment() {

    private var _fragmentFavoriteBinding : FragmentFavoriteBinding? = null
    private val binding get() = _fragmentFavoriteBinding!!
    private lateinit var eventAdapter: EventAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    companion object{
        private const val TAG = "FavoriteFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventAdapter = EventAdapter()
        binding.rvEvents.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvEvents.addItemDecoration(
            DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)
        )
        binding.rvEvents.adapter = eventAdapter
        favoriteViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireActivity().application))[FavoriteViewModel::class.java]

        fetchFavorites()
    }

    private fun fetchFavorites(){
        showLoading(true)
        favoriteViewModel.getAllFavoriteEvents().observe(viewLifecycleOwner){ favorite ->
            val items = arrayListOf<ListEventsItem>()
            favorite.map {
                val item =
                    ListEventsItem(id = it.id.toInt(), name = it.name, imageLogo = it?.imageLogo.toString(), summary = it?.summary.toString(), ownerName = it?.ownerName.toString(), quota = it?.quota!!.toInt() , description = it?.description.toString(), registrants = it?.registrant!!.toInt(), mediaCover = it?.mediaCover.toString(), beginTime = it?.beginTime.toString(), link = it?.link.toString(), cityName = "city", category = "category", endTime = "endTime")
                items.add(item)
            }
            eventAdapter.submitList(items)
            showLoading(false)
        }
    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else{
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentFavoriteBinding = null
    }

}