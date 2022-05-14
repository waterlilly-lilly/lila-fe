package dev.waterlilly.lila_fe.message

import kotlin.js.Date
import kotlin.js.Json
import kotlin.js.json

data class Message(val timestamp: Double, val sender: String, val type: MessageType, val id: String, val content: String) {
    fun toJson(): Json {
        val json: Json = json()
        with(json) {
            set("timestamp", timestamp)
            set("sender", sender)
            set("type", type.name)
            set("id", id)
            set("content", content)
        }
        return json
    }
}
