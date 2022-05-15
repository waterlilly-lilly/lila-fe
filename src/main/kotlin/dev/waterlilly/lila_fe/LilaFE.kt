package dev.waterlilly.lila_fe

import dev.waterlilly.lila_fe.window.WindowManager

fun main() {
    WindowManager.initializeWindows()
    WindowManager.toggleWindow("register")
    WindowManager.toggleWindow("login")
}