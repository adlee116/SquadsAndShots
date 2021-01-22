//package com.shots.squads_and_shots.logging
//
//import android.os.Bundle
//import androidx.annotation.VisibleForTesting
//
//class AnalyticsService(private val impl: Analytics) {
//
//    fun logEvent(name: String, params: Bundle) {
//        checkForNameLengthLimit(name)
//        impl.logEvent(name, params)
//    }
//
//    fun setUserProperty(name: String, value: String?) {
//        impl.setUserProperty(name, value)
//    }
//
//    @VisibleForTesting
//    fun checkForNameLengthLimit(name: String): Boolean {
//        return checkForLimit(name, Limit.EVENT_NAME_LENGTH)
//    }
//
//    private fun checkForLimit(value: String, limit: Limit): Boolean {
//        return value.length > limit.value
//    }
//
//    // v2 stuff
//
//    enum class Limit(val value: Int, val errorDisplay: String) {
//        EVENT_NAME_LENGTH(40, "event name length")
//    }
//}
