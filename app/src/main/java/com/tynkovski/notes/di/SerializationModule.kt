package com.tynkovski.notes.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module

@OptIn(ExperimentalSerializationApi::class)
val serializationModule = module {
    factory<Gson> { GsonBuilder().create() }

    factory<Json> {
        Json {
            isLenient = true
            ignoreUnknownKeys = true
            allowSpecialFloatingPointValues = true
            useArrayPolymorphism = false
            prettyPrint = true
            coerceInputValues = true
            encodeDefaults = true
            allowStructuredMapKeys = true
            explicitNulls = true
        }
    }
}