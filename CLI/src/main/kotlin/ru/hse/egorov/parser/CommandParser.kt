package ru.hse.egorov.parser

import ru.hse.egorov.parser.CommandToken.Companion.CommandType.ASSIGN_COMMAND
import ru.hse.egorov.parser.CommandToken.Companion.CommandType.Companion.getCommandByName
import ru.hse.egorov.parser.CommandToken.Companion.CommandType.UNKNOWN_COMMAND


/**
 * This class implements command input parser.
 */
class CommandParser : Parser {

    override fun parse(input: String): List<CommandToken> {
        return input.split("$PIPE_OPERATOR").filter { it.isNotEmpty() }.map { command ->
            val tokens = command.split("\\s+".toRegex()).filter { it.isNotEmpty() }
            if (tokens.size == 1 && command.split("=").size == 2) {
                CommandToken(ASSIGN_COMMAND, command)
            } else {
                val commandType = getCommandByName(tokens[0])
                if (commandType == UNKNOWN_COMMAND) {
                    CommandToken(commandType, tokens.subList(0, tokens.size).joinToString(" "))
                } else {
                    CommandToken(commandType, tokens.subList(1, tokens.size).joinToString(" "))
                }
            }
        }
    }

    companion object {
        private const val PIPE_OPERATOR = '|'
    }
}