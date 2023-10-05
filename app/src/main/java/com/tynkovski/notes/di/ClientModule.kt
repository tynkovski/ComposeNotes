package com.tynkovski.notes.di

import com.tynkovski.notes.data.local.holders.TokenHolder
import com.tynkovski.notes.data.remote.interceptors.TokenInterceptor
import com.tynkovski.notes.data.remote.logging.PRETTY
import com.tynkovski.notes.data.remote.urlProviders.UrlProviderImpl
import com.tynkovski.notes.data.remote.urlProviders.UrlProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.JsonConvertException
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val REST_BASE = "rest_client"
const val REST_AUTH = "rest_auth_client"

val clientModule = module {
    factory<Logger> { Logger.PRETTY }

    factory<UrlProvider> { UrlProviderImpl("192.168.0.109:8080") }

    factory<URLProtocol> { URLProtocol.HTTP } // https

    single<HttpClient>(named(REST_AUTH)) {
        HttpClient(OkHttp) {
            install(Logging) {
                level = LogLevel.ALL
                logger = get<Logger>()
            }

            install(ContentNegotiation) {
                json(
                    json = get<Json>(),
                    contentType = ContentType.Application.Json
                )
            }

            defaultRequest {
                host = get<UrlProvider>().url
                url {
                    protocol = get<URLProtocol>()
                }
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
            }

            HttpResponseValidator {
                handleResponseExceptionWithRequest { cause, _ ->
                    when (cause) {
                        is JsonConvertException -> cause.printStackTrace()
                        else -> throw cause
                    }
                }
            }
        }
    }

    single<HttpClient>(named(REST_BASE)) {
        HttpClient(OkHttp) {
             engine {
                 addInterceptor(TokenInterceptor(get<TokenHolder>()))
             }

            install(Logging) {
                level = LogLevel.ALL
                logger = get<Logger>()
            }

            install(ContentNegotiation) {
                json(
                    json = get<Json>(),
                    contentType = ContentType.Application.Json
                )
            }

            defaultRequest {
                host = get<UrlProvider>().url
                url {
                    protocol = get<URLProtocol>()
                }
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
            }

            HttpResponseValidator {
                handleResponseExceptionWithRequest { cause, _ ->
                    when (cause) {
                        is JsonConvertException -> cause.printStackTrace()
                        else -> throw cause
                    }
                }
            }
        }
    }
}