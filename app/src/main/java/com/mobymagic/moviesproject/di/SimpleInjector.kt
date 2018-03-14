package com.mobymagic.moviesproject.di

import com.mobymagic.moviesproject.BuildConfig
import com.mobymagic.moviesproject.data.AuthorizedNetworkInterceptor
import com.mobymagic.moviesproject.data.BASE_URL
import com.mobymagic.moviesproject.data.MovieService
import com.mobymagic.moviesproject.moviedetail.MovieDetailPresenter
import com.mobymagic.moviesproject.movielist.MovieListAdapter
import com.mobymagic.moviesproject.movielist.MovieListPresenter
import com.mobymagic.moviesproject.utils.HtmlUtils
import com.mobymagic.moviesproject.utils.HttpRequestUtils
import com.mobymagic.moviesproject.utils.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object SimpleInjector {

    private lateinit var retrofit: Retrofit
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var htmlUtils: HtmlUtils

    fun provideMovieListPresenter(movieService: MovieService, scheduler: Scheduler) : MovieListPresenter {
        return MovieListPresenter(movieService, scheduler)
    }

    fun provideMovieDetailPresenter() = MovieDetailPresenter()

    fun provideMovieListAdapter(listener: MovieListAdapter.OnMovieClickListener) : MovieListAdapter {
        return MovieListAdapter(listener)
    }

    fun provideHtmlUtils() : HtmlUtils {
        if(!::htmlUtils.isInitialized) {
            htmlUtils = HtmlUtils()
        }

        return htmlUtils
    }

    fun provideMovieService(): MovieService {
        return provideRetrofit().create(MovieService::class.java)
    }

    fun provideUiProductionScheduler() = SchedulerProvider.uiProduction()

    fun provideUiTestScheduler() = SchedulerProvider.uiTest()

    private fun provideRetrofit(): Retrofit {
        if(!::retrofit.isInitialized) {
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(provideOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(provideRxAdapter())
                    .build()
        }

        return retrofit
    }

    private fun provideOkHttpClient(): OkHttpClient {
        if(!::okHttpClient.isInitialized) {
            okHttpClient = OkHttpClient.Builder()
                    .addNetworkInterceptor(provideAuthorizedNetworkInterceptor())
                    .addInterceptor(provideHttpLoggingInterceptor())
                    .build()
        }

        return okHttpClient
    }

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()

        // Configure logging level based on whether app is in debug mode
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        return httpLoggingInterceptor
    }

    private fun provideRxAdapter(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    private fun provideAuthorizedNetworkInterceptor(): AuthorizedNetworkInterceptor {
        return AuthorizedNetworkInterceptor(BuildConfig.API_KEY, BuildConfig.LANGUAGE,
                provideHttpRequestUtils())
    }

    private fun provideHttpRequestUtils(): HttpRequestUtils {
        return HttpRequestUtils()
    }
}