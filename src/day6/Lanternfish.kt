package day6

import java.io.File
import java.math.BigInteger

data class Lanternfish(val daysUntilSpawning: Int) {
    fun iterate(): List<Lanternfish> {
        return when (daysUntilSpawning) {
            0 -> listOf(Lanternfish(6), Lanternfish(8))
            else -> listOf(Lanternfish(daysUntilSpawning - 1))
        }
    }
}

data class Population(val individuals: Map<Lanternfish, BigInteger> = mapOf()) {
    fun iterate(): Population {
        return Population(individuals
            .flatMap { it.key.iterate().map { newIndividual -> newIndividual to it.value } }
            .groupingBy { it.first }.aggregate { _, accumulator, element, _ ->
                when (accumulator) {
                    null -> element.second
                    else -> accumulator + element.second
                }
            })
    }

    fun numOfIndividuals(): BigInteger {
        return individuals.values.sumOf { it }
    }
}

object World {
    fun simulate(initial: Population, days: Int): Population {
        return (1..days).fold(initial) { pop, _ -> pop.iterate() }
    }
}

object Parser {
    fun processInput(lines: List<String>): Population {
        val countsStr = lines.first()
        val counts = countsStr
            .split(",")
            .map { it.toInt() }
            .groupingBy { it }
            .eachCount()
            .mapKeys { Lanternfish(it.key) }
            .mapValues { it.value.toBigInteger() }
        return Population(counts)
    }
}

fun main() {
    val input = File("src/day6/input.txt")
        .readLines()
    val initial = Parser.processInput(input)
    val part1 = World.simulate(initial, 80)
    println("---Part 1---")
    println("Number of lanternfish: ${part1.numOfIndividuals()}")
    println("---Part 2---")
    val part2 = World.simulate(initial, 256)
    println("Number of lanternfish: ${part2.numOfIndividuals()}")
}