package com.shots.squads_and_shots.di

import com.shots.squads_and_shots.domain.AddRoomListener
import com.shots.squads_and_shots.domain.CreateRoomUseCase
import com.shots.squads_and_shots.domain.CheckRoomExistsUseCase
import com.shots.squads_and_shots.domain.JoinRoomUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single { CreateRoomUseCase(get()) }
    single { CheckRoomExistsUseCase(get()) }
    single { JoinRoomUseCase(get()) }
    single { AddRoomListener(get()) }
}