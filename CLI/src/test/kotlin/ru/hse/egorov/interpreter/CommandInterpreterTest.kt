package ru.hse.egorov.interpreter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.hse.egorov.environment.Environment
import ru.hse.egorov.parser.CommandToken

internal class CommandInterpreterTest {
    private val env = Environment()

    @Test
    fun testOneCommand() {
        val interpreter = InterpreterFactory.getCliInterpreter(env)
        assertEquals("123", interpreter.interpret(listOf(CommandToken(CommandToken.Companion.CommandType.ECHO, "123"))))
    }

    @Test
    fun testThreeCommands() {
        val interpreter = InterpreterFactory.getCliInterpreter(env)
        assertEquals(
            "123", interpreter.interpret(
                listOf(
                    CommandToken(CommandToken.Companion.CommandType.ECHO, "123"),
                    CommandToken(CommandToken.Companion.CommandType.CAT, ""),
                    CommandToken(CommandToken.Companion.CommandType.CAT, "")
                )
            )
        )
    }
}