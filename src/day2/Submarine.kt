package day2

import java.io.File
import kotlin.math.max

data class Submarine(val horizontalPos: Int = 0, val depth: Int = 0) {
    fun process(cmd: String, amount: Int): Submarine {
        return when (cmd) {
            "forward" -> this.copy(horizontalPos = horizontalPos + amount)
            "down" -> this.copy(depth = depth + amount)
            "up" -> this.copy(depth = max(depth - amount, 0))
            else -> throw IllegalAccessException("Unknown command: $cmd")
        }
    }
}

data class Submarine2(val horizontalPos: Int = 0, val aim: Int = 0, val depth: Int = 0) {
    fun process(cmd: String, amount: Int): Submarine2 {
        return when (cmd) {
            "forward" -> this.copy(horizontalPos = horizontalPos + amount, depth = depth + aim * amount)
            "down" -> this.copy(aim = aim + amount)
            "up" -> this.copy(aim = aim - amount)
            else -> throw IllegalAccessException("Unknown command: $cmd")
        }
    }
}

fun main() {
    val lines = File("src/day2/input.txt")
        .readLines()

    val sub = processInput(lines)
    println("Submarine's horizontal position is ${sub.horizontalPos}. It's at depth ${sub.depth}.")
    println("Result: ${sub.horizontalPos * sub.depth}")

    val sub2 = processInput2(lines)
    println("Submarine's horizontal position is ${sub2.horizontalPos}. It's at depth ${sub2.depth}.")
    println("Result: ${sub2.horizontalPos * sub2.depth}")
}

public fun processInput2(lines: List<String>): Submarine2 {
    val cmds = parseLines(lines)
    return processCommands2(cmds)
}

public fun processCommands2(cmds: List<Pair<String, Int>>) =
    cmds.fold(Submarine2()) { sub, p ->
        sub.process(p.first, p.second)
    }

public fun processInput(lines: List<String>): Submarine {
    val cmds = parseLines(lines)
    return processCommands(cmds)
}

public fun processCommands(cmds: List<Pair<String, Int>>) =
    cmds.fold(Submarine()) { sub, p ->
        sub.process(p.first, p.second)
    }

fun parseLines(lines: List<String>): List<Pair<String, Int>> {
    return lines.map { it.split(" ") }
        .map { Pair(it[0], it[1].toInt()) }
}
