package ru.hse.egorov.environment

/**
 * This class represents available variables in CLI.
 */
class Environment {
    private val variables = HashMap<String, String>()

    fun getVariable(name: String) = variables[name] ?: ""

    fun setVariable(key: String, value: String) = variables.set(key, value)
}