package com.daniel.codetest.ui.post

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
import com.daniel.codetest.domain.model.Posts

/**
 * Created by Daniel.
 */
class PostFragment : Fragment() {

    private var _binding: SwipeRecyclerViewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var postViewModel: PostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]

        _binding = SwipeRecyclerViewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        postViewModel.getPosts(requireActivity())

        postViewModel.allPosts.observe(viewLifecycleOwner) { posts ->
            posts?.let { setUI(it) }
        }
        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUI(postsData: Posts) {
        _binding?.recyclerView?.layoutManager = LinearLayoutManager(requireActivity())
        _binding?.recyclerView?.addItemDecoration(
            DividerItemDecoration(
                _binding?.recyclerView?.context,
                (_binding?.recyclerView?.layoutManager as LinearLayoutManager).orientation
            )
        )
        _binding?.recyclerView?.recycledViewPool?.setMaxRecycledViews(0, 100)
        _binding?.recyclerView?.setItemViewCacheSize(100)

        val postAdapter = PostsAdapter(requireActivity(), postsData)
        _binding?.recyclerView?.adapter = PostsAdapter(requireActivity(), postsData)
        _binding?.recyclerView?.invalidate()
        postAdapter.notifyDataSetChanged()

        // SetOnRefreshListener on SwipeRefreshLayout
        _binding?.swipeContainer?.setOnRefreshListener {
            _binding?.swipeContainer?.isRefreshing = false
            postViewModel.getPosts(requireActivity())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}