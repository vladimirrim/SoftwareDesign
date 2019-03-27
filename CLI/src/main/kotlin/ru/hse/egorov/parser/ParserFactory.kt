package ru.hse.egorov.parser

import ru.hse.egorov.environment.Environment

/**
 * This class represents different CLI parsers factory.
 */
class ParserFactory {

    companion object {

        fun getCliParser(env: Environment) = CliParser(env)

        fun getQuoteParser() = QuoteParser()

        fun getCommandParser() = CommandParser()
    }
}