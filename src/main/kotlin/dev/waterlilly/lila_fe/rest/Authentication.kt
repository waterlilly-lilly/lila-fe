package dev.waterlilly.lila_fe.rest

import dev.waterlilly.lila_fe.pages.Login
import dev.waterlilly.lila_fe.util.Networking
import org.w3c.fetch.Headers
import org.w3c.fetch.Response
import kotlin.js.json

object Authentication {
    fun login(username: String, password: String): Response {
        val login = json(Pair("name", username), Pair("password", password))
        val headers = Headers()
        headers.append("Content-Type", "application/json")
        return Networking.fetch("/api/login", "POST", headers, JSON.stringify(login))
    }
    fun register(username: String, password: String): Response {
        val register = json(Pair("name", username), Pair("name", password))
        val headers = Headers()
        headers.append("Content-Type", "application/json")
        return Networking.fetch("/api/register", "POST", headers, JSON.stringify(register))
    }
}