package ru.hse.egorov.command

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.hse.egorov.environment.Environment
import ru.hse.egorov.parser.CommandToken
import java.io.File

internal class GrepCommandTest {
    private val sep = File.separator
    private val env = Environment()
    private val testDirPrefix = ".${sep}src${sep}test${sep}resources$sep"

    @Test
    fun testPreviousResult() {
        val grepCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.GREP, env)
        assertEquals("jojo\n", grepCommand.execute(listOf("(jo)+"), "jojo"))
    }

    @Test
    fun testNoSuchFile() {
        val grepCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.GREP, env)
        assertEquals("Unable to read file: jojo\n", grepCommand.execute(listOf("jojo", "jojo"), ""))
    }

    @Test
    fun testOneFile() {
        val grepCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.GREP, env)
        assertEquals(
            "Some example text\n", grepCommand.execute(
                listOf("text", testDirPrefix + "example.txt"),
                ""
            )
        )
    }

    @Test
    fun testTwoFiles() {
        val grepCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.GREP, env)
        val answer = "123\n1\n"
        assertEquals(
            answer, grepCommand.execute(
                listOf("1", testDirPrefix + "grep_test_1.txt", testDirPrefix + "test.txt"),
                ""
            )
        )
    }

    @Test
    fun testIgnoreCase() {
        val grepCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.GREP, env)
        assertEquals(
            "Some example text\n", grepCommand.execute(
                listOf("-i", "some", testDirPrefix + "example.txt"),
                ""
            )
        )
    }

    @Test
    fun testWordMatching() {
        val grepCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.GREP, env)
        val answer = "1\n"
        assertEquals(
            answer, grepCommand.execute(
                listOf("-w", "1", testDirPrefix + "grep_test_1.txt", testDirPrefix + "test.txt"),
                ""
            )
        )
    }

    @Test
    fun testAfterContextOneMatch() {
        val grepCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.GREP, env)
        val answer = "123\n8\n6\n1\n2\n3\n"
        assertEquals(
            answer, grepCommand.execute(
                listOf("-A", "2", "1", testDirPrefix + "grep_test_1.txt", testDirPrefix + "test.txt"),
                ""
            )
        )
    }

    @Test
    fun testAfterContextIntersectMatches() {
        val grepCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.GREP, env)
        val answer = "123\n8\n6\n1\n2\n3\n"
        assertEquals(
            answer, grepCommand.execute(
                listOf("-A", "3", "1", testDirPrefix + "grep_test_1.txt", testDirPrefix + "test.txt"),
                ""
            )
        )
    }

    @Test
    fun testTwoKeys() {
        val grepCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.GREP, env)
        val answer = "1\n2\n3\n"
        assertEquals(
            answer, grepCommand.execute(
                listOf("-A", "2", "-w", "1", testDirPrefix + "grep_test_1.txt", testDirPrefix + "test.txt"),
                ""
            )
        )
    }
}