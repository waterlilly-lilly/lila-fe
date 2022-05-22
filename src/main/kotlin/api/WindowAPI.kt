package api

import window.WindowManager

@JsExport
@JsName("window")
object WindowAPI {
    @JsName("toggleWindow")
    fun toggleWindow(window: String) {
        return WindowManager.toggleWindow(window)
    }
}