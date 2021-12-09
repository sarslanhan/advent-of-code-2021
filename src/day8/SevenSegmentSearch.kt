package day8

import java.io.File
import kotlin.math.abs

data class Entry(val signalPatterns: List<Set<Segment>>, val outputValues: List<Set<Segment>>)

object Parser {
    fun processInput(lines: List<String>): List<Entry> {
        return lines.map { line ->
            val parts = line.split(" | ")
            val patterns = parts[0]
            val output = parts[1]
            Entry(patterns.split(" ")
                .map { it.map { Segment.from(it) }.toSet() },
                output.split(" ")
                    .map { it.map { Segment.from(it) }.toSet() }
            )
        }
    }
}

enum class Segment(val char: Char) {
    A('a'),
    B('b'),
    C('c'),
    D('d'),
    E('e'),
    F('f'),
    G('g'),
    Unknown('?');

    companion object Parser {
        val All = arrayOf(A, B, C, D, E, F, G)
        fun from(x: Char): Segment {
            return when (x) {
                'a' -> A
                'b' -> B
                'c' -> C
                'd' -> D
                'e' -> E
                'f' -> F
                'g' -> G
                else -> throw IllegalArgumentException("$x is not a valid segment identifier")
            }
        }
    }
}

data class Board(val connections: Map<Segment, Segment>) {
    fun isComplete(): Boolean {
        return Segment.All.all { seg -> connections.getOrDefault(seg, Segment.Unknown) != Segment.Unknown }
    }

    fun discover(signals: List<Set<Segment>>): Board {
        val mappings = signals
            .map { signal ->
                val unknowns = signal
                    .map { connections.getOrDefault(it, Segment.Unknown) }

            }

        return this.copy()
    }
}

object SevenSegmentSearch {
    val numbers = mapOf(
        0 to setOf("abcefg"),
        1 to setOf("cf"),
        2 to setOf("acdeg"),
        3 to setOf("acdfg"),
        4 to setOf("bcdf"),
        5 to setOf("abdfg"),
        6 to setOf("abdefg"),
        7 to setOf("acf"),
        8 to setOf("abcdefg"),
        9 to setOf("abcdefg"),
    )

    fun part1(entries: List<Entry>): Int {
        return entries
            .sumOf { it.outputValues.count { out -> out.size in setOf(2, 4, 3, 7) } }
    }

    fun decodeInputMapping(
        inputsToDiscover: List<Set<String>>,
        knownCombinations: Map<Int, Set<Char>>,
        knownMappings: Map<Char, Char>
    ): Map<Char, Char> {
        if (knownMappings.size == 7) {
            return knownMappings
        }
        "abcdefg".map { knownMappings.get(it) }
        val missingMappings = numbers.keys.subtract(knownCombinations.keys)
        return mapOf()
    }

    fun part2(entries: List<Entry>): Int {

        val allInputs = entries
            .map { e ->
                val known = mapOf(
                    1 to setOf(e.signalPatterns.find { it.size == 2 } ?: setOf()),
                    4 to setOf(e.signalPatterns.find { it.size == 4 } ?: setOf()),
                    7 to setOf(e.signalPatterns.find { it.size == 3 } ?: setOf()),
                    8 to setOf(e.signalPatterns.find { it.size == 7 } ?: setOf()),
                ).mapValues {
//                    it.value
//                        .map { segs -> segs.map { seg -> Mapping(seg, Segment.Unknown) } }
                }

                "abcdefg"
            }
        return 0
    }
}

fun main() {
    val input = File("src/day8/input.txt")
        .readLines()
    val entries = Parser.processInput(input)
    val part1 = SevenSegmentSearch.part1(entries)
    println("---Part 1---")
    println("The number of times digits 1, 4, 7, or 8 appear in the output: $part1")
    val part2 = SevenSegmentSearch.part2(entries)
    println("---Part 2---")
    println("The sum of all output values: $part2")
}