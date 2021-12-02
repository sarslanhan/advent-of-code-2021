package day2

import kotlin.test.Test
import kotlin.test.*
import java.io.File

internal class SubmarineKtTest {
    private val lines = File("test/day2/sample.txt")
        .readLines()

    @Test
    fun processInput() {
        val sub = day2.processInput(lines)
        assertEquals(10, sub.depth)
        assertEquals(15, sub.horizontalPos)
    }

    @Test
    fun processInput2() {
        val sub = day2.processInput2(lines)
        assertEquals(60, sub.depth)
        assertEquals(15, sub.horizontalPos)
    }
}