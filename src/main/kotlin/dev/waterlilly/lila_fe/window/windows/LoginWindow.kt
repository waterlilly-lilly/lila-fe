package dev.waterlilly.lila_fe.window.windows

import dev.waterlilly.lila_fe.api.Authentication
import dev.waterlilly.lila_fe.window.Window
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.browser.window
import org.w3c.dom.HTMLFormElement
import org.w3c.xhr.FormData

object LoginWindow : Window {
    override fun runWindow() {
        val form = document.getElementById("login-form") as HTMLFormElement
        form.addEventListener("submit", { event ->
            event.preventDefault()
            val formData = FormData(form)
            val uname = formData.get("uname")
            val password = formData.get("password")

            try {
                Authentication.login(uname, password).then {res ->
                    if(res.status == 200.toShort()) {
                        document.getElementById("errormessage")?.innerHTML = ""
                        localStorage.setItem("username", uname)
                        document.getElementById("username")?.innerHTML = "Logged in as $uname"
                        window.location.replace("/index.html")
                    } else {
                        document.getElementById("errormessage")?.innerHTML = "Incorrect login!"
                    }
                }
            } catch(e: Exception) {
                console.log(e)
                document.getElementById("errormessage")?.innerHTML = "An error occurred. Please try again later. $e"
            }
        })
    }
}