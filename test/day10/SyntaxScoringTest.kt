package day10

import kotlin.test.Test
import kotlin.test.*
import java.io.File

class SyntaxScoringTest {
    val input = File("test/day10/sample.txt")
        .readLines()

    @Test
    fun part1() {
        val score = input.sumOf { SyntaxScoring.calculateScore(it) }
        assertEquals(26397, score)
    }

    @Test
    fun part2() {
        val score = SyntaxScoring.middleScore(input)
        assertEquals(288957.toBigInteger(), score)
    }
}