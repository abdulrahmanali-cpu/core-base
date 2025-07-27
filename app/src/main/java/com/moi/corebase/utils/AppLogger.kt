package com.moi.corebase.utils


import android.util.Log
import com.moi.corebase.BuildConfig

/**
 * A centralized Logger utility that only logs messages in DEBUG mode.
 * This prevents logs from being compiled into and showing up in release builds,
 * which is good for security and performance.
 */
object Logger {

    // A default tag for logs if no specific tag is provided.
    private const val DEFAULT_TAG = "MyAppDebug"

    // Logcat has a message length limit. We chunk long messages to avoid truncation.
    private const val MAX_LOG_LENGTH = 4000

    /**
     * Log a VERBOSE message.
     */
    fun v(tag: String, message: String) {
        if( BuildConfig.DEBUG) {
            Log.v(tag, message)
        }
    }

    fun v(message: String) {
        v(DEFAULT_TAG, message)
    }

    /**
     * Log a DEBUG message. Handles long messages by chunking them.
     */
    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            if (message.length > MAX_LOG_LENGTH) {
                logInChunks(tag, message, Log::d)
            } else {
                Log.d(tag, message)
            }
        }
    }

    fun d(message: String) {
        d(DEFAULT_TAG, message)
    }

    /**
     * Log an INFO message.
     */
    fun i(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message)
        }
    }

    fun i(message: String) {
        i(DEFAULT_TAG, message)
    }

    /**
     * Log a WARNING message.
     */
    fun w(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message)
        }
    }

    fun w(message: String) {
        w(DEFAULT_TAG, message)
    }

    /**
     * Log an ERROR message with an optional Throwable to print its stack trace.
     */
    fun e(tag: String, message: String, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message, throwable)
        }
    }

    fun e(message: String, throwable: Throwable? = null) {
        e(DEFAULT_TAG, message, throwable)
    }

    /**
     * Helper function to break down and log long messages.
     */
    private fun logInChunks(tag: String, message: String, logFunction: (String, String) -> Unit) {
        // Split the string into chunks
        var i = 0
        while (i < message.length) {
            val end = (i + MAX_LOG_LENGTH).coerceAtMost(message.length)
            val chunk = message.substring(i, end)
            logFunction(tag, chunk)
            i += MAX_LOG_LENGTH
        }
    }
}

/**
 * Extension function to log a VERBOSE message.
 * Examples:
 * Logging an Exception with a Stack Trace:
 * try {
 *     // Some code that might throw an exception
 *     int result = 10 / 0;
 * } catch (ArithmeticException e) {
 *     // Before
 *     Log.e("MyViewModel", "An error occurred during calculation.", e);
 *
 *     // After (the stack trace will be printed automatically in Logcat)
 *     Logger.e("MyViewModel", "An error occurred during calculation.", e);
 * }
 * -----------------------------------------------------------------
 * Before
 * Log.d("MyActivity", "User data loaded successfully.");
 *
 * After
 * Logger.d("MyActivity", "User data loaded successfully.");
 * Or using the default tag
 * Logger.d("User data loaded successfully.");
 *
 */
