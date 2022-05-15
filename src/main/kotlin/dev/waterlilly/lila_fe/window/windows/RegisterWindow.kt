package dev.waterlilly.lila_fe.window.windows

import dev.waterlilly.lila_fe.rest.Authentication
import dev.waterlilly.lila_fe.util.Networking
import dev.waterlilly.lila_fe.window.Window
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLFormElement
import org.w3c.xhr.FormData

object RegisterWindow : Window {
    override fun runWindow() {
        val form = document.getElementById("register") as HTMLFormElement
        form.addEventListener("submit", {event ->
            event.preventDefault()
            val formData = FormData(form)
            val uname = formData.get("uname")
            val password = formData.get("password")
            val confirm_password = formData.get("confirm-password")

            if(password != confirm_password) {
                document.getElementById("errormessage")?.innerHTML = "Passwords do not match!"
                return@addEventListener
            }
            try {
                val isTaken = Networking.fetch("/api/users/$uname")
                if (isTaken.status == 200.toShort()) {
                    document.getElementById("errormessage")?.innerHTML = "$uname is already taken!"
                    return@addEventListener
                }
                val regRes = Authentication.register(uname, password)
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