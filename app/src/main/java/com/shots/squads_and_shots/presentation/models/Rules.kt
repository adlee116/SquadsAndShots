package com.shots.squads_and_shots.presentation.models

data class Rules(
    val generalRules: List<GeneralRule>,
    val secretTasks: List<SecretTasks>,
    val nominatedRules: List<NominatedRule>,
)