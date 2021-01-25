package com.shots.squads_and_shots.di

import com.shots.squads_and_shots.domain.*
import org.koin.dsl.module

val useCaseModule = module {

    single { CreateRoomUseCase(get()) }
    single { CheckRoomExistsUseCase(get()) }
    single { JoinRoomUseCase(get()) }
    single { AddRoomListener(get()) }
    single { StartGameUseCase(get()) }
    single { GetGeneralRulesUseCase(get())}
}