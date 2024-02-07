package gyul.songgyubin.daytogo.utils.extensions

/**
 * Int형 확장함수
 *
 * @author   Gyub
 * @created  2024/02/07
 */

/**
 * null이면 [defaultValue] 반환
 */
fun Int?.orDefault(defaultValue: Int = 0) = this ?: defaultValue