package day4

import java.io.File
import java.lang.IllegalArgumentException

data class Cell(val num: Int, val marked: Boolean)

data class Board(val cells: List<List<Cell>>) {
    val rows: List<Boolean> = cells.map { row -> row.all { it.marked } }
    val columns: List<Boolean> = cells.indices.map { col -> cells.all { row -> row[col].marked } }

    val isWinning: Boolean = rows.any { it } or columns.any { it }

    fun mark(num: Int): Board {
        if (isWinning) {
            return this
        }
        return Board(
            cells.map {
                it.map { cell ->
                    if (cell.num == num) cell.copy(marked = true) else cell
                }
            }
        )
    }
}

data class GameState(val boards: List<Board>, val remainingNumbers: List<Int>, val lastPlayed: Int? = null, val lastWinningBoard: Board? = null) {
    val hasAnyWinning = boards.any { it.isWinning }
    val remainingBoards = boards.filter { !it.isWinning }
    val isFinishedForPart1 = remainingNumbers.isEmpty() || hasAnyWinning
    val isFinishedForPart2 = remainingBoards.isEmpty()

    fun iterate(): GameState {
        val num = remainingNumbers.first()
        val newBoards = boards.map { it.mark(num) }
        val lastWinningBoard = newBoards
            .filter { it.isWinning }
            .find { !boards.contains(it) }
        return GameState(newBoards, remainingNumbers.drop(1), num, lastWinningBoard)
    }
}

object BingoSimulator {
    fun scoreOfFirstWinner(state: GameState): Int {
        val finalState = state.remainingNumbers.indices
            .fold(state) { game, _ ->
                if (game.isFinishedForPart1) game else game.iterate()
            }
        if (!finalState.hasAnyWinning) {
            throw IllegalArgumentException("Boards has no winning state")
        }
        val winningBoard = finalState.boards.find { it.isWinning }
            ?: throw IllegalArgumentException("Final game state has no winning boards")
        val sum = winningBoard
            .cells.sumOf { row -> row.sumOf { if (!it.marked) it.num else 0 } }
        return finalState.lastPlayed?.times(sum) ?: throw IllegalArgumentException("Final game state has invalid state")
    }

    fun scoreOfLastWinner(state: GameState): Int {
        val finalState = state.remainingNumbers.indices
            .fold(state) { game, _ ->
                if (game.isFinishedForPart2) game else game.iterate()
            }
        if (finalState.remainingBoards.isNotEmpty()) {
            throw IllegalArgumentException("Boards has no winning state")
        }
        val lastWinningBoard = finalState.lastWinningBoard
            ?: throw IllegalArgumentException("Final game state has no winning boards")
        val sum = lastWinningBoard
            .cells.sumOf { row -> row.sumOf { if (!it.marked) it.num else 0 } }
        return finalState.lastPlayed?.times(sum) ?: throw IllegalArgumentException("Final game state has invalid state")
    }
}

public fun parseInput(lines: List<String>): GameState {
    val numsStr = lines.first() ?: throw IllegalArgumentException("Invalid input")
    val nums = numsStr.split(",").map { it.toInt() }
    val boards = lines
        .drop(1)
        .filter { it.isNotBlank() }
        .chunked(5)
        .map { boardLines ->
            Board(boardLines
                .map { rowStr ->
                    rowStr
                        .split("\\s".toRegex())
                        .filter { it.isNotBlank() }
                        .map { Cell(it.toInt(), false) }
                })
        }

    return GameState(boards, nums)
}

fun main() {
    val lines = File("src/day4/input.txt")
        .readLines()
    val initialState = parseInput(lines)
    val score = BingoSimulator.scoreOfFirstWinner(initialState)
    println("---Part 1---")
    println("Game score is: $score")
    val score2 = BingoSimulator.scoreOfLastWinner(initialState)
    println("---Part 2---")
    println("Game score is: $score2")
}