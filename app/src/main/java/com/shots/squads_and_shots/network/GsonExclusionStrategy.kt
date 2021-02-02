package com.shots.squads_and_shots.network

import com.google.firebase.database.Exclude
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes

class GsonExclusionStrategy : ExclusionStrategy {

    override fun shouldSkipClass(clazz: Class<*>?): Boolean = false

    override fun shouldSkipField(f: FieldAttributes?) =
        f?.getAnnotation(Exclude::class.java) != null
}
