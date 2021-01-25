package com.shots.squads_and_shots.domain

import com.shots.squads_and_shots.core.BaseUseCase
import com.shots.squads_and_shots.core.Result
import com.shots.squads_and_shots.network.StartGameRequest
import com.shots.squads_and_shots.network.repositories.RoomModelRepository
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.RoomModel
import kotlin.Exception

class StartGameUseCase(private val repository: RoomModelRepository): BaseUseCase<Boolean, RoomModel>() {
    override suspend fun run(params: RoomModel): Result<Boolean, Exception> {
        return try {
            repository.startGame(params)
            Result.Success(true)
        } catch (ex: java.lang.Exception) {
            Result.Failure(ex)
        }

    }
}