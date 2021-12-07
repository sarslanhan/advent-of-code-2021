package day7

import kotlin.test.Test
import kotlin.test.*
import java.io.File

internal class CrabsTest {
    val input = File("test/day7/sample.txt")
        .readLines()

    @Test
    fun processInput() {
        val crabs = Parser.processInput(input)
        assertEquals(10, crabs.size)
    }

    @Test
    fun part1() {
        val crabs = Parser.processInput(input)
        assertEquals(37, Crabs.part1(crabs))
    }

    @Test
    fun part2() {
        val crabs = Parser.processInput(input)
        assertEquals(168, Crabs.part2(crabs))
    }
}