package com.shots.squads_and_shots.presentation.homePage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.shots.squads_and_shots.domain.CheckRoomExistsUseCase
import com.shots.squads_and_shots.network.models.CheckRoomRequest

class GameCodeInputViewModel(private val checkRoomExistsUseCase: CheckRoomExistsUseCase) :
    ViewModel() {

    private val _roomExists = MutableLiveData<Boolean>()
    val roomExists: LiveData<Boolean> get() = _roomExists

    fun checkRoomExists(roomCode: String) {
        val roomRequest = CheckRoomRequest(
            roomCode, onSuccessListener, onFailureListener
        )
        checkRoomExistsUseCase.invoke(viewModelScope, roomRequest) {}
    }

    private val onSuccessListener = OnSuccessListener<DataSnapshot> {
        _roomExists.value = it.exists()
    }

    private val onFailureListener = OnFailureListener {
        _roomExists.value = false
    }
}