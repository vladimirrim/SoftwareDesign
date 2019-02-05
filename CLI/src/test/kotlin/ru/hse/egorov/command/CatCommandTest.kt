package ru.hse.egorov.command

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.hse.egorov.environment.Environment
import ru.hse.egorov.parser.CommandToken
import java.io.File

internal class CatCommandTest {
    private val sep = File.separator
    private val lineSep = "\r\n"
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
        assertEquals("Unable to read file: jojo\n", catCommand.execute(listOf("jojo"), ""))
    }

    @Test
    fun testOneFile() {
        val catCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.CAT, env)
        assertEquals(
            "Some example text\n", catCommand.execute(
                listOf(testDirPrefix + "example.txt"),
                ""
            )
        )
    }

    @Test
    fun testTwoFiles() {
        val catCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.CAT, env)
        val answer = "Some example text1${lineSep}2${lineSep}3\n"
        assertEquals(
            answer, catCommand.execute(
                listOf(testDirPrefix + "example.txt", testDirPrefix + "test.txt"),
                ""
            )
        )
    }
}