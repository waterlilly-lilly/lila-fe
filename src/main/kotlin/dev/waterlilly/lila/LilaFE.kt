package dev.waterlilly.lila

import dev.waterlilly.lila.api.WindowAPI.toggleWindow
import dev.waterlilly.lila.window.WindowManager

fun main() {
    WindowManager.initializeWindows()
    toggleWindow("register")
    toggleWindow("login")
    WindowManager.afterInitialize()
}