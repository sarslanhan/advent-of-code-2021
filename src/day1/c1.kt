package day1

import java.io.File

fun main() {

    val depths = File("src/day1/input.txt")
        .readLines()
        .map(String::toInt)

    val increases = part1(depths)
    println("Part1: $increases")

    val increases2 = part2(depths)
    println("Part2: $increases2")
}

public fun part2(depths: List<Int>): Int {
    return depths
        .windowed(3)
        .map { it.sum() }
        .windowed(2)
        .count { it[0] < it[1] }
}

public fun part1(depths: List<Int>): Int {
    return depths
        .drop(1)
        .zip(depths.dropLast(1))
        .count { it.first > it.second }
}