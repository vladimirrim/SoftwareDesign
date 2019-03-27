package ru.hse.egorov.parser

/**
 * Thrown when there is unpaired quote in input.
 */
class QuoteParsingException(message: String) : Exception(message)