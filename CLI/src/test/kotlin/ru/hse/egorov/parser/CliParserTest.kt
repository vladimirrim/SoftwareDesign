package ru.hse.egorov.parser

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.hse.egorov.environment.Environment
import ru.hse.egorov.interpreter.InterpreterFactory

internal class CliParserTest {
    private var env = Environment()

    @Test
    fun testOneCommand() {
        val parser = ParserFactory.getCliParser(env)
        val answer = listOf(CommandToken(CommandToken.Companion.CommandType.ECHO, "123"))
        assertEquals(answer, parser.parse("echo 123"))
    }

    @Test
    fun testAssignmentCommand() {
        env = Environment()
        val parser = ParserFactory.getCliParser(env)
        val interpreter = InterpreterFactory.getCliInterpreter(env)
        interpreter.interpret(parser.parse("a=b"))

        assertEquals("b", env.getVariable("a"))
    }

    @Test
    fun testThreeCommands() {
        val parser = ParserFactory.getCliParser(env)
        val answer = listOf(
            CommandToken(CommandToken.Companion.CommandType.ECHO, "123"),
            CommandToken(CommandToken.Companion.CommandType.STRING, "123"),
            CommandToken((CommandToken.Companion.CommandType.CAT), "")
        )
        assertEquals(answer, parser.parse("echo 123\"123\" | cat"))
    }

    @Test
    fun testWeakQuotingSubstitution() {
        env = Environment()
        val parser = ParserFactory.getCliParser(env)
        val interpreter = InterpreterFactory.getCliInterpreter(env)
        interpreter.interpret(parser.parse("a=echo"))
        val answer = listOf(CommandToken(CommandToken.Companion.CommandType.ECHO, "123"))

        assertEquals(answer, parser.parse("\$a 123"))
    }
}