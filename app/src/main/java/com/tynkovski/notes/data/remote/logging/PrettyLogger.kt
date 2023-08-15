package com.tynkovski.notes.data.remote.logging

import io.ktor.client.plugins.logging.Logger
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

val Logger.Companion.PRETTY: Logger get() = PrettyLoggerKtor()

val Logger.Companion.ANALYTICS: Logger get() = object : Logger {
    override fun log(message: String) {
        Analytics.leaveBreadcrumb(BreadcrumbType.NetworkInfo, message)
    }
}

private val formatter by lazy { PrettyFormatter.JsonBase() }

private const val KTOR_JSON_START_DELIMITER = "BODY START"
private const val KTOR_JSON_END_DELIMITER = "BODY END"

class PrettyLoggerKtor(
    private val maxLength: Int = 4000,
    private val minLength: Int = 3000,
    private val delegate: Logger = Logger.ANALYTICS,
) : Logger {

    override fun log(message: String) {
        val formattedMessage = formatKtorMessage(message)
        logLong(formattedMessage)
    }

    private fun String.getKtorStartMessage(): String {
        return substringBefore(KTOR_JSON_START_DELIMITER) + KTOR_JSON_START_DELIMITER
    }

    private fun String.getKtorJsonMessage(): String {
        return formatter.prettyFormat(
            this.substringAfter(KTOR_JSON_START_DELIMITER)
                .substringBefore(KTOR_JSON_END_DELIMITER)
        )
    }

    private fun String.getKtorEndMessage(): String =
        substringAfter(KTOR_JSON_END_DELIMITER) + KTOR_JSON_END_DELIMITER

    private fun formatKtorMessage(message: String): String =
        message.getKtorStartMessage() + "\n" + message.getKtorJsonMessage() + "\n" + message.getKtorEndMessage()

    private tailrec fun logLong(message: String) {
        // String to be logged is longer than the max...
        if (message.length > maxLength) {
            var msgSubstring = message.substring(0, maxLength)
            var msgSubstringEndIndex = maxLength

            // Try to find a substring break at a newline char.
            msgSubstring.lastIndexOf('\n').let { lastIndex ->
                if (lastIndex >= minLength) {
                    msgSubstring = msgSubstring.substring(0, lastIndex)
                    // skip over new line char
                    msgSubstringEndIndex = lastIndex + 1
                }
            }

            // Log the substring.
            delegate.log(msgSubstring)

            // Recursively log the remainder.
            logLong(message.substring(msgSubstringEndIndex))
        } else {
            delegate.log(message)
        } // String to be logged is shorter than the max...
    }
}

interface PrettyFormatter {

    fun prettyFormat(message: String): String

    class JsonBase : PrettyFormatter {
        private val json = Json {
            prettyPrint = true
            useArrayPolymorphism = true
        }

        override fun prettyFormat(message: String): String {
            return runCatching {
                val jsonObject = json.decodeFromString<JsonObject>(message)
                json.encodeToString(jsonObject)
            }.getOrDefault(message)
        }
    }
}