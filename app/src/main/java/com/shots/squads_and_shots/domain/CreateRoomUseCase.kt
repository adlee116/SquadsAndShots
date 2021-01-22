package com.shots.squads_and_shots.domain

import com.shots.squads_and_shots.core.BaseUseCase
import com.shots.squads_and_shots.core.Result
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.RoomModel
import com.shots.squads_and_shots.presentation.leadHoldingLobby.repository.RoomModelRepository

class CreateRoomUseCase(private val repository: RoomModelRepository): BaseUseCase<Boolean, RoomModel>() {
    override suspend fun run(params: RoomModel): Result<Boolean, Exception> {
        repository.createRoom(params)
        return Result.Success(true)
    }
}