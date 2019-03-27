package ru.hse.egorov.parser

import ru.hse.egorov.parser.CommandToken.Companion.CommandType.*
import ru.hse.egorov.parser.CommandToken.Companion.CommandType.Companion.getCommandByName


/**
 * This class implements command tokens parser.
 */
class CommandParser {

    fun parse(input: List<CommandToken>): List<ParsedToken> {
        val commands = splitCommands(input)

        return if (commands.all { it.isNotEmpty() } && commands.all { command -> command.any { it.isNotEmpty() } }) {
            commands.map { command ->
                if (commands.size == 1 && command[0].split("=").size > 1) {
                    ParsedToken(ASSIGN_COMMAND, listOf(command[0]))
                } else {
                    val commandType = getCommandByName(command[0])
                    if (commandType == UNKNOWN_COMMAND) {
                        ParsedToken(commandType, command.subList(0, command.size))
                    } else {
                        ParsedToken(commandType, command.subList(1, command.size))
                    }
                }
            }
        } else {
            listOf(ParsedToken(ECHO, listOf(PIPE_ERROR_MESSAGE)))
        }
    }

    private fun splitCommands(input: List<CommandToken>): MutableList<MutableList<String>> {
        var quote = ""
        val commands = mutableListOf<MutableList<String>>()
        var isSeparateLast = true

        input.forEach { token ->
            if (token.type == PARSE_COMMAND) {
                val tokens = token.args.split(PIPE_OPERATOR).toMutableList()

                if (tokens.first().isEmpty())
                    tokens[0] += " "
                val isSeparateFirst = tokens[0].first().isWhitespace()

                if (isSeparateLast) {
                    if (commands.isEmpty())
                        commands.add(mutableListOf())
                    commands.last().add(quote)
                } else
                    commands.last()[commands.last().size - 1] += quote
                quote = ""

                val firstCommandParts = tokens[0].split("\\s+".toRegex())
                if (firstCommandParts.any { it.isNotEmpty() }) {
                    if (isSeparateFirst || commands.isEmpty()) {
                        if (commands.isEmpty())
                            commands.add(mutableListOf())
                        commands.last().addAll(firstCommandParts)
                    } else {
                        commands.last()[commands.last().size - 1] += firstCommandParts.first()
                        if (firstCommandParts.size > 1)
                            commands.last().addAll(firstCommandParts.subList(1, firstCommandParts.size))
                    }
                }

                if (tokens.last().isEmpty()) {
                    tokens[tokens.size - 1] += " "
                }
                isSeparateLast = tokens.last().first().isWhitespace()
                tokens.removeAt(0)
                tokens.forEach { command ->
                    commands.add(command.split("\\s+".toRegex()).filter { it.isNotEmpty() }.toMutableList())
                }
            } else {
                quote += token.args
            }
        }

        if (quote.isNotEmpty()) {
            if (isSeparateLast)
                commands.last().add(quote)
            else
                commands.last()[commands.last().size - 1] += quote
        }
        return commands
    }

    companion object {
        private const val PIPE_ERROR_MESSAGE = "Invalid pipe command."
        private const val PIPE_OPERATOR = '|'
    }
}