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
    fun testInvalidQuoteCommands() {
        assertEquals("Invalid quoting.", executeLine("echo \"text"))
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
    fun testGrepInvalidAArgsNoArgs() {
        assertEquals("Unable to parse arguments: missing PATTERN operand", executeLine("echo 20 | grep -A 20"))
    }

    @Test
    fun testGrepInvalidAArgsNegative() {
        assertEquals(
            "Unable to parse arguments: Negative value for after context",
            executeLine("echo 20 | grep -A -10 20")
        )
    }

    @Test
    fun testGrepCat() {
        assertEquals(
            "    compile \"org.jetbrains.kotlin:kotlin-stdlib-jdk8\"$sep" +
                    "    compile group: 'com.xenomachina', name: 'kotlin-argparser', version: '2.0.7'$sep" +
                    "    from { configurations.compile.collect { it.isDirectory() ? it : zipTree(it) } }$sep" +
                    "compileKotlin {$sep" +
                    "compileTestKotlin {$sep",
            executeLine("cat build.gradle | grep compile")
        )
    }

    @Test
    fun testGrepCatWordMatching() {
        assertEquals(
            "    compile \"org.jetbrains.kotlin:kotlin-stdlib-jdk8\"$sep" +
                    "    compile group: 'com.xenomachina', name: 'kotlin-argparser', version: '2.0.7'$sep" +
                    "    from { configurations.compile.collect { it.isDirectory() ? it : zipTree(it) } }$sep",
            executeLine("cat build.gradle | grep -w compile")
        )
    }

    @Test
    fun testGrepWordMatching() {
        assertEquals(
            "    compile \"org.jetbrains.kotlin:kotlin-stdlib-jdk8\"$sep" +
                    "    compile group: 'com.xenomachina', name: 'kotlin-argparser', version: '2.0.7'$sep" +
                    "    from { configurations.compile.collect { it.isDirectory() ? it : zipTree(it) } }$sep",
            executeLine("grep -w compile build.gradle")
        )
    }

    @Test
    fun testGrepInvalidAArgsString() {
        assertEquals("Unable to parse arguments: For input string: \"kek\"", executeLine("echo 20 | grep -A kek 20"))
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