package com.daniel.codetest.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniel.codetest.databinding.SwipeRecyclerViewBinding
import com.daniel.codetest.domain.model.Users

/**
 * Created by Daniel.
 */
class HomeFragment : Fragment() {

    private var _binding: SwipeRecyclerViewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        homeViewModel  = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = SwipeRecyclerViewBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        homeViewModel.getUsers(requireActivity())

        homeViewModel.allUsers.observe(viewLifecycleOwner) { user ->
            user?.let { setUI(it) }
        }
        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUI(usersData: Users) {
        _binding?.recyclerView?.layoutManager = LinearLayoutManager(requireActivity())
        _binding?.recyclerView?.addItemDecoration(
            DividerItemDecoration(
                _binding?.recyclerView?.context,
                (_binding?.recyclerView?.layoutManager as LinearLayoutManager).orientation
            )
        )
        _binding?.recyclerView?.recycledViewPool?.setMaxRecycledViews(0, 100)
        _binding?.recyclerView?.setItemViewCacheSize(100)

        val homeAdapter = HomeAdapter(requireActivity(), usersData)
        _binding?.recyclerView?.adapter = HomeAdapter(requireActivity(), usersData)
        _binding?.recyclerView?.invalidate()
        homeAdapter.notifyDataSetChanged()

        // SetOnRefreshListener on SwipeRefreshLayout
        _binding?.swipeContainer?.setOnRefreshListener {
            _binding?.swipeContainer?.isRefreshing = false
            homeViewModel.getUsers(requireActivity())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}