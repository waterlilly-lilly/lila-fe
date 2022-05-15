package dev.waterlilly.lila_fe.util

import kotlinx.browser.window
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
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
}
