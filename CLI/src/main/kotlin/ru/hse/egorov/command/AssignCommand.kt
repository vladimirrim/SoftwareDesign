package ru.hse.egorov.command

import ru.hse.egorov.environment.Environment


/**
 * This class assigns new value to variable.
 */
class AssignCommand(private val env: Environment) : Command {
    override fun execute(args: List<String>, input: String): String {
        if (args.isEmpty())
            return ""

        val operands = args[0].split('=')
        val value = if (args.size > 1) {
            args.subList(1, args.size).joinToString(" ", operands[1] + " ")
        } else {
            operands[1]
        }
        env.setVariable(operands[0], value)
        return ""
    }
}