package ru.hse.egorov.command

import java.io.File

/**
 * This class prints files and directories in current directory.
 */
class PwdCommand : Command {

    override fun execute(args:List<String>, input: String): String =
        File(".").list().joinToString(" ", "", "\n") { it }
}