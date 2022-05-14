package dev.waterlilly.lila_fe

import dev.waterlilly.lila_fe.message.Message
import dev.waterlilly.lila_fe.message.MessageType
import kotlinx.browser.document
import org.w3c.dom.*
import kotlin.js.Date

fun main(args: Array<String>) {
    val message_text_submit = document.getElementById("message-text-submit") as HTMLTextAreaElement

    message_text_submit.addEventListener("click", {
        val text_entry_field = document.getElementById("message-text-entry") as HTMLInputElement
        console.log(text_entry_field.textContent)
    })
}