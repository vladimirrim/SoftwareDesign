package ru.hse.egorov.parser

import ru.hse.egorov.environment.Environment
import ru.hse.egorov.parser.CommandToken.Companion.CommandType.*

/**
 * This class implements command line input parser.
 */
class CliParser(private val env: Environment) : Parser {

    override fun parse(input: String): List<CommandToken> {
        return try {
            val unquotedString = ParserFactory.getQuoteParser().parse(input)
            unquotedString.flatMap { token ->
                when (token.type) {
                    STRONG_QUOTE -> listOf(CommandToken(STRING, token.args))
                    WEAK_QUOTE -> listOf(parseEnvironmentVariables(token.args))
                    else -> ParserFactory.getCommandParser().parse(parseEnvironmentVariables(token.args).args)
                }
            }
        } catch (e: QuoteParsingException) {
            listOf(CommandToken(ECHO, e.localizedMessage))
        }
    }

    private fun parseEnvironmentVariables(input: String): CommandToken {
        val commandString = input.split("\\s+".toRegex()).filter { it.isNotEmpty() }.joinToString(" ") { token ->
            var firstEntry = token[0] != ASSIGN_OPERATOR
            token.split("$ASSIGN_OPERATOR").joinToString("") {
                if (firstEntry)
                    it
                else {
                    firstEntry = false
                    env.getVariable(it)
                }
            }
        }

        return CommandToken(STRING, commandString)
    }

    companion object {
        private const val ASSIGN_OPERATOR = '$'
    }
}