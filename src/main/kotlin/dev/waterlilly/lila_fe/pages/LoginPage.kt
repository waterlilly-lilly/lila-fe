package dev.waterlilly.lila_fe.pages

import dev.waterlilly.lila_fe.rest.Authentication.login
import dev.waterlilly.lila_fe.rest.Authentication.register
import dev.waterlilly.lila_fe.util.Networking
import dev.waterlilly.lila_fe.util.Networking.fetch
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.browser.window
import org.w3c.dom.HTMLFormElement
import org.w3c.fetch.Headers
import org.w3c.fetch.Response
import org.w3c.xhr.FormData
import kotlin.js.json

object Login {
    var uname = document.getElementById("login-uname")?.nodeValue as String
    var password = document.getElementById("login-password")?.nodeValue as String
    val form = document.getElementById("login") as HTMLFormElement

    fun loginForm() {
        form.addEventListener("submit", { event ->
            event.preventDefault()
            val formData = FormData(form)
            uname = formData.get("uname")
            password = formData.get("password")

            try {
                val loginInfo = login(uname, password)
                if(loginInfo.status == 200.toShort()) {
                    document.getElementById("errormessage")?.innerHTML = ""
                    localStorage.setItem("username", uname)
                    document.getElementById("username")?.innerHTML = "Logged in as $uname"
                    window.location.replace("/index.html")
                } else {
                    document.getElementById("errormessage")?.innerHTML = "Incorrect login!"
                }
            } catch(e: Exception) {
                console.log(e)
                document.getElementById("errormessage")?.innerHTML = "An error occurred. Please try again later. $e"
            }
        })
    }

}
object Register {
    var uname = document.getElementById("register-uname")?.nodeValue as String
    var password = document.getElementById("register-password")?.nodeValue as String
    var confirm_password = document.getElementById("register-confirm-password")?.nodeValue as String
    val form = document.getElementById("register") as HTMLFormElement

    fun registerForm() {
        form.addEventListener("submit", {event ->
            event.preventDefault()
            val formData = FormData(form)
            uname = formData.get("uname")
            password = formData.get("password")
            confirm_password = formData.get("confirm-password")

            if(password != confirm_password) {
                document.getElementById("errormessage")?.innerHTML = "Passwords do not match!"
                return@addEventListener
            }
            try {
                val isTaken = fetch("/api/users/$uname")
                if (isTaken.status == 200.toShort()) {
                    document.getElementById("errormessage")?.innerHTML = "$uname is already taken!"
                    return@addEventListener
                }
                val regRes = register(uname, password)
                if (regRes.status == 200.toShort()) {
                    document.getElementById("errormessage")?.innerHTML = "Registered your account!"
                    window.location.replace("/login.html")
                } else {
                    document.getElementById("errormessage")?.innerHTML =
                        "Failed to register your account. Please try again later."
                }
            } catch(e: Exception) {
                console.log(e)
                document.getElementById("errormessage")?.innerHTML = "An error occurred. Please try again later. $e"
            }
        })
    }
}

