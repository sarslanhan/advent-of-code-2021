package day3

import java.io.File

data class Diagnostics(
    val gammaRate: Int, val epsilonRate: Int,
    val o2GeneratorRating: Int = 0,
    val co2ScrubberRating: Int = 0
) {
    val powerConsumption = gammaRate * epsilonRate
    val lifeSupportRating = o2GeneratorRating * co2ScrubberRating
}

object DiagnosticsReader {
    fun read(lines: List<String>): Diagnostics? {
        if (lines.isEmpty()) {
            return null
        }
        val l = lines[0].length
        val bits = (0 until l).map { i -> lines.map { s -> s[i] } }
        val gamma = bits.map {
            val counts = it.groupingBy { bit -> bit }.eachCount()
            val ones = counts.getOrDefault('1', 0)
            val zeros = counts.getOrDefault('0', 0)
            if (ones > zeros) '1' else '0'
        }
        val epsilon = gamma.map { if (it == '0') '1' else '0' }

        val oxGen = (0 until l)
            .fold(lines) { acc, idx ->
                if (acc.size == 1) {
                    acc
                } else {
                    val counts = acc.map { it.elementAt(idx) }
                        .groupingBy { it }.eachCount()
                    val ones = counts.getOrDefault('1', 0)
                    val zeros = counts.getOrDefault('0', 0)
                    if (ones >= zeros) {
                        acc.filter { it.elementAt(idx) == '1' }
                    } else {
                        acc.filter { it.elementAt(idx) == '0' }
                    }
                }
            }
            .first()

        val co2Scrub = (0 until l)
            .fold(lines) { acc, idx ->
                if (acc.size == 1) {
                    acc
                } else {
                    val counts = acc.map { it.elementAt(idx) }
                        .groupingBy { it }.eachCount()
                    val ones = counts.getOrDefault('1', 0)
                    val zeros = counts.getOrDefault('0', 0)
                    if (ones < zeros) {
                        acc.filter { it.elementAt(idx) == '1' }
                    } else {
                        acc.filter { it.elementAt(idx) == '0' }
                    }
                }
            }
            .first()

        return Diagnostics(
            gammaRate = gamma.joinToString("").toInt(2),
            epsilonRate = epsilon.joinToString("").toInt(2),
            o2GeneratorRating = oxGen.toInt(2),
            co2ScrubberRating = co2Scrub.toInt(2),
        )
    }
}

fun main() {
    val lines = File("./src/day3/input.txt")
        .readLines()
    val diag = DiagnosticsReader.read(lines)
    println("Power consumption is ${diag?.powerConsumption ?: "unknown"}")
    println("Life support rating is ${diag?.lifeSupportRating ?: "unknown"}")
}