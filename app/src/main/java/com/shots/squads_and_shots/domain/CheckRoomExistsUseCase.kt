package com.shots.squads_and_shots.domain

import com.shots.squads_and_shots.core.BaseUseCase
import com.shots.squads_and_shots.core.Result
import com.shots.squads_and_shots.network.models.CheckRoomRequest
import com.shots.squads_and_shots.network.repositories.RoomModelRepository

class CheckRoomExistsUseCase(private val roomModelRepo: RoomModelRepository) : BaseUseCase<Boolean, CheckRoomRequest>() {
    override suspend fun run(params: CheckRoomRequest): Result<Boolean, Exception> {
        return try {
            val room = roomModelRepo.getRoom(params.roomCode)
            room.addOnSuccessListener(params.onSuccessListener)
            room.addOnFailureListener(params.onFailureListener)
            Result.Success(true)
        } catch (ex: Exception) {
            Result.Success(false)
        }

    }
}