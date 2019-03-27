package ru.hse.egorov.interpreter

import ru.hse.egorov.command.CommandFactory
import ru.hse.egorov.environment.Environment
import ru.hse.egorov.parser.ParsedToken


/**
 * This class implements CLI commands executor.
 */
class CommandInterpreter(private val env: Environment) : Interpreter {

    override fun interpret(commands: List<ParsedToken>): String {
        var output = ""

        try {
            commands.forEach { command ->
                output = CommandFactory.getCommandByType(command.type, env)
                    .execute(command.args.filter { it.isNotEmpty() }, output)
            }
        } catch (e: CommandInterpretingException) {
            return e.message!!
        }

        return output
    }
}