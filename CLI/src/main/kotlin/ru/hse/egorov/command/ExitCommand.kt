package ru.hse.egorov.command

import kotlin.system.exitProcess

/**
 * This class exits CLI.
 */
class ExitCommand:Command {

    override fun execute(args:List<String>, input: String): String {
        exitProcess(0)
    }
}