package `2025`

import println
import readInput
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

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

    fun part2(input: List<String>): Long {
        val coords = mutableListOf<Pair<Int, Int>>()
        var maxSize = 0L

        for (line in input) {
            coords.add(line.split(',').map { it.toInt() }.let { it[0] to it[1] })
        }

        for (i in 0 ..< coords.size - 1) {
            rect@ for (j in i + 1 ..< coords.size) {
                val pt1 = coords[i]
                val pt2 = coords[j]

                val size = (abs(pt1.first - pt2.first) + 1L) * (abs(pt1.second - pt2.second) + 1L)
                if (size <= maxSize) continue
                
                for (line in coords.zipWithNext()) {
                    val pos0 = computePointPos(line.first, pt1 to pt2)
                    val pos1 = computePointPos(line.second, pt1 to pt2)
                    
                    if ((pos0 or pos1) != 0 && (pos0 and pos1) == 0) {
                        continue@rect
                    }
                }
                
                maxSize = size
            }
        }

        return maxSize
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("2025/Day09_test")
    check(part1(testInput) == 50L)
//    check(part2(testInput) == 24L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("2025/Day09")
    part1(input).println()
    part2(input).println()
}

const val INSIDE = 0b0000
const val LEFT = 0b0001
const val RIGHT = 0b0010
const val BOTTOM = 0b0100
const val TOP = 0b1000

fun computePointPos(point: Pair<Int, Int>, rect: Pair<Pair<Int, Int>, Pair<Int, Int>>): Int {
    var code = INSIDE
    
    if (point.first <= min(rect.first.first, rect.second.first)) code = code or LEFT
    else if (point.first >= max(rect.first.first, rect.second.first)) code = code or RIGHT
    if (point.second <= min(rect.first.second, rect.second.second)) code = code or TOP
    else if (point.second >= max(rect.first.second, rect.second.second)) code = code or BOTTOM
    
    return code
}