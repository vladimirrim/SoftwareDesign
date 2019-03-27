package ru.hse.egorov.parser

import ru.hse.egorov.environment.Environment
import ru.hse.egorov.parser.CommandToken.Companion.CommandType.ECHO
import ru.hse.egorov.parser.CommandToken.Companion.CommandType.STRONG_QUOTE

/**
 * This class implements command line input parser.
 */
class CliParser(private val env: Environment) {

    fun parse(input: String): List<ParsedToken> {
        return try {
            val unquotedString = ParserFactory.getQuoteParser().parse(input)
            ParserFactory.getCommandParser().parse(unquotedString.map { token ->
                when (token.type) {
                    STRONG_QUOTE -> token
                    else -> {
                        parseEnvironmentVariables(token)
                    }
                }
            })
        } catch (e: QuoteParsingException) {
            listOf(ParsedToken(ECHO, listOf(e.localizedMessage)))
        }
    }

    private fun parseEnvironmentVariables(token: CommandToken): CommandToken {
        val input = token.args
        var firstEntry = input[0] != ASSIGN_OPERATOR
        val commandString = input.split("$ASSIGN_OPERATOR").joinToString("") {
            if (firstEntry) {
                firstEntry = false
                it
            } else {
                val key = it.takeWhile { char ->
                    !char.isWhitespace() && char != '\'' && char != '"'
                }
                it.replaceFirst(key, env.getVariable(key))
            }
        }

        return CommandToken(token.type, commandString)
    }

    companion object {
        private const val ASSIGN_OPERATOR = '$'
    }
}