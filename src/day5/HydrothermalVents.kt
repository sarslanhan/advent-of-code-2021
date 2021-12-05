package day5

import java.io.File
import kotlin.math.abs

data class Point(val x: Int, val y: Int)

data class Line(val from: Point, val to: Point) {
    val isVertical = from.x == to.x
    val isHorizontal = from.y == to.y
    val isDiagonal = abs(from.x - to.x) == abs(from.y - to.y)

    fun coverage(): List<Point> {
        val dx = from.x - to.x
        val dy = from.y - to.y

        val rx = if (dx > 0) (from.x downTo to.x) else (from.x..to.x)
        val ry = if (dy > 0) (from.y downTo to.y) else (from.y..to.y)

        return if (isDiagonal)
            rx.zip(ry).map { Point(it.first, it.second) }
        else rx.flatMap { x ->
            ry.map { y ->
                Point(x, y)
            }
        }
    }
}

object Parser {
    fun parseLines(lines: List<String>): List<Line> {
        return lines.map { line ->
            val points = line.split(" -> ")
                .map {
                    val parts = it.split(",")
                        .map { num -> num.toInt() }
                    Point(parts[0], parts[1])
                }
            Line(points[0], points[1])
        }
    }
}

object HydrothermalVents {
    fun createMap(lines: List<Line>): Map<Point, Int> {
        val pointsCovered = lines.flatMap { it.coverage() }
        return pointsCovered
            .groupingBy { it }
            .eachCount()
    }

    fun part1(lines: List<Line>): Int {
        val verticalOrHorizontalLines = lines.filter { it.isHorizontal or it.isVertical }
        val map = createMap(verticalOrHorizontalLines)
        return map.filter { it.value >= 2 }
            .size
    }

    fun part2(lines: List<Line>): Int {
        val verticalOrHorizontalOrDiagonalLines = lines.filter { it.isHorizontal or it.isVertical or it.isDiagonal }
        val map = createMap(verticalOrHorizontalOrDiagonalLines)
        return map.filter { it.value >= 2 }
            .size
    }
}

fun main() {
    val input = File("src/day5/input.txt")
        .readLines()
    val lines = Parser.parseLines(input)
    println("---Part 1---")
    println("The number of points where at least two lines overlap: ${HydrothermalVents.part1(lines)}")
    println("---Part 2---")
    println("The number of points where at least two lines overlap: ${HydrothermalVents.part2(lines)}")
}