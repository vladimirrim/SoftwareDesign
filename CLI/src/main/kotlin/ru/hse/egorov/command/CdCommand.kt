package ru.hse.egorov.command

import ru.hse.egorov.environment.Environment

/**
 * This class represents the CLI command that changes the working directory to the given path.
 */
class CdCommand(env: Environment): Command {
    private val environment = env

    override fun execute(args: List<String>, input: String): String {
        return if (args.size > 1) {
            "cd: Too many arguments"
        } else if (args.isEmpty()) {
            environment.setWorkDir(environment.getHomeDir())
            ""
        } else {
            val file = environment.pathToFile(args[0])
            if (!file.exists()) {
                "cd: " + args[0] + ": No such file or directory"
            } else if (!file.isDirectory) {
                "cd: " + args[0] + ": Not a directory"
            } else {
                environment.setWorkDir(file.absolutePath)
                ""
            }
        }
    }
}