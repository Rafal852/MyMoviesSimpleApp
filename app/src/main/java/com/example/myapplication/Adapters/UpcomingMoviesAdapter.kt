package com.example.myapplication.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.myapplication.R
import com.example.myapplication.api.response.TopMovieList
import com.example.myapplication.api.response.UpcomingListResponse
import com.example.myapplication.databinding.ItemMoviesUpcomingBinding
import com.example.myapplication.utils.Constants
import javax.inject.Inject

class UpcomingMoviesAdapter @Inject() constructor() :
    PagingDataAdapter<UpcomingListResponse.Result, UpcomingMoviesAdapter.ViewHolder>(differCallback) {

    private lateinit var binding: ItemMoviesUpcomingBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemMoviesUpcomingBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: UpcomingListResponse.Result) {
            binding.apply {
                movieNameTxt.text = item.title
                movieInfoTxt.text=item.vote_average.toString()
                val moviePosterURL = Constants.POSTER_BACKDROP_URL + item?.backdrop_path
                moviePosterImg.load(moviePosterURL){
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FIT)
                }
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((UpcomingListResponse.Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (UpcomingListResponse.Result) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<UpcomingListResponse.Result>() {
            override fun areItemsTheSame(oldItem: UpcomingListResponse.Result, newItem: UpcomingListResponse.Result): Boolean {
                return oldItem.id == oldItem.id
            }

            override fun areContentsTheSame(oldItem: UpcomingListResponse.Result, newItem: UpcomingListResponse.Result): Boolean {
                return oldItem == newItem
            }
        }
    }

}