package gyul.songgyubin.daytogo.utils

import android.os.SystemClock
import android.util.Log

/**
 * Used to Single Click Event
 */

open class SingleEvent<Int>(private val content: kotlin.Int) {


    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): kotlin.Int {
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime: Long = currentClickTime - lastClickTime
        lastClickTime = currentClickTime

        if (elapsedTime <= MIN_CLICK_INTERVAL) {
            return 0
        }

        return if (hasBeenHandled) {
            0
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