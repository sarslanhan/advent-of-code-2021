package day7

import java.io.File
import kotlin.math.abs

data class Crab(val x: Int)

object Parser {
    fun processInput(lines: List<String>): List<Crab> {
        val countsStr = lines.first()
        return countsStr
            .split(",")
            .map { it.toInt() }
            .map { Crab(it) }
    }
}

object Crabs {
    fun part1(crabs: List<Crab>): Int {
        val sorted = crabs.sortedBy { it.x }
        val p50 = when (crabs.size % 2) {
            0 -> (sorted[crabs.size / 2].x + sorted[crabs.size / 2 - 1].x) / 2
            else -> sorted[crabs.size / 2].x
        }
        return crabs.sumOf { abs(p50 - it.x) }
    }

    fun part2(crabs: List<Crab>): Int {
        val maxX = crabs.maxOf { it.x }
        val fuelPerPosition = (1..maxX)
            .map { pos ->
                crabs.map { self ->
                    val dx = abs(self.x - pos)
                    dx * (dx + 1) / 2
                }
            }
        return fuelPerPosition
            .minOfOrNull { it.sum() } ?: 0
    }
}

fun main() {
    val input = File("src/day7/input.txt")
        .readLines()
    val crabs = Parser.processInput(input)
    val part1 = Crabs.part1(crabs)
    println("---Part 1---")
    println("Required fuel for the position that needs the least amount of fuel with constant fuel consumption: $part1")
    val part2 = Crabs.part2(crabs)
    println("---Part 2---")
    println("Required fuel for the position that needs the least amount of fuel with variable fuel consumption: $part2")
}