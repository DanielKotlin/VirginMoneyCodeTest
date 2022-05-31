package com.daniel.codetest.ui.post

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniel.codetest.databinding.PostAdapterBinding
import com.daniel.codetest.domain.model.Posts

/**
 * Created by Daniel.
 */
class PostsAdapter(
    private val mContext: Context,
    private val posts: Posts
) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemBinding = PostAdapterBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return PostViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val model = posts[position]
        holder.itemBinding.textTitle.text = model.title
        holder.itemBinding.textBody.text = model.body
    }

    /*
    * Posts for binding Recyclerview
    *
    * @param itemBinding binding the Post UI elements
    * */
    class PostViewHolder(val itemBinding: PostAdapterBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}