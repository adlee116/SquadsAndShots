package com.shots.squads_and_shots.di

//import com.shots.squads_and_shots.logging.AnalyticsService
//import com.shots.squads_and_shots.logging.FirebaseAnalytics
import com.shots.squads_and_shots.network.DatabaseInterface
import com.shots.squads_and_shots.network.FirebaseDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val applicationModule = module {

//    single { FirebaseAnalytics(androidContext()) }
//    single { AnalyticsService( get() )}
    single<DatabaseInterface> { FirebaseDatabase() }
}