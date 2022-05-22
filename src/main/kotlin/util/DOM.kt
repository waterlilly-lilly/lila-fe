package util

import kotlinx.dom.hasClass
import org.w3c.dom.Element

object DOM {
    fun parentContainsClass(cssClass: String, element: Element): Boolean {
        var a = element
        while(true) {
            if(a.hasClass(cssClass)) return true
            a = a.parentElement ?: return false
        }
    }
    fun getAllParents(element: Element): HashSet<Element> {
        var a = element
        val arr: HashSet<Element> = hashSetOf()
        while(a.parentElement != null) {
            arr.add(a.parentElement!!)
            a = a.parentElement!!
        }
        return arr
    }
}