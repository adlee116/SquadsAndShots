package com.shots.squads_and_shots.di

import com.shots.squads_and_shots.presentation.leadHoldingLobby.repository.RoomModelRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { RoomModelRepository( get() ) }
}