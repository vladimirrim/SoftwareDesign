package ru.hse.egorov.command

import ru.hse.egorov.interpreter.CommandInterpretingException
import java.io.File
import java.io.IOException
import java.nio.charset.Charset


/**
 * This class prints number of lines, words and bytes in given files or previous command result.
 */
class WcCommand : Command {

    override fun execute(args: List<String>, input: String): String {
        return if (args.isEmpty()) {
            calcStatistics(input.toByteArray(Charset.defaultCharset()))
        } else {
            args.joinToString(System.lineSeparator()) { calcFileStatistics(it) }
        } + System.lineSeparator()
    }

    private fun calcFileStatistics(filename: String): String {
        return try {
            calcStatistics(File(filename).readBytes())
        } catch (_: IOException) {
            throw CommandInterpretingException(FILE_READ_FAIL_MESSAGE_PREFIX + filename)
        }
    }

    private fun calcStatistics(bytes: ByteArray): String {
        val string = bytes.toString(Charset.defaultCharset())
        return "${string.lines().size} ${string.split("\\s+".toRegex()).filter { it.isNotEmpty() }.size} ${bytes.size}"
    }

    companion object {
        private const val FILE_READ_FAIL_MESSAGE_PREFIX = "Unable to read file: "
    }
}