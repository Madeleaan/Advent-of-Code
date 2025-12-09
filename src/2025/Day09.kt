package `2025`

import println
import readInput
import kotlin.math.abs
import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Long {
        val coords = mutableListOf<Pair<Int, Int>>()
        var maxSize = 0L
        
        for (line in input) {
            coords.add(line.split(',').map { it.toInt() }.let { it[0] to it[1] })
        }
        
        for (i in 0 ..< coords.size - 1) {
            for (j in i + 1 ..< coords.size) {
                val pt1 = coords[i]
                val pt2 = coords[j]
                
                val size = (abs(pt1.first - pt2.first) + 1L) * (abs(pt1.second - pt2.second) + 1L)
                maxSize = max(maxSize, size)
            }
        }
        
        return maxSize
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("2025/Day09_test")
    check(part1(testInput) == 50L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("2025/Day09")
    part1(input).println()
    part2(input).println()
}