package com.shots.squads_and_shots.domain

import com.shots.squads_and_shots.core.EmptyParamsUseCase
import com.shots.squads_and_shots.network.localStorage.LocalStorageRepository
import java.lang.Exception
import java.util.*

class CreateOrGetUserUniqueId(private val localStorage: LocalStorageRepository): EmptyParamsUseCase<String>() {
    override suspend fun run() = localStorage.getOrCreateUserId() }
