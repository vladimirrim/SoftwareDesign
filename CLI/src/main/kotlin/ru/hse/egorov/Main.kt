package ru.hse.egorov

import ru.hse.egorov.environment.Environment
import ru.hse.egorov.interpreter.InterpreterFactory
import ru.hse.egorov.parser.ParserFactory

/**
 * Starts the app.
 */
fun main() {
    val env = Environment()
    val interpreter = InterpreterFactory.getCliInterpreter(env)
    val parser = ParserFactory.getCliParser(env)

    while (true) {
        val input = readLine()
        input?.let {
            println(interpreter.interpret(parser.parse(it)))
        }
    }
}