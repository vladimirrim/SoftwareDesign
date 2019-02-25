package ru.hse.egorov.parser

import ru.hse.egorov.parser.CommandToken.Companion.CommandType.*
import ru.hse.egorov.parser.CommandToken.Companion.CommandType.Companion.getCommandByName


/**
 * This class implements command input parser.
 */
class CommandParser : Parser {

    override fun parse(input: String): List<CommandToken> {
        val commands = input.split("$PIPE_OPERATOR")
        return if (commands.all { command -> command.split("\\s+".toRegex()).any { it.isNotEmpty() } }) {
            input.split("$PIPE_OPERATOR").map { command ->
                val tokens = command.split("\\s+".toRegex()).filter { it.isNotEmpty() }
                if (commands.size == 1 && tokens[0].split("=").size > 1) {
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
        } else {
            listOf(CommandToken(ECHO, PIPE_ERROR_MESSAGE))
        }
    }

    companion object {
        private const val PIPE_ERROR_MESSAGE = "Invalid pipe command."
        private const val PIPE_OPERATOR = '|'
    }
}