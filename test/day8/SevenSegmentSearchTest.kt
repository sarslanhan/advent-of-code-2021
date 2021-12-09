package day8

import kotlin.test.Test
import kotlin.test.*
import java.io.File

internal class SevenSegmentSearchTest {
    val input = File("test/day8/sample.txt")
        .readLines()

    @Test
    fun processInput() {
        val entries = Parser.processInput(input)
        assertEquals(10, entries.size)
    }

    @Test
    fun part1() {
        val entries = Parser.processInput(input)
        assertEquals(26, SevenSegmentSearch.part1(entries))
    }

    @Test
    fun part2() {
        val entries = Parser.processInput(input)
        assertEquals(-1, SevenSegmentSearch.part2(entries))
    }
}