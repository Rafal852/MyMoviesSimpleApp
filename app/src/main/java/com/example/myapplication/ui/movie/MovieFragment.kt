package com.example.myapplication.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState


import androidx.recyclerview.widget.LinearLayoutManager

import com.example.myapplication.databinding.FragmentMovieBinding

import com.example.myapplication.viewModel.MoviesViewModel
import com.example.myapplication.Adapters.LoadMoreAdapter
import com.example.myapplication.Adapters.MoviesAdapter
import com.example.myapplication.Adapters.TopMoviesAdapter
import com.example.myapplication.Adapters.UpcomingMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding

    @Inject
    lateinit var moviesAdapter: MoviesAdapter

    @Inject
    lateinit var topMoviesAdapter: TopMoviesAdapter

    @Inject
    lateinit var upcomingMoviesAdapter: UpcomingMoviesAdapter



    private val viewModel: MoviesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Popular Movies

        binding.apply {

            lifecycleScope.launchWhenCreated {
                viewModel.moviesList.collect {
                    moviesAdapter.submitData(it)
                }
            }

            moviesAdapter.setOnItemClickListener {
                val direction = MoviesFragmentDirections.actionMovieFragmentToMovieDetailsFragment(it.id)
                findNavController().navigate(direction)
            }

            lifecycleScope.launchWhenCreated {
                moviesAdapter.loadStateFlow.collect{
                    val state = it.refresh
                    prgBarMovies.isVisible = state is LoadState.Loading
                }
            }


            rlPopularMovies.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = moviesAdapter
            }

            rlPopularMovies.adapter=moviesAdapter.withLoadStateFooter(
                LoadMoreAdapter{
                    moviesAdapter.retry()
                }
            )

        }


        //Top Rated Movies

        binding.apply {
            lifecycleScope.launchWhenCreated {
                viewModel.topMoviesList.collect{
                    topMoviesAdapter.submitData(it)
                }
            }


            topMoviesAdapter.setOnItemClickListener {
                val direction = MoviesFragmentDirections.actionMovieFragmentToMovieDetailsFragment(it.id)
                findNavController().navigate(direction)
            }

            lifecycleScope.launchWhenCreated {
                topMoviesAdapter.loadStateFlow.collect{
                    val state = it.refresh
                    prgBarMovies.isVisible = state is LoadState.Loading
                }
            }


            rlTopMovies.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = topMoviesAdapter
            }

            rlTopMovies.adapter=topMoviesAdapter.withLoadStateFooter(
                LoadMoreAdapter{
                    topMoviesAdapter.retry()
                }
            )
        }

        //Upcoming Movies

        binding.apply {
            lifecycleScope.launchWhenCreated {
                viewModel.upcomingMoviesList.collect{
                    upcomingMoviesAdapter.submitData(it)
                }
            }


            upcomingMoviesAdapter.setOnItemClickListener {
                val direction = MoviesFragmentDirections.actionMovieFragmentToMovieDetailsFragment(it.id)
                findNavController().navigate(direction)
            }

            lifecycleScope.launchWhenCreated {
                upcomingMoviesAdapter.loadStateFlow.collect{
                    val state = it.refresh
                    prgBarMovies.isVisible = state is LoadState.Loading
                }
            }


            rlUpcomingMovies.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = upcomingMoviesAdapter
            }

            rlUpcomingMovies.adapter=upcomingMoviesAdapter.withLoadStateFooter(
                LoadMoreAdapter{
                    upcomingMoviesAdapter.retry()
                }
            )
        }

    }

}