package day6

import kotlin.test.Test
import kotlin.test.*
import java.io.File

internal class LanternfishTest {
    val input = File("test/day6/sample.txt")
        .readLines()

    @Test
    fun processInput() {
        val pop = Parser.processInput(input)
        assertEquals(5.toBigInteger(), pop.individuals.values.sumOf { it })
    }

    @Test
    fun part1() {
        val pop = Parser.processInput(input)
        assertEquals(5934.toBigInteger(), World.simulate(pop, 80).numOfIndividuals())
    }

    @Test
    fun part2() {
        val pop = Parser.processInput(input)
        assertEquals(26984457539.toBigInteger(), World.simulate(pop, 256).numOfIndividuals())
    }
}