package com.shots.squads_and_shots.di

import com.shots.squads_and_shots.presentation.homePage.GameChooserViewModel
import com.shots.squads_and_shots.presentation.homePage.GameCodeInputViewModel
import com.shots.squads_and_shots.presentation.leadHoldingLobby.LeadHoldingLobbyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { GameChooserViewModel() }
    viewModel { LeadHoldingLobbyViewModel( get(), get(), get(), get(), get() ) }
    viewModel { GameCodeInputViewModel( get()) }
}