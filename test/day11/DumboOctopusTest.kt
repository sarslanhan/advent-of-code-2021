package day11

import kotlin.test.Test
import kotlin.test.*
import java.io.File

class DumboOctopusTest {
    val input = File("test/day11/sample.txt")
        .readLines()

    @Test
    fun part1() {
        val c = Cavern.parse(input)
        val flashes = Solution.part1(c)
        assertEquals(1656, flashes)
    }

    @Test
    fun part2() {
        val c = Cavern.parse(input)
        val iterations = Solution.part2(c)
        assertEquals(195, iterations)
    }
}