package ru.hse.egorov.command

import ru.hse.egorov.environment.Environment
import ru.hse.egorov.parser.CommandToken
import ru.hse.egorov.parser.CommandToken.Companion.CommandType.*


/**
 * This class represents CLI commands factory.
 */
class CommandFactory {
    companion object {
        fun getCommandByType(type: CommandToken.Companion.CommandType, env: Environment): Command {
            return when (type) {
                GREP -> GrepCommand()
                WC -> WcCommand()
                CAT -> CatCommand()
                ECHO -> EchoCommand()
                PWD -> PwdCommand()
                EXIT -> ExitCommand()
                ASSIGN_COMMAND -> AssignCommand(env)
                else -> UnknownCommand()
            }
        }
    }
}