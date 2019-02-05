package ru.hse.egorov.interpreter

import ru.hse.egorov.environment.Environment

/**
 * This class represents CLI interpreter factory.
 */
class InterpreterFactory {

    companion object {
        fun getCliInterpreter(env: Environment): Interpreter = CommandInterpreter(env)
    }
}