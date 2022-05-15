package dev.waterlilly.lila_fe.window

import dev.waterlilly.lila_fe.util.Networking
import dev.waterlilly.lila_fe.window.windows.LoginWindow
import dev.waterlilly.lila_fe.window.windows.RegisterWindow
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.dom.addClass
import org.w3c.dom.Element
import org.w3c.dom.events.Event
import org.w3c.fetch.Response

object WindowManager {
    val openWindows: HashSet<String> = HashSet()
    val windows: HashMap<String, Window> = HashMap()
    fun initializeWindows() {
        windows.put("login", LoginWindow)
        windows.put("register", RegisterWindow)
    }
    fun toggleWindow(window: String) {
        if(openWindows.contains(window)) {
            document.getElementById("$window-window")?.remove()
            openWindows.remove(window)
        } else {
            Networking.get("/windows/$window.htm")
                .then {res -> res.text()}
                .then {body ->
                    val div = document.createElement("div")
                    div.setAttribute("id", "$window-window")
                    div.addClass("window")
                    val frag = document.createDocumentFragment()
                    println("creating window $window")
                    div.innerHTML = body
                    dragElement(div)
                    document.body?.insertBefore(div, document.getElementById("errormessage"))
                    windows[window]?.runWindow()
                    openWindows.add(window)
                }
        }
    }
    fun dragElement(element: Element) {
        //for the time being i shamelessly stole this from https://www.w3schools.com/howto/howto_js_draggable.asp. I will port this to kotlin eventually
        js("var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;\n" +
                "  if (document.getElementById(element.id + \"header\")) {\n" +
                "    // if present, the header is where you move the DIV from:\n" +
                "    document.getElementById(element.id + \"header\").onmousedown = dragMouseDown;\n" +
                "  } else {\n" +
                "    // otherwise, move the DIV from anywhere inside the DIV:\n" +
                "    element.onmousedown = dragMouseDown;\n" +
                "  }\n" +
                "\n" +
                "  function dragMouseDown(e) {\n" +
                "    e = e || window.event;\n" +
                "    e.preventDefault();\n" +
                "    // get the mouse cursor position at startup:\n" +
                "    pos3 = e.clientX;\n" +
                "    pos4 = e.clientY;\n" +
                "    document.onmouseup = closeDragElement;\n" +
                "    // call a function whenever the cursor moves:\n" +
                "    document.onmousemove = elementDrag;\n" +
                "  }\n" +
                "\n" +
                "  function elementDrag(e) {\n" +
                "    e = e || window.event;\n" +
                "    e.preventDefault();\n" +
                "    // calculate the new cursor position:\n" +
                "    pos1 = pos3 - e.clientX;\n" +
                "    pos2 = pos4 - e.clientY;\n" +
                "    pos3 = e.clientX;\n" +
                "    pos4 = e.clientY;\n" +
                "    // set the element's new position:\n" +
                "    element.style.top = (element.offsetTop - pos2) + \"px\";\n" +
                "    element.style.left = (element.offsetLeft - pos1) + \"px\";\n" +
                "  }\n" +
                "\n" +
                "  function closeDragElement() {\n" +
                "    // stop moving when mouse button is released:\n" +
                "    document.onmouseup = null;\n" +
                "    document.onmousemove = null;\n" +
                "  }")
    }
}