package com.example

import com.example.util.HtmlImageParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `extractImagesFromHtml parses img tags correctly`() {
        val html = """
            <div class="card">
                <img src="https://example.com/logo.png" alt="RepBattle Logo" />
                <img class="avatar" src='https://example.com/athlete.jpg' data-alt='Marouane Headshot' />
            </div>
        """.trimIndent()

        val images = HtmlImageParser.extractImagesFromHtml(html)
        assertEquals(2, images.size)
        assertEquals("https://example.com/logo.png", images[0].url)
        assertEquals("RepBattle Logo", images[0].altText)
        assertEquals("https://example.com/athlete.jpg", images[1].url)
        assertEquals("Marouane Headshot", images[1].altText)
    }

    @Test
    fun `extractImagesFromHtml parses css background image urls`() {
        val html = """
            <div style="background-image: url('https://example.com/gym-dark.jpg');"></div>
        """.trimIndent()

        val images = HtmlImageParser.extractImagesFromHtml(html)
        assertEquals(1, images.size)
        assertEquals("https://example.com/gym-dark.jpg", images[0].url)
    }
}
