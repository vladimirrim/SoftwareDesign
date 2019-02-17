package ru.hse.egorov.environment

import java.io.File
import java.nio.file.Paths

/**
 * This class represents available variables in CLI.
 */
class Environment {
    private val variables = HashMap<String, String>()

    fun getVariable(name: String) = variables[name] ?: ""

    fun setVariable(key: String, value: String) = variables.set(key, value)

    private fun isWindows() = System.getProperty("os.name").startsWith("Windows")

    private fun getWorkDirVarName() : String = if (isWindows()) { "CD" } else { "PWD" }

    private fun getHomeDirVarName() : String = if (isWindows()) { "USERPROFILE" } else { "HOME" }

    init {
        for ((k, v) in System.getenv()) {
            setVariable(k, v)
        }
        setWorkDir(System.getProperty("user.dir"))
    }

    fun getWorkDir() = getVariable(getWorkDirVarName())

    fun setWorkDir(path: String) = setVariable(getWorkDirVarName(), path)

    fun getHomeDir() = getVariable(getHomeDirVarName())

    fun toStringSet() = variables.entries.map { it.key + "=" + it.value }

    fun pathToFile(path: String) : File {
        val file = File(path)
        return if (file.isAbsolute) {
            file
        } else {
            Paths.get(getWorkDir(), path).toFile()
        }
    }
}