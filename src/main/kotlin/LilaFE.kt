import api.WindowAPI

fun main() {
    WindowAPI.toggleWindow("register")
    WindowAPI.toggleWindow("login")
    WindowManager.afterInitialize()
}