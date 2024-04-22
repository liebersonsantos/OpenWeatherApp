package com.liebersonsantos.openweatherapp.factory

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException
import java.net.URL

/**
 * Here's the file where I mock my Ktor clients. To set up a mock client, you just need to pass in
 * the `MockEngine` instead of the `Android` Engine.
 */

private val responseHeaders = headersOf(HttpHeaders.ContentType, "application/json")

val ktorErrorClient = HttpClient(MockEngine) {
    engine {
        addHandler {
            respond(
                content = """{}""",
                status = HttpStatusCode.BadRequest,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
    }
    expectSuccess = true
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
        level = LogLevel.ALL
    }
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        })
    }
}

fun createClient(
    json: String,
    status: HttpStatusCode
) = HttpClient(MockEngine) {
    engine {
        addHandler {
            respond(
                json,
                status,
                responseHeaders
            )
        }
    }
    expectSuccess = true
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
        level = LogLevel.ALL
    }
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        })
    }
}

fun String.readFile(): String {
    val content: URL? = ClassLoader.getSystemResource(this)
    return content?.readText() ?: throw FileNotFoundException("file path: $this was not found")
}