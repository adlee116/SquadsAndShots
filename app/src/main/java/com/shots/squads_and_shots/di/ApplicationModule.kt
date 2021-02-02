package com.shots.squads_and_shots.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.*
import com.shots.squads_and_shots.network.GsonExclusionStrategy
import com.shots.squads_and_shots.network.fireCloudStorage.FirebaseStorage
import com.shots.squads_and_shots.network.fireCloudStorage.StorageInterface
import com.shots.squads_and_shots.network.localStorage.LocalStorageInterface
import com.shots.squads_and_shots.network.localStorage.Preferences
import com.shots.squads_and_shots.network.localStorage.SharedPreferencesStorage
import com.shots.squads_and_shots.network.realTimeFireStore.DatabaseInterface
import com.shots.squads_and_shots.network.realTimeFireStore.FirebaseDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val applicationModule = module {

    single<DatabaseInterface> { FirebaseDatabase() }

    // Access a Cloud Firestore instance from your Activity
    single<StorageInterface> { FirebaseStorage() }
    single<SharedPreferences>(named(Preferences.FILE_NAME)) {
        androidContext().getSharedPreferences(Preferences.FILE_NAME, Context.MODE_PRIVATE)
    }
    single<LocalStorageInterface>{SharedPreferencesStorage(get())}
    single { Preferences(get(named(Preferences.FILE_NAME)), get()) }

    single<ExclusionStrategy> { GsonExclusionStrategy() }
    single {
        GsonBuilder().addSerializationExclusionStrategy(get())
    }

    single<Gson> { get<GsonBuilder>().create() }
}