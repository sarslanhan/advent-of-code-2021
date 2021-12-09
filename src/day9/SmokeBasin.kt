package day9

import java.io.File
import java.math.BigInteger

data class Point(val x: Int, val y: Int)

data class SmokeBasin(val heightMap: List<List<Int>>) {
    val lowPoints = heightMap.indices.flatMap { x ->
        heightMap[x].indices.flatMap { y ->
            val height = heightMap[x][y]
            val neighbours = listOf(
                if (x == 0) 10 else heightMap[x - 1][y],
                if (x == heightMap.size - 1) 10 else heightMap[x + 1][y],
                if (y == 0) 10 else heightMap[x][y - 1],
                if (y == heightMap[x].size - 1) 10 else heightMap[x][y + 1],
            )
            if (neighbours.all { it > height }) {
                listOf(Point(x, y))
            } else {
                listOf()
            }
        }
    }

    companion object Parser {
        fun parseHeightMap(lines: List<String>): SmokeBasin {
            return SmokeBasin(lines.map { str ->
                str.map { it.minus('0') }
            })
        }
    }

    fun sumOfRiskInLowPoints(): Int {
        val risks = lowPoints.map { heightMap[it.x][it.y] + 1 }

        return risks.sum()
    }

    tailrec fun findBasin(p: Point, alreadyChecked: Set<Point> = setOf(p), acc: Set<Point> = setOf(p)): Set<Point> {
        val neighbours = listOfNotNull(
            if (p.x == 0) null else Point(p.x - 1, p.y),
            if (p.x == heightMap.size - 1) null else Point(p.x + 1, p.y),
            if (p.y == 0) null else Point(p.x, p.y - 1),
            if (p.y == heightMap[p.x].size - 1) null else Point(p.x, p.y + 1),
        ).filterNot { alreadyChecked.contains(it) }

        val (inside, outside) = neighbours.partition { heightMap[it.x][it.y] != 9 }
        if (inside.isEmpty()) {
            return acc
        }
        return inside.fold(acc + inside) {intermediateResult, point ->
            findBasin(point, alreadyChecked.union(outside).union(intermediateResult), intermediateResult)
        }
    }

    fun multiplicationOfSizeOf3LargestBasins(): Int {
        val basins = lowPoints.map {
            findBasin(it)
        }
        return basins.map { it.size }
            .sorted()
            .reversed()
            .take(3)
            .fold(1) {
                acc, i -> acc * i
            }
    }
}

fun main() {
    val input = File("src/day9/input.txt")
        .readLines()
    val basin = SmokeBasin.parseHeightMap(input)
    val part1 = basin.sumOfRiskInLowPoints()
    println("---Part 1---")
    println("The sum of the risk levels of all the low points: $part1")
    val part2 = basin.multiplicationOfSizeOf3LargestBasins()
    println("---Part 2---")
    println("The multiplication of the sizes of the three largest basins: $part2")

}