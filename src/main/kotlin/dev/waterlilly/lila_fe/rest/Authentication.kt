package dev.waterlilly.lila_fe.rest

import dev.waterlilly.lila_fe.util.Networking
import org.w3c.fetch.Response
import kotlin.js.Promise
import kotlin.js.json

object Authentication {
    fun login(username: String, password: String): Promise<Response> {
        val login = json(Pair("name", username), Pair("password", password))
        return Networking.post("/api/login", login)
    }
    fun register(username: String, password: String): Promise<Response> {
        val register = json(Pair("name", username), Pair("name", password))
        return Networking.post("/api/register",  register)
    }
    fun logout(username: String): Promise<Response> {
        val logout = json(Pair("name", username))
        return Networking.post("/api/logout", logout)
    }
}