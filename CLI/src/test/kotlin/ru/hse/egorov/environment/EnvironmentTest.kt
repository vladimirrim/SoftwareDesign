package ru.hse.egorov.environment

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class EnvironmentTest {

    @Test
    fun testNoSuchKey() {
        val env = Environment()
        assertEquals("", env.getVariable("a"))
    }

    @Test
    fun testAssignKey() {
        val env = Environment()
        env.setVariable("a", "b")
        assertEquals("b", env.getVariable("a"))
    }

    @Test
    fun testReassignKey() {
        val env = Environment()
        env.setVariable("a", "b")
        env.setVariable("a", "c")
        assertEquals("c", env.getVariable("a"))
    }
}