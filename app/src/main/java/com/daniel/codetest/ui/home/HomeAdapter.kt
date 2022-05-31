package com.daniel.codetest.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniel.codetest.R
import com.daniel.codetest.databinding.HomeAdapterBinding
import com.daniel.codetest.domain.model.Users

/**
 * Created by Daniel.
 */
class HomeAdapter(
    private val mContext: Context,
    private val users: Users
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemBinding = HomeAdapterBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return HomeViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val model = users[position]
        holder.itemBinding.textName.text = model.name
        holder.itemBinding.textEmail.text = model.email
        if (model.status == mContext.resources.getString(R.string.status)) {
            holder.itemBinding.homeRoot.setBackgroundColor(mContext.getColor(R.color.green))
        } else {
            holder.itemBinding.homeRoot.setBackgroundColor(mContext.getColor(R.color.red))
        }

        if (model.gender == mContext.resources.getString(R.string.gender)) {
            holder.itemBinding.profilePic.setBackgroundResource(R.drawable.ic_user_male_avatar)
        } else {
            holder.itemBinding.profilePic.setBackgroundResource(R.drawable.ic_user_woman_avatar)
        }
    }

    /*
    * Used for binding Recyclerview
    *
    * @param itemBinding binding the Users UI elements
    * */
    class HomeViewHolder(val itemBinding: HomeAdapterBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}