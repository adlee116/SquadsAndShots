package com.shots

import android.app.Application
import com.shots.squads_and_shots.di.applicationModule
import com.shots.squads_and_shots.di.repositoryModule
import com.shots.squads_and_shots.di.useCaseModule
import com.shots.squads_and_shots.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SquadsAndShots : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SquadsAndShots)
            modules(
                listOf(
                    viewModelModule,
                    applicationModule,
                    repositoryModule,
                    useCaseModule
                )
            )
        }
    }

}