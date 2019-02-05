package ru.hse.egorov.command

import ru.hse.egorov.environment.Environment


/**
 * This class assigns new value to variable.
 */
class AssignCommand(private val env: Environment) : Command {
    override fun execute(args:List<String>, input: String): String {
        val operands = args[0].split('=')
        env.setVariable(operands[0], operands[1])
        return ""
    }
}