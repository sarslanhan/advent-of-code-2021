package day1

import kotlin.test.Test
import kotlin.test.*
import java.io.File

internal class C1KtTest {
    private val depths = File("test/day1/sample.txt")
        .readLines()
        .map(String::toInt)

    @Test
    fun part2() {
        assertEquals(5, day1.part2(depths))
    }

    @Test
    fun part1() {
        assertEquals(7, day1.part1(depths))
    }
}