package ru.hse.egorov.command

import ru.hse.egorov.interpreter.CommandInterpretingException


/**
 * This class tries to executed unknown command using external resources.
 */
class UnknownCommand : Command {

    override fun execute(args: List<String>, input: String): String {
        return try {
            val process = Runtime.getRuntime().exec(args.toTypedArray())
            process.outputStream.write(input.toByteArray())
            if (process.waitFor() != 0)
                throw CommandInterpretingException(String(process.errorStream.readBytes()))
            String(process.inputStream.readBytes())
        } catch (_: Exception) {
            throw CommandInterpretingException("$EXCEPTION_MESSAGE${args[0]}")
        }
    }

    companion object {
        private const val EXCEPTION_MESSAGE = "Unable to execute command: "
    }
}