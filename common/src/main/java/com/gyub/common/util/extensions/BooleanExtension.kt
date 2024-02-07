package com.gyub.common.util.extensions

/**
 * Boolean 형 확장함수
 *
 * @author   Gyub
 * @created  2024/02/07
 */

/**
 * null이면 false 반환
 */
fun Boolean?.orFalse() = this ?: false