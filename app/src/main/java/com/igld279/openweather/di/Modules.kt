package com.igld279.openweather.di

import android.app.Application
import android.content.Context
import com.igld279.openweather.data.Repository
import com.igld279.openweather.data.remote.RemoteDataSource
import com.igld279.openweather.data.local.AppDatabase
import com.igld279.openweather.data.local.LocalDataSource
import com.igld279.openweather.data.local.MyWeatherDao
import com.igld279.openweather.data.location.LocationDataSource
import com.igld279.openweather.domain.converters.CurrentConverter
import com.igld279.openweather.domain.converters.DailyConverter
import com.igld279.openweather.domain.converters.HourlyConverter
import com.igld279.openweather.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    fun provideCurrentConverter(): CurrentConverter{
        return CurrentConverter.instance
    }

    fun provideHourlyConverter(): HourlyConverter {
        return HourlyConverter.instance
    }

    fun provideDailyConverter(): DailyConverter {
        return DailyConverter.instance
    }

    viewModel { MainViewModel(get(), get(), get(), get(), get()) }
    single { provideCurrentConverter() }
    single { provideHourlyConverter() }
    single { provideDailyConverter() }
}

val repoModule = module {

    fun provideLocationDataSource(context: Context): LocationDataSource {
        return LocationDataSource(context)
    }

    fun provideRemoteDataSource(): RemoteDataSource {
        return RemoteDataSource.instance
    }

    fun provideLocalDataSource(myWeatherDao: MyWeatherDao): LocalDataSource {
        return LocalDataSource(myWeatherDao)
    }

    fun provideRepository(locationDataSource: LocationDataSource,
                          remoteDataSource: RemoteDataSource,
                          localDataSource: LocalDataSource)
    : Repository {
        return Repository(locationDataSource, remoteDataSource, localDataSource)
    }

    single { provideLocationDataSource(get()) }
    single { provideRemoteDataSource() }
    single { provideLocalDataSource(get()) }
    single { provideRepository(get(), get(), get()) }

}

val databaseModule = module {
    fun provideAppDatabase(application: Application): AppDatabase {
        return AppDatabase.getDatabase(application)
    }

    fun provideMyWeatherDao(db: AppDatabase): MyWeatherDao {
        return db.myWeatherDao()
    }

    single { provideAppDatabase(get()) }
    single { provideMyWeatherDao(get()) }
}