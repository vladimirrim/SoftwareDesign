package ru.hse.egorov

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.hse.egorov.environment.Environment
import ru.hse.egorov.interpreter.InterpreterFactory
import ru.hse.egorov.parser.ParserFactory

internal class MainTest{
    private val env = Environment()
    private val interpreter = InterpreterFactory.getCliInterpreter(env)
    private val parser = ParserFactory.getCliParser(env)

    @Test
    fun testComplex(){
        executeLine("a=echo")
        executeLine("b=cat")
        executeLine("c=wc")
        executeLine("kek=jojo")

        assertEquals("1 1 4\n", executeLine("\$a \$kek | \$b|\$c"))
    }

    private fun executeLine(input:String) = interpreter.interpret(parser.parse(input))
}