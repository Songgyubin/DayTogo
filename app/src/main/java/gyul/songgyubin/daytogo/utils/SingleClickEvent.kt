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
     * 처음 클릭 시 받아온 SingleClickEventFlag를 리턴해주고
     * MIN_CLICK_INTERVAL안에 재클릭한다면 NONE을 리턴하여
     * 아무런 동작을 일으키지 않도록한다.
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