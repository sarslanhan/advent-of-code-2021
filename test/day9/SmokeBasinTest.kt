package day9

import kotlin.test.Test
import kotlin.test.*
import java.io.File

class SmokeBasinTest {
    val input = File("test/day9/sample.txt")
        .readLines()

    @Test
    fun parser() {
        val basin = SmokeBasin.parseHeightMap(input)
        assertEquals(5, basin.heightMap.size)
        assert(basin.heightMap.all { it.size == 10 })
    }

    @Test
    fun part1() {
        val basin = SmokeBasin.parseHeightMap(input)
        assertEquals(15, basin.sumOfRiskInLowPoints())
    }

    @Test
    fun part2() {
        val basin = SmokeBasin.parseHeightMap(input)
        assertEquals(1134, basin.multiplicationOfSizeOf3LargestBasins())
    }
}