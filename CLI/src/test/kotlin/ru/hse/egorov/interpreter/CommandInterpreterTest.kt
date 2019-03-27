package ru.hse.egorov.interpreter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.hse.egorov.environment.Environment
import ru.hse.egorov.parser.CommandToken
import ru.hse.egorov.parser.ParsedToken

internal class CommandInterpreterTest {
    private val env = Environment()

    @Test
    fun testOneCommand() {
        val interpreter = InterpreterFactory.getCliInterpreter(env)
        assertEquals(
            "123",
            interpreter.interpret(listOf(ParsedToken(CommandToken.Companion.CommandType.ECHO, listOf("123"))))
        )
    }

    @Test
    fun testThreeCommands() {
        val interpreter = InterpreterFactory.getCliInterpreter(env)
        assertEquals(
            "123", interpreter.interpret(
                listOf(
                    ParsedToken(CommandToken.Companion.CommandType.ECHO, listOf("123")),
                    ParsedToken(CommandToken.Companion.CommandType.CAT, listOf("")),
                    ParsedToken(CommandToken.Companion.CommandType.CAT, listOf(""))
                )
            )
        )
    }
}