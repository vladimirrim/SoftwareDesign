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
                GREP -> GrepCommand(env)
                WC -> WcCommand(env)
                CAT -> CatCommand(env)
                ECHO -> EchoCommand()
                PWD -> PwdCommand(env)
                EXIT -> ExitCommand()
                ASSIGN_COMMAND -> AssignCommand(env)
                CD -> CdCommand(env)
                LS -> LsCommand(env)
                else -> UnknownCommand(env)
            }
        }
    }
}