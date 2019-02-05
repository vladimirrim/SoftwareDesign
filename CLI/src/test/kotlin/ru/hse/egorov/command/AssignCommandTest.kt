package ru.hse.egorov.command

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.hse.egorov.environment.Environment
import ru.hse.egorov.parser.CommandToken

internal class AssignCommandTest{

    @Test
    fun testAssignValue(){
        val env = Environment()
        val assignCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.ASSIGN_COMMAND, env)
        assignCommand.execute(listOf("a=b"), "")
        assertEquals("b", env.getVariable("a"))
    }

    @Test
    fun testReassignValue(){
        val env = Environment()
        val assignCommand = CommandFactory.getCommandByType(CommandToken.Companion.CommandType.ASSIGN_COMMAND, env)
        assignCommand.execute(listOf("a=b"), "")
        assignCommand.execute(listOf("a=bdce"), "")
        assertEquals("bdce", env.getVariable("a"))
    }
}