package ru.hse.egorov.command

import ru.hse.egorov.environment.Environment
import java.io.File

/**
 * This class prints files and directories in current directory.
 */
class PwdCommand(env: Environment) : Command {
    private val environment = env

    override fun execute(args:List<String>, input: String): String =
        File(environment.getWorkDir()).list().joinToString(" ", "", "\n") { it }
}