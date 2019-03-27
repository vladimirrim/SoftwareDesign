package ru.hse.egorov

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.hse.egorov.environment.Environment
import ru.hse.egorov.interpreter.InterpreterFactory
import ru.hse.egorov.parser.ParserFactory

internal class MainTest {
    private val env = Environment()
    private val sep = System.lineSeparator()
    private val interpreter = InterpreterFactory.getCliInterpreter(env)
    private val parser = ParserFactory.getCliParser(env)

    @Test
    fun testComplex() {
        executeLine("a=echo")
        executeLine("b=cat")
        executeLine("c=wc")
        executeLine("kek=jojo")

        assertEquals("1 1 4$sep", executeLine("\$a \$kek | \$b|\$c"))
    }

    @Test
    fun testInvalidPipeCommands() {
        assertEquals("Invalid pipe command.", executeLine("cat f.txt | | wc"))
    }

    @Test
    fun testPipelineWithoutSpaces() {
        assertEquals("1", executeLine("echo 1|cat|cat|cat"))
    }

    @Test
    fun testInvalidPipeLast() {
        assertEquals("Invalid pipe command.", executeLine("echo 123 |"))
    }

    @Test
    fun testInvalidPipeFirst() {
        assertEquals("Invalid pipe command.", executeLine("| wc"))
    }

    @Test
    fun testInvalidQuoteCommands() {
        assertEquals("Invalid quoting.", executeLine("echo \"text"))
    }

    @Test
    fun testEchoWeakQuotesStringQuotesAssign() {
        executeLine("x=1")

        assertEquals("'1231'", executeLine("echo \"'123\$x'\""))
    }

    @Test
    fun testAssignWithSpaces() {
        executeLine("t=\" some text with spaces\"")

        assertEquals("some text with spaces", executeLine("echo \$t"))
    }

    @Test
    fun testEchoWithSpaces() {
        assertEquals("    text    f", executeLine("echo \"    text    f\""))
    }

    @Test
    fun testArgsWithQuotes() {
        assertEquals("privet", executeLine("echo 'pri'vet"))
    }

    @Test
    fun testCommandWithQuotes() {
        assertEquals("text", executeLine("\"ec\"ho text"))
    }

    @Test
    fun testAssignWithQuote() {
        executeLine("x=text")
        assertEquals("'text", executeLine("echo \"'\$x\""))
    }

    private fun executeLine(input: String) = interpreter.interpret(parser.parse(input))
}