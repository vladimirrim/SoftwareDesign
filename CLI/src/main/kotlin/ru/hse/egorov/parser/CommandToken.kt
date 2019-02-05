package ru.hse.egorov.parser

/**
 * This class represents CLI command with arguments.
 */
data class CommandToken(val type: CommandType, var args: String) {

    companion object {
        enum class CommandType {
            STRING, CAT, ECHO, PWD, WC, EXIT, WEAK_QUOTE, STRONG_QUOTE, PARSE_COMMAND, UNKNOWN_COMMAND, ASSIGN_COMMAND;

            companion object {
                fun getCommandByName(name: String): CommandType {
                    return values().firstOrNull {
                        it.name.toLowerCase() == name.toLowerCase()
                    } ?: UNKNOWN_COMMAND
                }
            }
        }
    }
}