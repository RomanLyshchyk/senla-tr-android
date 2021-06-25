package com.example.senlastudy.presenter.playing

import com.example.senlastudy.MovieApplication
import com.example.senlastudy.presenter.MainContract
import com.example.senlastudy.presenter.MoviePresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieNowPlayingPresenter(private val iMovieNowPlayingView: MainContract.IMovieView) :
    MoviePresenter<MainContract.IMovieView>(), MainContract {

    private val disposables: CompositeDisposable = CompositeDisposable()

    fun downloadingMovieList(language: String, page: Int) {

        disposables.add(
            MovieApplication.apiService.getNowPlayingMovie(language, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response -> iMovieNowPlayingView.setData(response.results) },
                    { t -> iMovieNowPlayingView.errorResponse(t) })
        )

    }


}