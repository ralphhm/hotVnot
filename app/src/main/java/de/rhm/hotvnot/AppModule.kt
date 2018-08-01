package de.rhm.hotvnot

import com.squareup.moshi.Moshi
import de.rhm.hotvnot.api.Home24Service
import de.rhm.hotvnot.selection.SelectionViewModel
import io.reactivex.schedulers.Schedulers
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val AppModule = applicationContext {

    bean<Moshi> { Moshi.Builder().build() }

    bean<Converter.Factory> { MoshiConverterFactory.create(get()) }

    bean<Home24Service> {
        Retrofit.Builder().baseUrl("https://api-mobile.home24.com/api/v2.0/")
                .addConverterFactory(get())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build().create(Home24Service::class.java)
    }

    viewModel { SelectionViewModel(get()) }

}