package com.example.util

import java.util.regex.Pattern

data class ParsedHtmlImage(
    val url: String,
    val altText: String = "",
    val title: String = "",
    val sourceTag: String = ""
)

object HtmlImageParser {

    private val IMG_TAG_PATTERN = Pattern.compile(
        "<img\\b([^>]*)/?>",
        Pattern.CASE_INSENSITIVE
    )

    private val SRC_PATTERN = Pattern.compile(
        """\bsrc\s*=\s*["']([^"']+)["']""",
        Pattern.CASE_INSENSITIVE
    )

    private val ALT_PATTERN = Pattern.compile(
        """\balt\s*=\s*["']([^"']*)["']""",
        Pattern.CASE_INSENSITIVE
    )

    private val DATA_ALT_PATTERN = Pattern.compile(
        """\bdata-alt\s*=\s*["']([^"']*)["']""",
        Pattern.CASE_INSENSITIVE
    )

    private val TITLE_PATTERN = Pattern.compile(
        """\btitle\s*=\s*["']([^"']*)["']""",
        Pattern.CASE_INSENSITIVE
    )

    private val CSS_BG_PATTERN = Pattern.compile(
        """url\s*\(\s*['"]?([^'")]+)['"]?\s*\)""",
        Pattern.CASE_INSENSITIVE
    )

    /**
     * Parses an HTML string and extracts all referenced image URLs, including <img> tags
     * and CSS background-image: url(...) declarations.
     */
    fun extractImagesFromHtml(html: String): List<ParsedHtmlImage> {
        val results = mutableListOf<ParsedHtmlImage>()
        val seenUrls = mutableSetOf<String>()

        // 1. Match all <img ...> tags
        val imgMatcher = IMG_TAG_PATTERN.matcher(html)
        while (imgMatcher.find()) {
            val fullTag = imgMatcher.group(0) ?: ""
            val attributes = imgMatcher.group(1) ?: ""

            val srcMatcher = SRC_PATTERN.matcher(attributes)
            if (srcMatcher.find()) {
                val url = srcMatcher.group(1)?.trim() ?: ""
                if (url.isNotEmpty() && !url.startsWith("data:") && seenUrls.add(url)) {
                    val alt = (findAttribute(attributes, ALT_PATTERN)
                        ?: findAttribute(attributes, DATA_ALT_PATTERN) ?: "").trim()
                    val title = (findAttribute(attributes, TITLE_PATTERN) ?: "").trim()

                    results.add(
                        ParsedHtmlImage(
                            url = url,
                            altText = alt,
                            title = title,
                            sourceTag = fullTag
                        )
                    )
                }
            }
        }

        // 2. Match CSS background-image url(...)
        val cssMatcher = CSS_BG_PATTERN.matcher(html)
        while (cssMatcher.find()) {
            val bgUrl = cssMatcher.group(1)?.trim() ?: ""
            if (bgUrl.isNotEmpty() && !bgUrl.startsWith("data:") && seenUrls.add(bgUrl)) {
                results.add(
                    ParsedHtmlImage(
                        url = bgUrl,
                        altText = "Background Image Asset",
                        title = "CSS Background Asset",
                        sourceTag = cssMatcher.group(0) ?: ""
                    )
                )
            }
        }

        return results
    }

    private fun findAttribute(attributes: String, pattern: Pattern): String? {
        val matcher = pattern.matcher(attributes)
        return if (matcher.find()) {
            matcher.group(1)
        } else {
            null
        }
    }
}
