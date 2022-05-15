package dev.waterlilly.lila_fe.window

import kotlinx.browser.document
import kotlinx.dom.addClass

object WindowManager {
    val openWindows: HashSet<String> = HashSet()
    fun toggleWindow(window: String) {
        if(openWindows.contains(window)) {
            document.getElementById("$window-window")?.remove()
            openWindows.remove(window)
        } else {
            console.log("Opening window $window")
            val div = document.createElement("div")
            div.setAttribute("id", "$window-window")
            div.addClass("window")
            val frag = document.createDocumentFragment()
            div.innerHTML = Windows.Windows[window]?.html ?: "Error"
            document.body?.insertBefore(div, document.getElementById("errormessage"))
            openWindows.add(window)
        }
    }
}