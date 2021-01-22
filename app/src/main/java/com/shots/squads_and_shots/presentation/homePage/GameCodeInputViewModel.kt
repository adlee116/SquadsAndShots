package com.shots.squads_and_shots.presentation.homePage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shots.squads_and_shots.domain.CheckRoomExistsUseCase

class GameCodeInputViewModel(private val checkRoomExistsUseCase: CheckRoomExistsUseCase) :
    ViewModel() {

    private val _roomExists = MutableLiveData<Boolean>()
    val roomExists: LiveData<Boolean> get() = _roomExists

    fun checkRoomExists(roomCode: String) {
        checkRoomExistsUseCase.invoke(viewModelScope, roomCode) { result ->
            result.result(
                onSuccess = {
                    _roomExists.value = it
                },
                onFailure = {
                    _roomExists.value = false
                }
            )

        }
    }
}