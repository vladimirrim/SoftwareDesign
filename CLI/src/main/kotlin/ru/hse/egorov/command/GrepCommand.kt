package ru.hse.egorov.command

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import java.io.File
import java.io.IOException

/**
 * This class implements grep commands with following keys -- -i for case ignoring, -A n for showing n string after matching string,
 * -w for word matching.
 */
class GrepCommand : Command {

    override fun execute(args: List<String>, input: String): String {
        return ArgParser(args.toTypedArray()).parseInto(::GrepArgs).run {
            val regex = if (isIgnoreCase) {
                Regex(pattern, RegexOption.IGNORE_CASE)
            } else {
                Regex(pattern)
            }

            buildString {
                if (sources.isEmpty()) {
                    append(processString(input, regex, isWordMatching, afterContext))
                } else {
                    for (source in sources) {
                        append(processFile(source, regex, isWordMatching, afterContext))
                    }
                }
            }
        }
    }

    private fun processString(string: String, regex: Regex, isWordMatching: Boolean, afterContext: Int): String {

        fun matches(line: String): Boolean {
            return if (isWordMatching) {
                line.split("\\s+".toRegex()).any { regex.matches(it) }
            } else {
                regex.containsMatchIn(line)
            }
        }

        return buildString {
            var contextLinesCount = 0
            string.lines().forEach {
                if (matches(it)) {
                    append(it).append("\n")
                    contextLinesCount = afterContext
                } else if (contextLinesCount-- > 0) {
                    append(it).append("\n")
                }
            }
        }
    }

    private fun processFile(filename: String, regex: Regex, isWordMatching: Boolean, afterContext: Int): String {
        return try {
            processString(File(filename).readText(), regex, isWordMatching, afterContext)
        } catch (_: IOException) {
            FILE_READ_FAIL_MESSAGE_PREFIX + "$filename\n"
        }
    }

    companion object {
        private const val FILE_READ_FAIL_MESSAGE_PREFIX = "Unable to read file: "

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