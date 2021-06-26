package com.example.senlastudy.presenter.rated

import com.example.senlastudy.MovieApplication
import com.example.senlastudy.presenter.BasePresenter
import com.example.senlastudy.presenter.MovieListContract
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class BaseTopRatedPresenter : BasePresenter<MovieListContract.ViewMovieList>(),
    MovieListContract.PresenterMovieList {

    override fun downloadingMovieList(language: String, page: Int) {


        getCompositeDisposable().add(
            MovieApplication.apiService.getTopRatedMovie(language, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response -> getView().setData(response.results) },
                    { t -> getView().errorResponse(t) })
        )

    }


}