package ru.hse.egorov.command

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import ru.hse.egorov.interpreter.CommandInterpretingException
import java.io.File
import java.io.IOException

/**
 * This class implements grep commands with following keys -- -i for case ignoring, -A n for showing n string after matching string,
 * -w for word matching.
 */
class GrepCommand : Command {

    override fun execute(args: List<String>, input: String): String {
        lateinit var grepArgs: GrepArgs
        try {
            grepArgs = ArgParser(args.toTypedArray()).parseInto(::GrepArgs)
        } catch (e: Exception) {
            throw CommandInterpretingException(ARGS_PARSE_FAIL_MESSAGE_PREFIX + e.localizedMessage)
        }
        return grepArgs.run {
            if (afterContext < 0)
                throw CommandInterpretingException(ARGS_PARSE_FAIL_MESSAGE_PREFIX + "Negative value for after context")
            val completePattern = if (isWordMatching) {
                "\\b$pattern\\b"
            } else {
                pattern
            }
            val regex = if (isIgnoreCase) {
                Regex(completePattern, RegexOption.IGNORE_CASE)
            } else {
                Regex(completePattern)
            }

            buildString {
                if (sources.isEmpty()) {
                    append(processString(input, regex, afterContext))
                } else {
                    for (source in sources) {
                        append(processFile(source, regex, afterContext))
                    }
                }
            }
        }
    }

    private fun processString(string: String, regex: Regex, afterContext: Int): String {

        fun matches(line: String): Boolean {
            return regex.containsMatchIn(line)
        }

        return buildString {
            var contextLinesCount = 0
            string.lines().forEach {
                if (matches(it)) {
                    append(it).append(System.lineSeparator())
                    contextLinesCount = afterContext
                } else if (contextLinesCount-- > 0) {
                    append(it).append(System.lineSeparator())
                }
            }
        }
    }

    private fun processFile(filename: String, regex: Regex, afterContext: Int): String {
        return try {
            processString(File(filename).readText(), regex, afterContext)
        } catch (_: IOException) {
            throw CommandInterpretingException(FILE_READ_FAIL_MESSAGE_PREFIX + "$filename${System.lineSeparator()}")
        }
    }

    companion object {
        private const val FILE_READ_FAIL_MESSAGE_PREFIX = "Unable to read file: "
        private const val ARGS_PARSE_FAIL_MESSAGE_PREFIX = "Unable to parse arguments: "

        private class GrepArgs(parser: ArgParser) {

            val isWordMatching by parser.flagging(
                "-w", help = ""
            )

            val isIgnoreCase by parser.flagging(
                "-i", help = ""
            )

            val afterContext by parser.storing(
                "-A", help = ""
            ) { toInt() }.default(0)

            val pattern by parser.positional(
                "PATTERN", help = ""
            )

            val sources by parser.positionalList(
                "SOURCES",
                help = "", sizeRange = 0..Int.MAX_VALUE
            )
        }
    }
}