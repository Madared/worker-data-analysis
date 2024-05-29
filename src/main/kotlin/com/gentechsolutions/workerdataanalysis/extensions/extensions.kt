package com.gentechsolutions.workerDataAnalysis.extensions

import java.util.*


fun <T> Optional<out T>.toResult(error: Throwable): Result<T> {
    return if (this.isPresent) {
        Result.success(this.get())
    } else {
        Result.failure(error)
    }
}

fun <T> T?.toOption(): Optional<out T> =
    if (this == null) Optional.empty()
    else Optional.of(this)

fun String.normalize() = this.trim().lowercase().removeSurrounding("")

fun <T, TOut> Optional<T>.bind(mapper: (data: T) -> Optional<TOut>): Optional<out TOut> {
    if (this.isPresent) {
        val result: Optional<TOut> = mapper(this.get())
        return result;
    } else {
        return Optional.empty<TOut>();
    };
}

fun <T, TOut> T.into(mapper: (data: T) -> TOut) = mapper(this)

fun <T, TOut> Result<T>.bind(mapper: (data: T) -> Result<TOut>): Result<TOut> =
    if (this.isFailure) {
        Result.failure(this.exceptionOrNull()!!)
    } else {
        mapper(this.getOrThrow())
    }

fun <T> Optional<out T>.compareWith(
    other: Optional<out T>,
    comparer: (first: T, second: T) -> Boolean
): Boolean {
    return if (this.isPresent) {
        other.isPresent && comparer(this.get(), other.get())
    } else {
        other.isEmpty
    }
}

fun <T> Optional<out T>.compareOrMissing(other: Optional<out T>, comparer: (one: T, two: T) -> Boolean): Boolean {
    return if (this.isEmpty || other.isEmpty) {
        true
    } else {
        comparer(this.get(), other.get())
    }
}

fun <T> Optional<out T>.compareWith(other: Optional<out T>): Boolean {
    return this.compareWith(other) { one, two -> one == two }
}
fun <T> Optional<out T>.orOption(other: Optional<out T>) = if (this.isEmpty) other else this
fun <T> Optional<out T>.orDefault(default: T) = if (this.isEmpty) default else this.get()



