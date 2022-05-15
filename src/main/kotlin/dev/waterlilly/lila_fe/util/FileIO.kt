package dev.waterlilly.lila_fe.util

import org.w3c.xhr.XMLHttpRequest

object FileIO {
    fun readFile(path: String): String {
        var result: String = "Failed to get window contents!"
        val xmlhttp = XMLHttpRequest()
        xmlhttp.open("GET", path, false)
        xmlhttp.send()
        if(xmlhttp.status == 200.toShort())
            result = xmlhttp.responseText
        return result
    }
}