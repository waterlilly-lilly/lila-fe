import kotlinx.browser.window
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import kotlin.js.Json
import kotlin.js.Promise

object Networking {
    fun fetch(path: String, method: String, headers: Headers, body: String?): Promise<Response> {
        return window.fetch(path, RequestInit(method = method, headers = headers, body = body))
    }
    fun get(path: String): Promise<Response> = fetch(path, "GET", Headers(), null)
    fun post(path: String, body: Json): Promise<Response> {
        val headers = Headers()
        headers.append("Content-Type", "application/json")
        return fetch(path, "POST", headers, JSON.stringify(body))
    }
}