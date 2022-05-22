package window.windows

import api.AuthenticationAPI
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLFormElement
import org.w3c.xhr.FormData
import util.Networking
import util.equ

object RegisterWindow {
    fun runWindow() {
        val form = document.getElementById("register-form") as HTMLFormElement
        form.addEventListener("submit", {event ->
            event.preventDefault()
            val formData = FormData(form)
            val uname = formData.get("uname")
            val password = formData.get("password")
            val confirmPassword = formData.get("confirm-password")

            if(password != confirmPassword) {
                document.getElementById("errormessage")?.innerHTML = "Passwords do not match!"
                return@addEventListener
            }
            try {
                Networking.get("/api/users/$uname")
                    .then { res ->
                        if (res.status equ 200) {
                            document.getElementById("errormessage")?.innerHTML = "$uname is already taken!"
                            return@then
                        }
                        AuthenticationAPI.register(uname as String, password as String).then { regRes ->
                            if (regRes.status equ 200) {
                                document.getElementById("errormessage")?.innerHTML = "Registered your account!"
                                window.location.replace("/login.html")
                            } else {
                                document.getElementById("errormessage")?.innerHTML =
                                    "Failed to register your account. Please try again later."
                            }
                        }
                    }
            } catch(e: Exception) {
                console.log(e)
                document.getElementById("errormessage")?.innerHTML = "An error occurred. Please try again later. $e"
            }
        })
    }
}