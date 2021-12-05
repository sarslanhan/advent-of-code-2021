package day4

import kotlin.test.Test
import kotlin.test.*
import java.io.File

internal class BingoTest {
    private val lines = File("test/day4/sample.txt")
        .readLines()

    @Test
    fun processInput() {
        val state = parseInput(lines)
        val score = BingoSimulator.scoreOfFirstWinner(state)
        assertEquals(4512, score)
        val score2 = BingoSimulator.scoreOfLastWinner(state)
        assertEquals(1924, score2)
    }
}