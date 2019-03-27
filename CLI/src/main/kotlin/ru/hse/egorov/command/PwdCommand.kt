package ru.hse.egorov.command

import java.nio.file.Paths

/**
 * This class prints current directory path.
 */
class PwdCommand : Command {

    override fun execute(args: List<String>, input: String): String =
        Paths.get("").toAbsolutePath().toString() + "\n"
}