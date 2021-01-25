package com.shots.squads_and_shots.domain

import com.shots.squads_and_shots.core.BaseUseCase
import com.shots.squads_and_shots.core.Result
import com.shots.squads_and_shots.network.StartGameRequest
import com.shots.squads_and_shots.network.repositories.RoomModelRepository
import kotlin.Exception

class StartGameUseCase(private val repository: RoomModelRepository): BaseUseCase<Boolean, StartGameRequest>() {
    override suspend fun run(params: StartGameRequest): Result<Boolean, Exception> {
        return try {
            repository.startGame(params.roomCode, params.rules)
            Result.Success(true)
        } catch (ex: java.lang.Exception) {
            Result.Failure(ex)
        }

    }
}