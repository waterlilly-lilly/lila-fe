import api.WindowAPI
import window.WindowManager

fun main() {
    WindowAPI.toggleWindow("register")
    WindowAPI.toggleWindow("login")
    WindowManager.afterInitialize()
}