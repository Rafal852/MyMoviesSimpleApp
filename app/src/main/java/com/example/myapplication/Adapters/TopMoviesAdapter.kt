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
import com.example.myapplication.databinding.ItemTopMoviesBinding
import com.example.myapplication.utils.Constants.POSTER_BASE_URL
import com.example.myapplication.api.response.TopMovieList

import javax.inject.Inject

class TopMoviesAdapter @Inject() constructor() :
    PagingDataAdapter<TopMovieList.Result, TopMoviesAdapter.ViewHolder>(differCallback) {

    private lateinit var binding: ItemTopMoviesBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemTopMoviesBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: TopMovieList.Result) {
            binding.apply {
                tvMovieName2.text = item.title
                tvMovieDateRelease2.text = item.release_date
                tvRate2.text=item.vote_average.toString()
                val moviePosterURL = POSTER_BASE_URL + item?.poster_path
                imgMovie2.load(moviePosterURL){
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }
                tvLang2.text=item.original_language

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((TopMovieList.Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (TopMovieList.Result) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<TopMovieList.Result>() {
            override fun areItemsTheSame(oldItem: TopMovieList.Result, newItem: TopMovieList.Result): Boolean {
                return oldItem.id == oldItem.id
            }

            override fun areContentsTheSame(oldItem: TopMovieList.Result, newItem: TopMovieList.Result): Boolean {
                return oldItem == newItem
            }
        }
    }

}