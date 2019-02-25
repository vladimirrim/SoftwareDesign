package ru.hse.egorov.interpreter

import ru.hse.egorov.command.CommandFactory
import ru.hse.egorov.environment.Environment
import ru.hse.egorov.parser.CommandToken


/**
 * This class implements CLI commands executor.
 */
class CommandInterpreter(private val env: Environment) : Interpreter {

    override fun interpret(commands: List<CommandToken>): String {
        var output = ""
        commands.forEach { command ->
            output = CommandFactory.getCommandByType(command.type, env)
                .execute(command.args.split("\\s+".toRegex()).filter { it.isNotEmpty() }, output)
        }

        return output
    }
}