package ru.hse.egorov.command

import ru.hse.egorov.environment.Environment
import java.io.File


/**
 * This class tries to executed unknown command using external resources.
 */
class UnknownCommand(env: Environment) : Command {
    private val environment = env

    override fun execute(args: List<String>, input: String): String {
        return try {
            val process = Runtime.getRuntime().exec(
                args.toTypedArray(),
                environment.toStringSet().toTypedArray(),
                File(environment.getWorkDir())
            )
            process.outputStream.write(input.toByteArray())
            process.waitFor()
            process.inputStream.readBytes().toString()
        } catch (_: Exception) {
            "$EXCEPTION_MESSAGE${args[0]}"
        }
    }

    companion object {
        private const val EXCEPTION_MESSAGE = "Unable to execute command: "
    }
}