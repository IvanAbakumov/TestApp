package com.world.domain.common

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.SendChannel

inline fun <T : Any> guardLet(vararg elements: T?, closure: () -> Nothing): List<T> {
    return if (elements.all { it != null }) {
        elements.filterNotNull()
    } else {
        closure()
    }
}

inline fun <T : Any> ifLet(vararg elements: T?, closure: (List<T>) -> Unit) {
    if (elements.all { it != null }) {
        closure(elements.filterNotNull())
    }
}

inline fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

inline fun <T1 : Any, T2 : Any, T3 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    block: (T1, T2, T3) -> R?,
): R? {
    return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
}

inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    block: (T1, T2, T3, T4) -> R?,
): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null) block(p1, p2, p3, p4) else null
}

inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    p5: T5?,
    block: (T1, T2, T3, T4, T5) -> R?,
): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null && p5 != null) block(
        p1,
        p2,
        p3,
        p4,
        p5
    ) else null
}

inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, T7 : Any, T8 : Any, T9 : Any, T10 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    p5: T5?,
    p6: T6?,
    p7: T7?,
    p8: T8?,
    p9: T9?,
    p10: T10?,
    block: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> R?,
): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null && p5 != null && p6 != null && p7 != null && p8 != null && p9 != null && p10 != null) block(
        p1,
        p2,
        p3,
        p4,
        p5,
        p6,
        p7,
        p8,
        p9,
        p10,
    ) else null
}

fun <E> SendChannel<E>.offerCatching(element: E): Boolean {
    return runCatching { trySend(element).isSuccess }.getOrDefault(false)
}

fun CoroutineScope.launchTimerPeriodicAsync(
    repeatMillis: Long,
    action: () -> Unit,
) = this.async {
    if (repeatMillis > 0) {
        while (true) {
            delay(repeatMillis)
            action()
        }
    } else {
        action()
    }
}

fun CoroutineScope.launchTimerAsync(
    repeatMillis: Long,
    action: () -> Unit,
) = this.async {
    if (repeatMillis > 0) {
        delay(repeatMillis)
        action()
    } else {
        action()
    }
}

fun CoroutineScope.safeLaunch(block: suspend CoroutineScope.() -> Unit): Job {
    return this.launch {
        try {
            block()
        } catch (ce: CancellationException) {
            // You can ignore or log this exception
        }
    }
}