package day3

import kotlin.test.Test
import kotlin.test.*
import java.io.File

internal class DiagnosticsKtTest {
    private val lines = File("test/day3/sample.txt")
        .readLines()

    @Test
    fun processInput() {
        val diag = DiagnosticsReader.read(lines)
        assertNotNull(diag)
        assertEquals(22, diag.gammaRate)
        assertEquals(9, diag.epsilonRate)
        assertEquals(198, diag.powerConsumption)
    }
}