import kotlinx.browser.document
import kotlinx.dom.addClass
import kotlinx.dom.hasClass
import kotlinx.dom.removeClass
import org.w3c.dom.*
import org.w3c.dom.events.MouseEvent
object WindowManager {
    val openWindows: HashSet<String> = HashSet()
    fun afterInitialize() {
        document.addEventListener("click", {event ->
            val target = event.target as Element
            if(DOM.parentContainsClass("window", target)) {
                document.getElementsByClassName("active-window").asList().iterator().forEach { it.removeClass("active-window")}
                DOM.getAllParents(target).forEach { if(it.hasClass("window")) it.addClass("active-window") }
            }
        })
    }
    fun toggleWindow(window: String) {
        if (openWindows.contains(window)) {
            document.getElementById("$window-window")?.remove()
            println("Destroying window $window")
            openWindows.remove(window)
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
                    dragElement(div)
                    openWindows.add(window)
                }
        }
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