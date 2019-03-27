package ru.hse.egorov.parser

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.hse.egorov.parser.CommandToken.Companion.CommandType.PARSE_COMMAND

internal class CommandParserTest {

    @Test
    fun testOneCommand() {
        val parser = ParserFactory.getCommandParser()
        val answer = listOf(ParsedToken(CommandToken.Companion.CommandType.ECHO, listOf("123")))

        assertEquals(answer, parser.parse(listOf(CommandToken(PARSE_COMMAND, "echo 123"))))
    }

    @Test
    fun testThreeCommands() {
        val parser = ParserFactory.getCommandParser()
        val answer = listOf(
            ParsedToken(CommandToken.Companion.CommandType.ECHO, listOf("123")),
            ParsedToken(CommandToken.Companion.CommandType.CAT, listOf()),
            ParsedToken(CommandToken.Companion.CommandType.WC, listOf())
        )

        assertEquals(answer, parser.parse(listOf(CommandToken(PARSE_COMMAND, "echo 123|cat | wc"))))
    }

    @Test
    fun testAssignCommand() {
        val parser = ParserFactory.getCommandParser()
        val answer = listOf(ParsedToken(CommandToken.Companion.CommandType.ASSIGN_COMMAND, listOf("a=b")))

        assertEquals(answer, parser.parse(listOf(CommandToken(PARSE_COMMAND, "a=b"))))
    }

    @Test
    fun testUnknownCommand() {
        val parser = ParserFactory.getCommandParser()
        val answer = listOf(ParsedToken(CommandToken.Companion.CommandType.UNKNOWN_COMMAND, listOf("jojo")))

        assertEquals(answer, parser.parse(listOf(CommandToken(PARSE_COMMAND, "jojo"))))
    }
}