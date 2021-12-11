package day10

import java.io.File
import java.math.BigInteger

data class Token(val char: Char) {
    companion object Tokens {
        val openingChars = "([{<".toSet()
        val closingChars = ")]}>".toSet()
        val validChars = openingChars.union(closingChars).toSet()
    }

    init {
        require(validChars.contains(char))
    }

    val isOpening = openingChars.contains(char)
//    val isClosing = closingChars.contains(char)

    fun matchingToken(): Token {
        val matchingChar = when (char) {
            '(' -> ')'
            '[' -> ']'
            '{' -> '}'
            '<' -> '>'
            ')' -> '('
            ']' -> '['
            '}' -> '{'
            '>' -> '<'
            else -> throw IllegalArgumentException("Unknown token: $char")
        }
        return Token(matchingChar)
    }

    fun errorScore(): Int {
        return when (char) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0
        }
    }

    fun autoCompleteScore(): Int {
        return when (char) {
            ')' -> 1
            ']' -> 2
            '}' -> 3
            '>' -> 4
            else -> 0
        }
    }
}

object SyntaxScoring {
    private tailrec fun calculateScore(tokens: List<Token>, stack: List<Token> = listOf()): Pair<Int, List<Token>> {
        if (tokens.isEmpty()) {
            return if (stack.isNotEmpty()) 0 to stack // incomplete line
            else 0 to stack // complete & correct line
        }
        val token = tokens.first()
        return if (token.isOpening) {
            calculateScore(tokens.drop(1), listOf(token) + stack)
        } else {
            if (stack.isEmpty()) {
                token.errorScore() to stack
            } else {
                val top = stack.first()
                if (top.matchingToken() != token) {
                    token.errorScore() to stack
                } else {
                    calculateScore(tokens.drop(1), stack.drop(1))
                }
            }
        }
    }

    fun calculateScore(s: String): Int {
        val tokens = s.map { Token(it) }
        return calculateScore(tokens).first
    }

    fun autoCompleteScore(s: String): BigInteger {
        val tokens = s.map { Token(it) }
        val (score, remaining) = calculateScore(tokens)
        if (score == 0 && remaining.isNotEmpty()) {
            return remaining.fold(0.toBigInteger()) { acc, token ->
                (acc * 5.toBigInteger()) + token.matchingToken().autoCompleteScore().toBigInteger()
            }
        }
        return 0.toBigInteger()
    }

    fun middleScore(ss: List<String>): BigInteger {
        val scores = ss.map { autoCompleteScore(it) }
            .filter { it != 0.toBigInteger() }
            .sorted()

        return scores[scores.size / 2]
    }
}

fun main() {
    val input = File("src/day10/input.txt")
        .readLines()
    val part1 = input.sumOf { SyntaxScoring.calculateScore(it) }
    println("---Part 1---")
    println("The sum of all error scores: $part1")
    val part2 = SyntaxScoring.middleScore(input)
    println("---Part 2---")
    println("The middle score: $part2")
}