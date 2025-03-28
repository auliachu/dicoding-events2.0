package com.example.mydicodingevents.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.mydicodingevents.databinding.FragmentHomeBinding
import com.example.mydicodingevents.ui.adapter.EventAdapter
import com.example.mydicodingevents.ui.adapter.SwipeEventAdapter
import com.example.mydicodingevents.ui.detail.DetailActivity
import com.google.android.material.search.SearchBar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    private val eventAdapter = SwipeEventAdapter{ eventId ->
        Toast.makeText(requireContext(), "Clicked Event ID : ${eventId}", Toast.LENGTH_SHORT).show()
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_EVENT_ID, eventId)
        startActivity(intent)
    }

    private val scrollEventAdapter = EventAdapter{ eventId ->
        Toast.makeText(requireContext(), "Clicked Event ID : ${eventId}", Toast.LENGTH_SHORT).show()
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_EVENT_ID, eventId)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }



    private fun setupRecyclerView(){
        val horizontallayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.swipeEvents.apply {
            this.layoutManager = horizontallayoutManager
            this.adapter = eventAdapter

            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(this)
        }

        val verticallayoutManager = LinearLayoutManager(requireContext())
        binding.scrollEvents.apply {
            this.layoutManager = verticallayoutManager
            this.adapter = scrollEventAdapter
        }
    }

    private fun observeViewModel (){
        homeViewModel.upcomingListEvents.observe(viewLifecycleOwner){event ->
            if (event != null ){
                eventAdapter.submitList(event)
            }
        }

        homeViewModel.finishedListEvents.observe(viewLifecycleOwner){event ->
            if (event!= null) {
                scrollEventAdapter.submitList(event)
            }
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}