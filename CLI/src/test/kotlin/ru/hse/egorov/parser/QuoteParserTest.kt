package ru.hse.egorov.parser

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import ru.hse.egorov.parser.CommandToken.Companion.CommandType.*

internal class QuoteParserTest {

    @Test
    fun testNoQuotes() {
        val parser = ParserFactory.getQuoteParser()
        val answer = listOf(CommandToken(PARSE_COMMAND, "123"))

        assertEquals(answer, parser.parse("123"))
    }

    @Test
    fun testWeakQuotes() {
        val parser = ParserFactory.getQuoteParser()
        val answer = listOf(CommandToken(WEAK_QUOTE, "123"))

        assertEquals(answer, parser.parse("\"123\""))
    }

    @Test
    fun testStrongQuotes() {
        val parser = ParserFactory.getQuoteParser()
        val answer = listOf(CommandToken(STRONG_QUOTE, "123"))

        assertEquals(answer, parser.parse("'123'"))
    }

    @Test
    fun testWeakAndStrongQuotes() {
        val parser = ParserFactory.getQuoteParser()
        val answer = listOf(
            CommandToken(STRONG_QUOTE, "123"),
            CommandToken(WEAK_QUOTE, "123")
        )

        assertEquals(answer, parser.parse("'123'\"123\""))
    }

    @Test
    fun testStrongQuotesInsideWeakQuotes() {
        val parser = ParserFactory.getQuoteParser()
        val answer = listOf(CommandToken(WEAK_QUOTE, "'123'"))

        assertEquals(answer, parser.parse("\"'123'\""))
    }

    @Test
    fun testInvalidQuotes() {
        val parser = ParserFactory.getQuoteParser()

        assertThrows(QuoteParsingException::class.java) {
            parser.parse("\"123")
        }
    }
}