package com.shots.squads_and_shots.domain

import com.shots.squads_and_shots.core.BaseUseCase
import com.shots.squads_and_shots.core.Result
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.JoinModel
import com.shots.squads_and_shots.presentation.leadHoldingLobby.repository.RoomModelRepository

class JoinRoomUseCase(private val repository: RoomModelRepository): BaseUseCase<Boolean, JoinModel>() {
    override suspend fun run(params: JoinModel): Result<Boolean, Exception> {
        repository.joinRoom(params.roomCode, params.player)
        return Result.Success(true)
    }
}