package dev.waterlilly.lila_fe.util

import kotlinx.browser.window
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import kotlin.js.Json
import kotlin.js.Promise

object Networking {
    fun fetch(path: String, method: String, headers: Headers, body: String): Response {
        var response: Response = Response.error()

        run<Promise<Any?>>{
            val promise = Promise.resolve(window.fetch(path, RequestInit(method = method, headers = headers, body = body)))
            var resJson: Promise<Any?> = Promise { _: (Any?) -> Unit, _: (Throwable) -> Unit -> }
            promise.then {res ->
                resJson = res.json()
            }
            resJson}
            .then {rsp ->
                response = rsp as Response
            }
        return response
    }
    fun fetch(path: String): Response = fetch(path, "GET", Headers(), "")
    fun post(path: String, body: Json): Response {
        val headers = Headers()
        headers.append("Content-Type", "application/json")
        return fetch(path, "POST", headers, JSON.stringify(body))
    }
}
