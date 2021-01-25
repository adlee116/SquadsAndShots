package com.shots.squads_and_shots.di

import com.shots.squads_and_shots.network.fireCloudStorage.FirebaseStorage
import com.shots.squads_and_shots.network.fireCloudStorage.StorageInterface
import com.shots.squads_and_shots.network.realTimeFireStore.DatabaseInterface
import com.shots.squads_and_shots.network.realTimeFireStore.FirebaseDatabase
import org.koin.dsl.module

val applicationModule = module {

    single<DatabaseInterface> { FirebaseDatabase() }

    // Access a Cloud Firestore instance from your Activity
    single<StorageInterface> { FirebaseStorage() }
}