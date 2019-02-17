package ru.hse.egorov.command

import ru.hse.egorov.environment.Environment
import java.io.File

/**
 * This class represents the CLI command that prints list of files in the current directory.
 */
class LsCommand(env: Environment) : Command {
    private val environment = env

    override fun execute(args: List<String>, input: String): String {
        if (args.size > 1) {
            return "ls: Too many arguments"
        }
        val file : File = if (args.isEmpty()) { File(environment.getWorkDir()) } else { environment.pathToFile(args[0]) }
        return if (!file.exists()) {
            "ls: cannot access '" + args[0] + "': No such file or directory"
        } else if (file.isDirectory) {
            file.list().joinToString(" ", "", "\n") { it }
        } else {
            args[0] + "\n"
        }
    }
}