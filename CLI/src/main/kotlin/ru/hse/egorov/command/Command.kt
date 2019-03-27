package ru.hse.egorov.command

/**
 * Interface for CLI Command.
 */
interface Command {

    /**
     * Executes CLI Command.
     * @param args are given parameters
     * @param input is previous command result.
     * @return command result.
     */
    fun execute(args: List<String>, input: String): String
}