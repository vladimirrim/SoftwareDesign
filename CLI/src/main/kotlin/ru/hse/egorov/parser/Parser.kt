package ru.hse.egorov.parser


/**
 * This interface represents input parser to CLI commands.
 */
interface Parser {

    /**
     * Parses input to commands.
     * @return parsed commands.
     */
    fun parse(input: String): List<CommandToken>
}