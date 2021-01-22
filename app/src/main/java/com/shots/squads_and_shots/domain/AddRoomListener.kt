package com.shots.squads_and_shots.domain

import com.google.firebase.database.ValueEventListener
import com.shots.squads_and_shots.core.BaseUseCase
import com.shots.squads_and_shots.core.Result
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.ListenerRequest
import com.shots.squads_and_shots.presentation.leadHoldingLobby.repository.RoomModelRepository

class AddRoomListener(private val roomModelRepo: RoomModelRepository) : BaseUseCase<ValueEventListener, ListenerRequest>() {
    override suspend fun run(params: ListenerRequest): Result<ValueEventListener, Exception> {
        return Result.Success(roomModelRepo.createRoomListener(params))
    }
}