package com.example.mydicodingevents.ui.favorite

import FavoriteEventAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydicodingevents.data.response.ListEventsItem
import com.example.mydicodingevents.databinding.FragmentFavoriteBinding
import com.example.mydicodingevents.ui.adapter.EventAdapter
import com.example.mydicodingevents.ui.detail.DetailActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FavoriteFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    companion object{
        const val TAG = "Favorite_Fragment"
    }

    private var _fragmentFavoriteBinding : FragmentFavoriteBinding? = null
    private val binding get() = _fragmentFavoriteBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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

    }

    private fun submitList(event: List<ListEventsItem>){
        val favoriteEventAdapter = FavoriteEventAdapter(event)
        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEvents.adapter = favoriteEventAdapter
        favoriteEventAdapter.submitList(event)

        favoriteEventAdapter.setOnItemClickCallback(object: FavoriteEventAdapter.OnItemClickCallback {
            override fun onItemCallback(data: ListEventsItem) {
                val context = binding.root.context
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_EVENT_ID, data.id)
                startActivity(intent)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentFavoriteBinding = null
    }

}