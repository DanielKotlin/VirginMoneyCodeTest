package com.daniel.codetest.presentation.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniel.codetest.R
import com.daniel.codetest.databinding.RoominfoDialogBinding
import com.daniel.codetest.databinding.SwipeRecyclerViewBinding
import com.daniel.codetest.localdb.PeoplesInfoListItem
import com.daniel.codetest.presentation.interfaces.PeopleItemClickListener
import com.daniel.codetest.utils.CommonUtils
import com.squareup.picasso.Picasso

/**
 * Created by Daniel.
 */
class HomeFragment : Fragment(), PeopleItemClickListener {

    private var _binding: SwipeRecyclerViewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = SwipeRecyclerViewBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        pullUsersData()

        homeViewModel.allUsers.observe(viewLifecycleOwner) { usersList ->
            setUI(usersList)
        }
        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUI(usersData: List<PeoplesInfoListItem>) {
        _binding?.recyclerView?.layoutManager = LinearLayoutManager(requireActivity())
        _binding?.recyclerView?.addItemDecoration(
            DividerItemDecoration(
                _binding?.recyclerView?.context,
                (_binding?.recyclerView?.layoutManager as LinearLayoutManager).orientation
            )
        )
        _binding?.recyclerView?.recycledViewPool?.setMaxRecycledViews(0, 100)
        _binding?.recyclerView?.setItemViewCacheSize(100)

        val homeAdapter = HomeAdapter(requireActivity(), usersData, this)
        _binding?.recyclerView?.adapter = HomeAdapter(requireActivity(), usersData, this)
        _binding?.recyclerView?.invalidate()
        homeAdapter.notifyDataSetChanged()

        // SetOnRefreshListener on SwipeRefreshLayout
        _binding?.swipeContainer?.setOnRefreshListener {
            _binding?.swipeContainer?.isRefreshing = false
            pullUsersData()
        }
    }

    private fun pullUsersData() {
        if (CommonUtils.isNetworkConnected(requireActivity())) {
            homeViewModel.getUsers(requireActivity())
        } else {
            homeViewModel.getUsersDataFromRoom(requireActivity())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPeopleItemClickListener(model: PeoplesInfoListItem) {
        showRoomInfo(model)
    }

    /**
     *  This function used to show the popup for room information
     */
    @SuppressLint("SetTextI18n")
    private fun showRoomInfo(model: PeoplesInfoListItem) {
        val dialog = Dialog(requireActivity())
        val binding: RoominfoDialogBinding =
            RoominfoDialogBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        Picasso.get().load(model.avatar).into(binding.InfoPeoplePic)
        binding.Details.text = "Name: "+model.fromName + " " + model.lastName+"\nEmail: "+ model.email

        homeViewModel.getRoomInfo(requireActivity(), model.id)
        homeViewModel.roomInfo.observe(viewLifecycleOwner) { roomInfo ->
            roomInfo.forEach {
                binding.statusMessage.text = "Status: "+it.isOccupied+"\nMax Occupancy: "+it.maxOccupancy.toString()
                if (it.isOccupied) {
                    binding.statusMessage.setTextColor(ContextCompat.getColor(requireActivity(), R.color.status_green))
                } else {
                    binding.statusMessage.setTextColor(ContextCompat.getColor(requireActivity(), R.color.status_red))
                }
            }

        }
        binding.OkMessage.setOnClickListener {
            dialog.hide()
        }

        //now that the dialog is set up, it's time to show it
        dialog.show()
    }
}