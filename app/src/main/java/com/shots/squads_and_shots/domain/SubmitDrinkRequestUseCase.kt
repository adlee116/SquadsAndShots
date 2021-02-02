package com.shots.squads_and_shots.domain

import com.shots.squads_and_shots.core.BaseUseCase
import com.shots.squads_and_shots.core.Result
import com.shots.squads_and_shots.network.models.DrinkRequest
import com.shots.squads_and_shots.network.repositories.DrinkHistoryRepository

class SubmitDrinkRequestUseCase(private val repository: DrinkHistoryRepository): BaseUseCase<Boolean, DrinkRequest>() {
    override suspend fun run(params: DrinkRequest): Result<Boolean, Exception> {
        return try {
            repository.submitDrinkRequest(params.roomCode, params.drink)
            Result.Success(true)
        } catch (ex: java.lang.Exception) {
            Result.Failure(ex)
        }
    }
}