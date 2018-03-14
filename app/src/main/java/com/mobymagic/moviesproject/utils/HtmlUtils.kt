package com.mobymagic.moviesproject.utils

import android.text.Html
import android.text.Spanned

class HtmlUtils {

    /**
     * Helper method that uses the new Html.fromHtml on Nougat and above devices while using
     * the deprecated one on Lower devices with API version lower than Nougat
     * @param text The html text
     * @return A spanned html text
     */
    fun fromHtml(text: String): Spanned {
        return if (VersionUtils.hasNougat()) {
            Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(text)
        }
    }

}