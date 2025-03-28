package com.example.mydicodingevents.ui.finished

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
import com.example.mydicodingevents.databinding.FragmentFinishedBinding
import com.example.mydicodingevents.ui.adapter.EventAdapter
import com.example.mydicodingevents.ui.detail.DetailActivity

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!
    private lateinit var finishedViewModel: FinishedViewModel

    private val finishedEventAdapter = EventAdapter { eventId : String ->
        Toast.makeText(requireContext(), "Clicked event ID : ${eventId}", Toast.LENGTH_SHORT).show()
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_EVENT_ID, eventId)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        finishedViewModel = ViewModelProvider(this).get(FinishedViewModel::class.java)
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView(){
        val layoutManager = LinearLayoutManager(requireContext())
        binding.finishEvent.apply {
            this.layoutManager = layoutManager
            this.adapter = finishedEventAdapter

            val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
            addItemDecoration(itemDecoration)
        }
    }

    private fun observeViewModel(){
        finishedViewModel.listEvent.observe(viewLifecycleOwner){ event ->
            if (event != null){
                finishedEventAdapter.submitList(event)
            }
        }

        finishedViewModel.isLoading.observe(viewLifecycleOwner){ isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}