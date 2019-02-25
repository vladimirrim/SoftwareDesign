package ru.hse.egorov.parser

import ru.hse.egorov.parser.CommandToken.Companion.CommandType
import ru.hse.egorov.parser.CommandToken.Companion.CommandType.*

/**
 * This class parses string with quotes to CLI commands.
 */
class QuoteParser : Parser {

    override fun parse(input: String): List<CommandToken> {
        val result = ArrayList<CommandToken>()
        var i = 0
        while (i < input.length) {
            val parsingResult: List<ParsingResult> = when (input[i]) {
                WEAK_QUOTING -> {
                    parseWeakQuoting(i, input, WEAK_QUOTE)
                }
                STRONG_QUOTING -> {
                    parseStrongQuoting(i, input, STRONG_QUOTE)
                }

                else -> {
                    parseNonQuoting(i, input, PARSE_COMMAND)
                }
            }
            i = parsingResult.last().endPosition
            result.addAll(parsingResult.map { CommandToken(it.commandType, it.token) })
        }
        return result
    }

    private fun parseWeakQuoting(startPosition: Int, input: String, type: CommandType) =
        parseQuoting(startPosition, input, type) { i ->
            i < input.length && input[i] != WEAK_QUOTING
        }

    private fun parseStrongQuoting(startPosition: Int, input: String, type: CommandType) =
        parseQuoting(startPosition, input, type) { i ->
            i < input.length && input[i] != STRONG_QUOTING
        }

    private fun parseNonQuoting(startPosition: Int, input: String, type: CommandType) =
        parseQuoting(startPosition, input, type) { i ->
            i < input.length && input[i] != STRONG_QUOTING && input[i] != WEAK_QUOTING
        }

    private fun parseAfterQuoting(startPosition: Int, input: String, type: CommandType) =
        parseQuoting(startPosition, input, type) { i ->
            i < input.length && input[i] != STRONG_QUOTING && input[i] != WEAK_QUOTING && !input[i].isWhitespace()
        }

    private fun parseQuoting(
        startPosition: Int,
        input: String,
        type: CommandType,
        predicate: (Int) -> Boolean
    ): List<ParsingResult> {
        var i = startPosition
        var hasQuotes = false
        if (input[i] == STRONG_QUOTING || input[i] == WEAK_QUOTING) {
            i++
            hasQuotes = true
        }
        var token = ""
        while (predicate(i)) {
            token = "$token${input[i++]}"
        }
        if (hasQuotes && i == input.length &&
            input.last() != input[startPosition]
        ) throw QuoteParsingException(QUOTE_MESSAGE)

        if (hasQuotes) {
            i++
            if (i < input.length && !input[i].isWhitespace() && input[i] != STRONG_QUOTING && input[i] != WEAK_QUOTING)
                return mutableListOf(ParsingResult(i, token, type)) + parseAfterQuoting(i, input, STRING)
        }
        return listOf(ParsingResult(i, token, type))
    }

    companion object {
        private const val WEAK_QUOTING = '"'
        private const val STRONG_QUOTING = '\''
        private const val QUOTE_MESSAGE = "Invalid quoting."

        private data class ParsingResult(val endPosition: Int, val token: String, val commandType: CommandType)
    }
}