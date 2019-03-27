package ru.hse.egorov.parser

import ru.hse.egorov.parser.CommandToken.Companion.CommandType

/**
 * This class represents parse CLI command.
 */
data class ParsedToken(val type: CommandType, var args: List<String>)
