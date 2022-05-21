
package dev.waterlilly.lila.api

import dev.waterlilly.lila.util.Networking
import dev.waterlilly.lila.window.WindowManager
import kotlinx.browser.document
import kotlinx.dom.addClass
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
@JsExport
object WindowAPI {
    @JsName("toggleWindow")
    fun toggleWindow(window: String) {
        if (WindowManager.openWindows.contains(window)) {
            document.getElementById("$window-window")?.remove()
            WindowManager.openWindows.remove(window)
        } else {
            Networking.get("/windows/$window.htm")
                .then { res -> res.text() }
                .then { body ->
                    val div = document.createElement("div") as HTMLDivElement
                    val header = document.createElement("div") as HTMLDivElement
                    val dragHandle = document.createElement("button") as HTMLButtonElement

                    dragHandle.innerHTML = "<span class=\"material-symbols-outlined\">open_with</span>"
                    dragHandle.addClass("window-drag-button")
                    dragHandle.id = "$window-window-drag-button"

                    header.id = "$window-window-header"
                    header.addClass("window-header")
                    header.innerHTML = dragHandle.outerHTML + "\n" + "Window"

                    div.id = "$window-window"

                    div.addClass("window")
                    div.innerHTML = header.outerHTML + "\n" + body

                    println("creating window $window")
                    document.body?.insertBefore(div, document.getElementById("errormessage"))
                    WindowManager.windows[window]?.runWindow()
                    WindowManager.dragElement(div)
                    WindowManager.openWindows.add(window)
                }
        }
    }
}