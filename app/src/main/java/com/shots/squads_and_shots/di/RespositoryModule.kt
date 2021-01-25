package com.shots.squads_and_shots.di

import com.shots.squads_and_shots.network.repositories.RoomModelRepository
import com.shots.squads_and_shots.network.repositories.StorageRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { RoomModelRepository( get() ) }
    single { StorageRepository ( get() )}
}