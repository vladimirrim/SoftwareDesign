package ru.hse.egorov.parser

/**
 * This class parses string with quotes to CLI commands.
 */
class QuoteParser : Parser {

    override fun parse(input: String): List<CommandToken> {
        val result = ArrayList<CommandToken>()
        var i = 0
        while (i < input.length) {
            lateinit var parsingResult: ParsingResult
            lateinit var commandType: CommandToken.Companion.CommandType
            when (input[i]) {
                WEAK_QUOTING -> {
                    parsingResult = parseWeakQuoting(i, input)
                    commandType = CommandToken.Companion.CommandType.WEAK_QUOTE
                }
                STRONG_QUOTING -> {
                    parsingResult = parseStrongQuoting(i, input)
                    commandType = CommandToken.Companion.CommandType.STRONG_QUOTE
                }

                else -> {
                    parsingResult = parseNonQuoting(i, input)
                    commandType = CommandToken.Companion.CommandType.PARSE_COMMAND
                }
            }
            i = parsingResult.endPosition
            result.add(CommandToken(commandType, parsingResult.token))
        }
        return result
    }

    private fun parseWeakQuoting(startPosition: Int, input: String) = parseQuoting(startPosition, input) { i ->
        i < input.length && input[i] != WEAK_QUOTING
    }

    private fun parseStrongQuoting(startPosition: Int, input: String) = parseQuoting(startPosition, input) { i ->
        i < input.length && input[i] != STRONG_QUOTING
    }

    private fun parseNonQuoting(startPosition: Int, input: String) = parseQuoting(startPosition, input) { i ->
        i < input.length && input[i] != STRONG_QUOTING && input[i] != WEAK_QUOTING
    }

    private fun parseQuoting(startPosition: Int, input: String, predicate: (Int) -> Boolean): ParsingResult {
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

        if (hasQuotes) i++

        return ParsingResult(i, token)
    }

    companion object {
        private const val WEAK_QUOTING = '\''
        private const val STRONG_QUOTING = '"'
        private const val QUOTE_MESSAGE = "Invalid quoting."

        private data class ParsingResult(val endPosition: Int, val token: String)
    }
}