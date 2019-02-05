package ru.hse.egorov.command

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.hse.egorov.environment.Environment
import ru.hse.egorov.parser.CommandToken
import java.io.File

internal class WcCommandTest {
    private val sep = File.separator
    private val env = Environment()
    private val testDirPrefix = ".${sep}src${sep}test${sep}resources$sep"

    @Test
    fun testPreviousResult() {
        val wcCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.WC, env)
        assertEquals("1 1 4\n", wcCommand.execute(listOf(), "jojo"))
    }

    @Test
    fun testNoSuchFile() {
        val wcCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.WC, env)
        assertEquals("Unable to read file: jojo\n", wcCommand.execute(listOf("jojo"), ""))
    }

    @Test
    fun testOneFile() {
        val wcCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.WC, env)
        assertEquals(
            "1 3 17\n", wcCommand.execute(
                listOf(testDirPrefix + "example.txt"),
                ""
            )
        )
    }

    @Test
    fun testTwoFiles() {
        val wcCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.WC, env)
        val answer = "1 3 17\n3 3 7\n"
        assertEquals(
            answer, wcCommand.execute(
                listOf(testDirPrefix + "example.txt", testDirPrefix + "test.txt"),
                ""
            )
        )
    }
}