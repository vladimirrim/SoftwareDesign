package ru.hse.egorov.command

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.hse.egorov.environment.Environment
import ru.hse.egorov.parser.CommandToken

internal class EchoCommandTest{
    private val env = Environment()

    @Test
    fun testNoArguments(){
        val echoCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.ECHO, env)
        assertEquals("jojo", echoCommand.execute(listOf(), "jojo"))
    }

    @Test
    fun testTwoArguments(){
        val echoCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.ECHO, env)
        assertEquals("jo jo", echoCommand.execute(listOf("jo", "jo"), "abc"))
    }
}