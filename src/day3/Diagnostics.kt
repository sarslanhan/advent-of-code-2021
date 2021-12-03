package day3

import java.io.File

data class Diagnostics(
    val gammaRate: Int, val epsilonRate: Int,
    val o2GeneratorRating: Int = 0,
    val co2ScrubberRating: Int = 0
) {
    val powerConsumption = gammaRate * epsilonRate
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

        return Diagnostics(
            gamma.joinToString("").toInt(2),
            epsilon.joinToString("").toInt(2)
        )
    }
}

fun main() {
    val lines = File("./src/day3/input.txt")
        .readLines()
    val diag = DiagnosticsReader.read(lines)
    println("Power consumption is ${diag?.powerConsumption ?: "unknown"}")
}