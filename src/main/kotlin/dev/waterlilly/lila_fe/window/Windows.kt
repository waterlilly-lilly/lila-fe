package dev.waterlilly.lila_fe.window

import dev.waterlilly.lila_fe.rest.Authentication.register
import dev.waterlilly.lila_fe.util.FileIO
import dev.waterlilly.lila_fe.window.windows.*

object Windows {
    val Windows: HashMap<String, WindowItem> = hashMapOf(
        win("login", LoginWindow),
        win("register", RegisterWindow)
    )


    fun win(name: String, window: Window): Pair<String, WindowItem> {
        val wi = WindowItem(window, FileIO.readFile("windows/register.htm"))
        return Pair(name, wi)
    }
    data class WindowItem(val window: Window, val html: String)
}