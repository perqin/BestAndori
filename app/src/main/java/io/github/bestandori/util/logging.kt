package io.github.bestandori.util

import android.util.Log

fun i(tag: String, message: String) {
    log(Log.INFO, tag, message)
}

fun d(tag: String, message: String) {
    log(Log.DEBUG, tag, message)
}

fun v(tag: String, message: String) {
    log(Log.VERBOSE, tag, message)
}

private fun log(level: Int, tag: String, message: String) {
    Log.println(level, "ba_$tag", message)
}
