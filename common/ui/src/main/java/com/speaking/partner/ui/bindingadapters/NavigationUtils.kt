package com.speaking.partner.ui.bindingadapters

import android.net.Uri
import androidx.core.net.toUri
import com.speaking.partner.ui.utils.DeepLinkConstants.DEEPLINK_BASE_URI

fun String.toDeepLink(arg: Map<String, Any>, baseUri: String? = null): Uri {
    if (arg.isNullOrEmpty()) throw NullPointerException("Map cannot be null or empty!")

    var query = (baseUri ?: DEEPLINK_BASE_URI) + this
    arg.map { query += it.key + "=" + it.value + "&" }

    return query.substring(0, query.length - 1).toUri()
}

fun String.toDeepLink(baseUri: String = DEEPLINK_BASE_URI) = (baseUri + this).toUri()