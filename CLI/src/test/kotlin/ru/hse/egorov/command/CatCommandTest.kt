package ru.hse.egorov.command

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.hse.egorov.environment.Environment
import ru.hse.egorov.interpreter.CommandInterpretingException
import ru.hse.egorov.parser.CommandToken
import java.io.File

internal class CatCommandTest {
    private val sep = File.separator
    private val lineSep = "\r\n"
    private val catLineSep = System.lineSeparator()
    private val env = Environment()
    private val testDirPrefix = ".${sep}src${sep}test${sep}resources$sep"

    @Test
    fun testPreviousResult() {
        val catCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.CAT, env)
        assertEquals("jojo", catCommand.execute(listOf(), "jojo"))
    }

    @Test
    fun testNoSuchFile() {
        val catCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.CAT, env)
        assertThrows<CommandInterpretingException>("Unable to read file: jojo$catLineSep") {
            catCommand.execute(listOf("jojo"), "")
        }
    }

    @Test
    fun testOneFile() {
        val catCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.CAT, env)
        assertEquals(
            "Some example text$catLineSep", catCommand.execute(
                listOf(testDirPrefix + "example.txt"),
                ""
            )
        )
    }

    @Test
    fun testTwoFiles() {
        val catCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.CAT, env)
        val answer = "Some example text1${lineSep}2${lineSep}3$catLineSep"
        assertEquals(
            answer, catCommand.execute(
                listOf(testDirPrefix + "example.txt", testDirPrefix + "test.txt"),
                ""
            )
        )
    }
}