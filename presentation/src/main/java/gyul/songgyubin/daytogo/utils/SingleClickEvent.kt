package gyul.songgyubin.daytogo.utils

import android.os.SystemClock

/**
 * Used to Single Click Event
 */

open class SingleClickEvent<event : SingleClickEventFlag>(private val content: SingleClickEventFlag) {


    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): SingleClickEventFlag {

        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime: Long = currentClickTime - lastClickTime
        lastClickTime = currentClickTime

        if (elapsedTime <= MIN_CLICK_INTERVAL) {
            return SingleClickEventFlag.NONE
        }
        return if (hasBeenHandled) {
            SingleClickEventFlag.NONE
        } else {
            hasBeenHandled = true
            content
        }
    }

    companion object {
        private val MIN_CLICK_INTERVAL: Long = 1000
        private var lastClickTime: Long = 0
    }

}