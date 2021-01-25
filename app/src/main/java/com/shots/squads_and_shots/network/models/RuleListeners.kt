package com.shots.squads_and_shots.network.models

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.QuerySnapshot

data class RuleListeners(
    val successListener: OnSuccessListener<QuerySnapshot>,
    val failureListener: OnFailureListener
)