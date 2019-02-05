package ru.hse.egorov.command

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import ru.hse.egorov.environment.Environment
import ru.hse.egorov.parser.CommandToken

internal class PwdCommandTest {
    private val env = Environment()

    @Test
    fun testPwd() {
        val pwdCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.PWD, env)
        assertTrue(pwdCommand.execute(listOf(), "").contains("src"))
        assertTrue(pwdCommand.execute(listOf(), "").contains("build.gradle"))
        assertTrue(pwdCommand.execute(listOf(), "").contains("gradlew"))
    }
}