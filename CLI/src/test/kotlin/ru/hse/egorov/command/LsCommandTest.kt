package ru.hse.egorov.command

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.hse.egorov.environment.Environment
import ru.hse.egorov.parser.CommandToken

class LsCommandTest {
    private val environment = Environment()
    private val lsCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.LS, environment)

    @Test
    fun testLs() {
        val output = lsCommand.execute(listOf("src"), "")
        assertTrue(output.contains("main"))
        assertTrue(output.contains("test"))
    }

    @Test
    fun testLsNoArgs() {
        val output = lsCommand.execute(listOf(), "")
        assertTrue(output.contains("src"))
    }

    @Test
    fun testLsTwoArgs() {
        val output = lsCommand.execute(listOf("a", "b"), "")
        assertTrue(output.contains("Too many arguments"))
    }

    @Test
    fun testLsUnexistentPath() {
        val output = lsCommand.execute(listOf("unexistent/path"), "")
        assertTrue(output.contains("No such file or directory"))
    }

    @Test
    fun testLsFile() {
        val output = lsCommand.execute(listOf("src/test/resources/test.txt"), "")
        assertEquals("src/test/resources/test.txt\n", output)
    }
}
