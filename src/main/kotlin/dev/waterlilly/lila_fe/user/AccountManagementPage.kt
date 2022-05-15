package dev.waterlilly.lila_fe.user

import dev.waterlilly.lila_fe.user.Login.uname
import dev.waterlilly.lila_fe.util.Networking
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.browser.window
import org.w3c.dom.HTMLFormElement
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import org.w3c.xhr.FormData
import kotlin.js.Promise
import kotlin.js.json

object Login {
    var uname = document.getElementById("login-uname")?.nodeValue
    var password = document.getElementById("login-password")?.nodeValue
    val form = document.getElementById("register") as HTMLFormElement
    val username = localStorage.getItem("username")

    fun loginForm() {
        form.addEventListener("submit", {event ->
            event.preventDefault()
            val formData = FormData(form)
            uname = formData.get("uname")
            password = formData.get("password")

            try {
                val login = json(Pair("name", uname), Pair("password", password))
                val headers = Headers()
                headers.append("Content-Type", "application/json")
                val loginInfo = Networking.fetch("/api/login", "POST", headers, JSON.stringify(login))
                if(loginInfo.status == 200.toShort()) {
                    login()
                } else {
                    incorrectLogin()
                }
            } catch(e: Exception) {
                console.log(e)
                document.getElementById("errormessage")?.innerHTML = "An error occurred. Please try again later. $e"
            }
        })
    }
    fun login() {
        document.getElementById("errormessage")?.innerHTML = ""
        localStorage.setItem("username", uname.toString())
        document.getElementById("username")?.innerHTML = "Logged in as $uname"
        window.location.replace("/index.html")
    }
    fun incorrectLogin() {
        document.getElementById("errormessage")?.innerHTML = "Incorrect login!"
    }
}
object Register {
    fun registerForm() {

    }
}

