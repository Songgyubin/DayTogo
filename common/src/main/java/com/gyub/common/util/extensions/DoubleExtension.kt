package com.gyub.common.util.extensions

/**
 * Double형 Extension
 *
 * @author   Gyub
 * @created  2024/02/07
 */

/**
 * null이면 [defaultValue] 반환
 */
fun Double?.orDefault(defaultValue: Double = 0.0) = this ?: defaultValue