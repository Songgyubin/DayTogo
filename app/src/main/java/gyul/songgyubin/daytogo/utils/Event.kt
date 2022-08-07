package gyul.songgyubin.daytogo.utils

import android.os.SystemClock
import android.util.Log

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {


    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime: Long = currentClickTime - lastClickTime
        lastClickTime = currentClickTime

        if (elapsedTime <= MIN_CLICK_INTERVAL) {
            return null
        }

        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content

    companion object {
        private val MIN_CLICK_INTERVAL: Long = 1000
        private var lastClickTime: Long = 0
    }

}