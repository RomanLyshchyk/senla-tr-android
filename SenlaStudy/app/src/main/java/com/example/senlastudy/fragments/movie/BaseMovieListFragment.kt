package com.example.senlastudy.fragments.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.senlastudy.MovieApplication
import com.example.senlastudy.adapter.MovieAdapter
import com.example.senlastudy.databinding.FragmentMovieListBinding
import com.example.senlastudy.fragments.BaseFragment
import com.example.senlastudy.presenter.MovieListContract
import com.example.senlastudy.retrofit.pojo.Movie

abstract class BaseMovieListFragment :
    BaseFragment<MovieListContract.PresenterMovieList, MovieListContract.ViewMovieList>(),
    MovieListContract.ViewMovieList,
    MovieAdapter.OnMovieClickListener {

    private var page = 1
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private val adapter: MovieAdapter by lazy { MovieAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializationAttributes()
    }

    private fun initializationAttributes() {
        binding.rvMovieList.adapter = adapter
        binding.rvMovieList.setHasFixedSize(true)
        binding.rvMovieList.layoutManager =
            LinearLayoutManager(requireContext())
        binding.rvMovieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    page++
                    getMovie()
                }
            }

        })
    }

    override fun setData(movie: List<Movie>) {
        adapter.setData(movie)
        adapter.notifyDataSetChanged()
        binding.noMovie.isVisible = false
        binding.rvMovieList.isVisible = true
    }

    override fun errorResponse(error: Throwable) {
        binding.noMovie.isVisible = true
        binding.rvMovieList.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMovieClick(movie: Movie) {
        val activity = (context as NavigationFragment)
        activity.openMovieDetail(movie)
    }

    interface NavigationFragment {
        fun openMovieDetail(movie: Movie)
    }

    protected fun getPage(): Int {
        return page
    }

    protected fun getMovie() {
        getPresenter().downloadingMovieList(
            getPage()
        )
    }

}