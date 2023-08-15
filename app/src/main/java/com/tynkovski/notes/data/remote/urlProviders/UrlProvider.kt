package com.tynkovski.notes.data.remote.urlProviders

interface UrlProvider {
    val url: String
}

class UrlProviderImpl(
    override val url: String
): UrlProvider