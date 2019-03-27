package ru.hse.egorov.command

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.hse.egorov.environment.Environment
import ru.hse.egorov.interpreter.CommandInterpretingException
import ru.hse.egorov.parser.CommandToken
import java.io.File

internal class WcCommandTest {
    private val sep = File.separator
    private val lineSep = System.lineSeparator()
    private val env = Environment()
    private val testDirPrefix = ".${sep}src${sep}test${sep}resources$sep"

    @Test
    fun testPreviousResult() {
        val wcCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.WC, env)
        assertEquals("1 1 4$lineSep", wcCommand.execute(listOf(), "jojo"))
    }

    @Test
    fun testNoSuchFile() {
        val wcCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.WC, env)
        assertThrows<CommandInterpretingException>("Unable to read file: jojo$lineSep") {
            wcCommand.execute(listOf("jojo"), "")
        }
    }

    @Test
    fun testOneFile() {
        val wcCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.WC, env)
        assertEquals(
            "1 3 17$lineSep", wcCommand.execute(
                listOf(testDirPrefix + "example.txt"),
                ""
            )
        )
    }

    @Test
    fun testTwoFiles() {
        val wcCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.WC, env)
        val answer = "1 3 17${lineSep}3 3 7$lineSep"
        assertEquals(
            answer, wcCommand.execute(
                listOf(testDirPrefix + "example.txt", testDirPrefix + "test.txt"),
                ""
            )
        )
    }
}