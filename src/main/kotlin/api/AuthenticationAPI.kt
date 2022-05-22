package api

import util.Networking
import org.w3c.fetch.Response
import kotlin.js.Promise
import kotlin.js.json

@JsExport
@JsName("authentication")
object AuthenticationAPI {
    @JsName("login")
    fun login(username: String, password: String): Promise<Response> {
        val login = json("name" to username,"password" to password)
        return Networking.post("/api/login", login)
    }
    @JsName("register")
    fun register(username: String, password: String): Promise<Response> {
        val register = json("name" to username,"password" to password)
        return Networking.post("/api/register",  register)
    }
    @JsName("logout")
    fun logout(username: String): Promise<Response> {
        val logout = json("name" to username)
        return Networking.post("/api/logout", logout)
    }
}