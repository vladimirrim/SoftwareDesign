package ru.hse.egorov.command


/**
 * This class tries to executed unknown command using external resources.
 */
class UnknownCommand : Command {

    override fun execute(args: List<String>, input: String): String {
        return try {
            val process = Runtime.getRuntime().exec(args.toTypedArray())
            process.outputStream.write(input.toByteArray())
            process.waitFor()
            String(process.inputStream.readBytes())
        } catch (_: Exception) {
            "$EXCEPTION_MESSAGE${args[0]}"
        }
    }

    companion object {
        private const val EXCEPTION_MESSAGE = "Unable to execute command: "
    }
}