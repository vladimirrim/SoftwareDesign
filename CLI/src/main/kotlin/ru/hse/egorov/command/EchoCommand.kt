package ru.hse.egorov.command

/**
 * This class print given arguments or previous command result.
 */
class EchoCommand : Command {

    override fun execute(args: List<String>, input: String): String {
        return if (args.isEmpty()) {
            input
        } else {
            args.joinToString(" ")
        }
    }
}