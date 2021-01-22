package com.shots.squads_and_shots.presentation.leadHoldingLobby.model

class RoomModel {
    var players: HashMap<String, Player> = HashMap()
    var gameRules: MutableList<String>? = null
    var secretRules: MutableList<SecretRule>? = null
    var roomCode: String = ""
}

class Player {
    var id: String = ""
    var name: String = ""
}

class SecretRule {
    var playerId: String = ""
    var rule: String = ""
}


//class RoomModel {
//    var players: MutableList<Map<String, Player>> = mutableListOf()
//    var gameRules: MutableList<String>? = null
//    var secretRules: MutableList<SecretRule>? = null
//    var roomCode: String = ""
//}
//
//class Player {
//    var id: String = ""
//    var name: String = ""
//}
//
//class SecretRule {
//    var playerId: String = ""
//    var rule: String = ""
//}
