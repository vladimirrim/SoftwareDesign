package ru.hse.egorov.command

import java.io.File
import java.io.IOException

/**
 * This class represents CLI cat command, which prints given files or previous command output.
 */
class CatCommand : Command {

    override fun execute(args: List<String>, input: String): String {
        return if (args.isEmpty()) {
            input
        } else {
            args.joinToString("", "", "\n") { readFile(it) }
        }
    }

    private fun readFile(filename: String): String {
        return try {
            File(filename).readText()
        } catch (_: IOException) {
            FILE_READ_FAIL_MESSAGE_PREFIX + filename
        }
    }

    companion object {
        private const val FILE_READ_FAIL_MESSAGE_PREFIX = "Unable to read file: "
    }
}