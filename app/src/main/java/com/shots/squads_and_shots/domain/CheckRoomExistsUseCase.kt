package com.shots.squads_and_shots.domain

import com.shots.squads_and_shots.core.BaseUseCase
import com.shots.squads_and_shots.core.Result
import com.shots.squads_and_shots.presentation.leadHoldingLobby.repository.RoomModelRepository

class CheckRoomExistsUseCase(private val roomModelRepo: RoomModelRepository) : BaseUseCase<Boolean, String>() {
    override suspend fun run(params: String): Result<Boolean, Exception> {
        return try {
            val room = roomModelRepo.getRoom(params)
            Result.Success(room.result?.exists() ?: false)
        } catch (ex: Exception) {
            Result.Success(false)
        }

    }
}