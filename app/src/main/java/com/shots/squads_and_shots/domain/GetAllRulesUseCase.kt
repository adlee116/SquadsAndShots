package com.shots.squads_and_shots.domain

import com.shots.squads_and_shots.core.BaseUseCase
import com.shots.squads_and_shots.core.Result
import com.shots.squads_and_shots.network.models.RuleListeners
import com.shots.squads_and_shots.network.repositories.StorageRepository

class GetAllRulesUseCase(private val storageRepository: StorageRepository) :
    BaseUseCase<Boolean, RuleListeners>() {
    override suspend fun run(params: RuleListeners): Result<Boolean, Exception> {
        return try {
            val collection = storageRepository.getCollection("Rules")
            collection.addOnSuccessListener(params.successListener)
            collection.addOnFailureListener(params.failureListener)
            Result.Success(true)
        } catch (ex: java.lang.Exception) {
            Result.Failure(ex)
        }
    }

}
