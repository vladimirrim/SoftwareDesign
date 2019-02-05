package ru.hse.egorov.parser

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CommandParserTest {

    @Test
    fun testOneCommand() {
        val parser = ParserFactory.getCommandParser()
        val answer = listOf(CommandToken(CommandToken.Companion.CommandType.ECHO, "123"))

        assertEquals(answer, parser.parse("echo 123"))
    }

    @Test
    fun testThreeCommands() {
        val parser = ParserFactory.getCommandParser()
        val answer = listOf(
            CommandToken(CommandToken.Companion.CommandType.ECHO, "123"),
            CommandToken(CommandToken.Companion.CommandType.CAT, ""),
            CommandToken(CommandToken.Companion.CommandType.WC, "")
        )

        assertEquals(answer, parser.parse("echo 123|cat | wc"))
    }

    @Test
    fun testAssignCommand() {
        val parser = ParserFactory.getCommandParser()
        val answer = listOf(CommandToken(CommandToken.Companion.CommandType.ASSIGN_COMMAND, "a=b"))

        assertEquals(answer, parser.parse("a=b"))
    }

    @Test
    fun testUnknownCommand() {
        val parser = ParserFactory.getCommandParser()
        val answer = listOf(CommandToken(CommandToken.Companion.CommandType.UNKNOWN_COMMAND, "jojo"))

        assertEquals(answer, parser.parse("jojo"))
    }
}