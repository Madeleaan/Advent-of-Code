package `2025`

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var accessible = 0

        input.toList().forEachIndexed { y, line ->
            line.toList().forEachIndexed { x, char ->
                if (char == '@') {
                    var rolls = 0

                    for (dy in -1..1) {
                        for (dx in -1..1) {
                            if (dy == 0 && dx == 0) continue
                            if (x + dx !in 0..<line.length) continue
                            if (y + dy !in 0..<input.size) continue

                            if (input[y + dy][x + dx] == '@') rolls++
                        }
                    }

                    if (rolls < 4) accessible++
                }
            }
        }

        return accessible
    }

    fun part2(input: List<String>): Int {
        val map = input.toMutableList()
        var removed = 0

        do {
            var accessible = 0

            val mapCopy = map.toMutableList()
            mapCopy.forEachIndexed { y, line ->
                line.forEachIndexed { x, char ->
                    if (char == '@') {
                        var rolls = 0

                        for (dy in -1..1) {
                            for (dx in -1..1) {
                                if (dy == 0 && dx == 0) continue
                                if (x + dx !in 0..<line.length) continue
                                if (y + dy !in 0..<input.size) continue

                                if (mapCopy[y + dy][x + dx] == '@') rolls++
                            }
                        }

                        if (rolls < 4) {
                            removed++
                            accessible++

                            val lineCopy = map[y].toMutableList()
                            lineCopy[x] = 'x'
                            map[y] = lineCopy.joinToString("")
                        }
                    }
                }
            }
        } while (accessible > 0)


        return removed
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("2025/Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("2025/Day04")
    part1(input).println()
    part2(input).println()
}
