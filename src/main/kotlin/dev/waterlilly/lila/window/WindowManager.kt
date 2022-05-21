package dev.waterlilly.lila.window

import dev.waterlilly.lila.util.DOM
import dev.waterlilly.lila.window.windows.LoginWindow
import dev.waterlilly.lila.window.windows.RegisterWindow
import kotlinx.browser.document
import kotlinx.dom.addClass
import kotlinx.dom.hasClass
import kotlinx.dom.removeClass
import org.w3c.dom.*
import org.w3c.dom.events.MouseEvent
object WindowManager {
    val openWindows: HashSet<String> = HashSet()
    val windows: HashMap<String, Window> = HashMap()
    fun initializeWindows() {
        windows["login"] = LoginWindow
        windows["register"] = RegisterWindow
    }
    fun afterInitialize() {
        document.addEventListener("click", {event ->
            val target = event.target as Element
            if(DOM.parentContainsClass("window", target)) {
                document.getElementsByClassName("active-window").asList().iterator().forEach { it.removeClass("active-window")}
                DOM.getAllParents(target).forEach { if(it.hasClass("window")) it.addClass("active-window") }
            }
        })
    }

    fun dragElement(element: Element) {
        var pos1: Int
        var pos2: Int
        var pos3 = 0
        var pos4 = 0
        val closeDragElement = { _: MouseEvent ->
            document.onmouseup = null
            document.onmousemove = null
        }
        val elementDrag = {e: MouseEvent ->
            js("e = e || window.event")
            e.preventDefault()

            pos1 = pos3 - e.clientX
            pos2 = pos4 - e.clientY
            pos3 = e.clientX
            pos4 = e.clientY

            element.setAttribute("style", "top: ${js("element.offsetTop") as Int - pos2}px; left: ${js("element.offsetLeft") as Int - pos1}px")

        }
        val dragMouseDown = {e: MouseEvent ->
            js("e = e || window.event")
            e.preventDefault()

            pos3 = e.clientX
            pos4 = e.clientY

            document.onmouseup = closeDragElement
            document.onmousemove = elementDrag

        }
        (document.getElementById(element.id + "-drag-button") as HTMLButtonElement).onmousedown = dragMouseDown
    }
}