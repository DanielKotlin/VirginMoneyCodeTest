package com.daniel.codetest.presentation.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniel.codetest.databinding.HomeAdapterBinding
import com.daniel.codetest.localdb.PeoplesInfoListItem
import com.daniel.codetest.presentation.interfaces.PeopleItemClickListener
import com.squareup.picasso.Picasso

/**
 * Created by Daniel.
 */
class HomeAdapter(
    private val mContext: Context,
    private val users: List<PeoplesInfoListItem>,
    private val peopleItemClickListener: PeopleItemClickListener
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemBinding = HomeAdapterBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return HomeViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = users.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val model = users[position]
        holder.itemBinding.textName.text = model.fromName + " " + model.lastName
        holder.itemBinding.textEmail.text = model.email
        Picasso.get().load(model.avatar).into(holder.itemBinding.profilePic)

        holder.itemView.setOnClickListener {
            peopleItemClickListener.onPeopleItemClickListener(model)
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