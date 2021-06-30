package com.example.senlastudy.presenter.upcoming

import com.example.senlastudy.MovieApplication
import com.example.senlastudy.presenter.BasePresenter
import com.example.senlastudy.presenter.MovieListContract
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class UpcomingPresenter() : BasePresenter<MovieListContract.ViewMovieList>(),
    MovieListContract.PresenterMovieList {

    override fun downloadingMovieList(page: Int) {
        getView().showViewLoading()
        getCompositeDisposable().add(
            MovieApplication.apiService.getUpcomingMovie(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response ->
                        getView().setData(response.results)
                        getView().hideViewLoading()
                    },
                    { t ->
                        getView().errorResponse(t)
                        getView().hideViewLoading()
                    })
        )
    }
}