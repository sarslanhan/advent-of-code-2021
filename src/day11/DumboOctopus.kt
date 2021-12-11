package day11

import java.io.File

data class DumboOctopus(val energy: Int, val x: Int, val y: Int, val isFlashing: Boolean = false) {
    val willFlash = energy > 9

    fun next(incr: Int = 1): DumboOctopus {
        val n = energy + incr
        return this.copy(energy = n)
    }

    fun step(): DumboOctopus {
        return this.copy(energy = if (willFlash) 0 else energy, isFlashing = willFlash)
    }
}

data class Cavern(val octopuses: List<List<DumboOctopus>>, val flashesSoFar: Int = 0, val iterations: Int = 0) {
    companion object Parser {
        fun parse(lines: List<String>): Cavern {
            val octopuses = lines.mapIndexed { x, line ->
                line.mapIndexed { y, c ->
                    DumboOctopus(c.minus('0'), x, y)
                }
            }
            return Cavern(octopuses)
        }
    }

    tailrec private fun iterate(
        octos: List<List<DumboOctopus>>,
        alreadyFlashing: Set<Pair<Int, Int>> = setOf()
    ): List<List<DumboOctopus>> {
        val updated = octos.map { row ->
            row.map {
                it.next(if (alreadyFlashing.isNotEmpty()) 0 else 1)
            }
        }
        val willFlash = updated.flatMap { row ->
            row
                .filter { !alreadyFlashing.contains(it.x to it.y) && it.willFlash }
        }
        val ns = willFlash.flatMap { neighbours(it.x, it.y) }
            .groupingBy { it }
            .eachCount()
            .filterKeys { !alreadyFlashing.contains(it) }
            .filterValues { it > 0 }

        if (ns.isEmpty()) {
            return updated.map { row -> row.map { it.step() } }
        }
        val withSideEffects = updated.map { row ->
            row.map {
                it.next(ns.getOrDefault(it.x to it.y, 0))
            }
        }
        return iterate(
            withSideEffects,
            alreadyFlashing + willFlash.map { it.x to it.y }.toSet()
        )
    }

    fun step(): Cavern {
        val updated = iterate(octopuses)
        return Cavern(updated, flashesSoFar + updated.sumOf { row -> row.count { it.isFlashing } }, iterations + 1)
    }

    tailrec fun iterateUntilAllFlashes(cavern: Cavern = this): Cavern {
        if (cavern.octopuses.all { row -> row.all { it.isFlashing } }) {
            return cavern
        }
        return iterateUntilAllFlashes(cavern.step())
    }

    private fun neighbours(
        x: Int,
        y: Int,
    ) = listOf(
        x - 1 to y,
        x + 1 to y,
        x to y - 1,
        x to y + 1,
        x - 1 to y - 1,
        x - 1 to y + 1,
        x + 1 to y - 1,
        x + 1 to y + 1
    )
        .filter { (x, y) ->
            x >= 0 && x < octopuses.size
                    && y >= 0 && y < octopuses[x].size
        }
}

object Solution {
    fun part1(c: Cavern): Int {
        return (0 until 100).fold(c) { acc, it ->
//            println("Iteration $it")
//            println(acc.octopuses.joinToString("\n") { it.map { oc -> oc.energy }.joinToString("") })
//            println()
            acc.step()
        }
            .flashesSoFar
    }

    fun part2(c: Cavern): Int {
        val result = c.iterateUntilAllFlashes()
        return result.iterations
    }
}

fun main() {
    val input = File("src/day11/input.txt")
        .readLines()
    val cavern = Cavern.parse(input)

    val part1 = Solution.part1(cavern)
    println("---Part 1---")
    println("The total flashes are there after 100 steps: $part1")
    val part2 = Solution.part2(cavern)
    println("---Part 2---")
    println("The first step during which all octopuses flash: $part2")
}