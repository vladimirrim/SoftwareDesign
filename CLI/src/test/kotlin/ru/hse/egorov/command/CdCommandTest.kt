package ru.hse.egorov.command

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.hse.egorov.environment.Environment
import ru.hse.egorov.parser.CommandToken
import java.io.File

internal class CdCommandTest {
    @Test
    fun testCd() {
        val env = Environment()
        val cdCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.CD, env)
        assertEquals("", cdCommand.execute(listOf("src"), ""))
        assertEquals(File("src").absolutePath, File(env.getWorkDir()).absolutePath)
    }

    @Test
    fun testCdNoArgs() {
        val env = Environment()
        val cdCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.CD, env)
        assertEquals("", cdCommand.execute(listOf(), ""))
        assertEquals(File(env.getHomeDir()).absolutePath, File(env.getWorkDir()).absolutePath)
    }

    @Test
    fun testCdToUnexistentPath() {
        val env = Environment()
        val cdCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.CD, env)
        assertTrue(cdCommand.execute(listOf("unexistent/path"), "").contains("No such file or directory"))
    }

    @Test
    fun testCdToFile() {
        val env = Environment()
        val cdCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.CD, env)
        assertTrue(cdCommand.execute(listOf("src/test/resources/test.txt"), "").contains("Not a directory"))
    }

    @Test
    fun testTwoArgsToCd() {
        val env = Environment()
        val cdCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.CD, env)
        assertTrue(cdCommand.execute(listOf("a", "b"), "").contains("Too many arguments"))
    }
}
