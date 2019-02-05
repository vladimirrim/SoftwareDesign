package ru.hse.egorov.interpreter

import ru.hse.egorov.parser.CommandToken

/**
 * This interface represents commands executor.
 */
interface Interpreter {

    /**
     * Executes given commands
     * @param commands are given commands
     * @return interpreted result
     */
    fun interpret(commands: List<CommandToken>): String
}