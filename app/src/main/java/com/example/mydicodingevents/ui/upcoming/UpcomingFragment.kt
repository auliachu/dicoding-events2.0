package com.example.mydicodingevents.ui.upcoming

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydicodingevents.databinding.FragmentUpcomingBinding
import com.example.mydicodingevents.ui.adapter.EventAdapter
import com.example.mydicodingevents.ui.detail.DetailActivity

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private lateinit var upcomingViewModel: UpcomingViewModel

    private val Evadapter = EventAdapter{ eventId ->
        Toast.makeText(requireContext(), "Clicked event ID: ${eventId}", Toast.LENGTH_SHORT).show()
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_EVENT_ID, eventId)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        upcomingViewModel =
            ViewModelProvider(this).get(UpcomingViewModel::class.java)

        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView(){
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvEvents.apply {
            this.layoutManager = layoutManager
            this.adapter = Evadapter

            val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
            addItemDecoration(itemDecoration)
        }
    }

    private fun observeViewModel(){
        upcomingViewModel.listEvent.observe(viewLifecycleOwner){ event ->
            if(event != null){
                Evadapter.submitList(event)
            }
        }

        upcomingViewModel.isLoading.observe(viewLifecycleOwner) {isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}