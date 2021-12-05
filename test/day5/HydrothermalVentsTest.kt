package day5

import kotlin.test.Test
import kotlin.test.*
import java.io.File

internal class HydrothermalVentsTest {
    val input = File("test/day5/sample.txt")
        .readLines()

    @Test
    fun parser() {
        val lines = Parser.parseLines(input)
        assertEquals(input.size, lines.size)
    }

    @Test
    fun part1() {
        val lines = Parser.parseLines(input)
        assertEquals(5, HydrothermalVents.part1(lines))
    }

    @Test
    fun part2() {
        val lines = Parser.parseLines(input)
        assertEquals(12, HydrothermalVents.part2(lines))
    }
}