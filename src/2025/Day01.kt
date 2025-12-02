package `2025`

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var dial = 50
        var zeroes = 0

        for (line in input) {
            val amount = line.substring(1).toInt()

            if (line[0] == 'L') dial -= amount
            else dial += amount

            if (dial % 100 == 0) zeroes++
        }

        return zeroes
    }

    fun part2(input: List<String>): Int {
        var dial = 50
        var zeroes = 0

        for (line in input) {
            val amount = line.substring(1).toInt()

            if (line[0] == 'L') {
                repeat(amount) {
                    dial -= 1
                    if (dial % 100 == 0) zeroes++
                }
                while (dial < 0) dial += 100
            }
            else {
                repeat(amount) {
                    dial += 1
                    if (dial % 100 == 0) zeroes++
                }
                while (dial > 0) dial -= 100
            }
        }

        return zeroes
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("2025/Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("2025/Day01")
    part1(input).println()
    part2(input).println()
}
