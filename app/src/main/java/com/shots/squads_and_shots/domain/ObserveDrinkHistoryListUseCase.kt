package com.shots.squads_and_shots.domain

import com.shots.squads_and_shots.core.BaseUseCase
import com.shots.squads_and_shots.core.Result
import com.shots.squads_and_shots.network.repositories.DrinkHistoryRepository
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.ListenerRequest

class ObserveDrinkHistoryListUseCase(private val repository: DrinkHistoryRepository): BaseUseCase<Boolean, ListenerRequest>() {
    override suspend fun run(params: ListenerRequest): Result<Boolean, Exception> {
        return try {
            repository.setDrinkListener(params)
            Result.Success(true)
        } catch(ex: java.lang.Exception) {
            Result.Failure(ex)
        }
    }
}