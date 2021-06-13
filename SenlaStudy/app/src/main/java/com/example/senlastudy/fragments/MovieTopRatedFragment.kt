package com.example.senlastudy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.senlastudy.MovieApplication
import com.example.senlastudy.adapter.MovieAdapter
import com.example.senlastudy.databinding.FragmentMovieTopRatedBinding
import com.example.senlastudy.presenter.rated.MovieTopRatedPresenter
import com.example.senlastudy.retrofit.pojo.Movie
import com.example.senlastudy.view.MainContract


class MovieTopRatedFragment : Fragment(), MainContract.IMovieView, MovieAdapter.OnMovieClickListener {

    val movieTopRatedPresenter: MovieTopRatedPresenter by lazy { MovieTopRatedPresenter(this) }
    private val adapter: MovieAdapter by lazy { MovieAdapter(this) }
    private var _binding: FragmentMovieTopRatedBinding? = null
    private val binding get() = _binding!!
    private var page = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieTopRatedBinding.inflate(inflater, container, false)

        setupRecyclerView()
        movieTopRatedPresenter.downloadingMovieList(
            MovieApplication.localLanguage,
            page
        )

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.apply {
            rvMovieList.adapter = adapter
            rvMovieList.setHasFixedSize(true)
            rvMovieList.layoutManager =
                LinearLayoutManager(requireContext())
            rvMovieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        page++
                        movieTopRatedPresenter.downloadingMovieList(
                            MovieApplication.localLanguage,
                            page
                        )
                    }
                }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        movieTopRatedPresenter.detach()
    }

    override fun setData(movie: List<Movie>) {
        binding.noMovie.isVisible = false
        binding.rvMovieList.isVisible = true
        adapter.setData(movie)
        adapter.notifyDataSetChanged()
    }

    override fun errorResponse(error: Throwable) {
        binding.noMovie.isVisible = true
        binding.rvMovieList.isVisible = false
    }

    override fun onMovieClick(movie: Movie) {
        Toast.makeText(requireContext(), movie.title, Toast.LENGTH_SHORT).show()
    }


}