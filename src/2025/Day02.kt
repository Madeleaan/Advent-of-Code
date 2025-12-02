package `2025`

import println
import readInput

fun main() {
    fun part1(input: List<String>): Long {
        val ranges = input[0].split(',')
        var invalid = 0L

        for (range in ranges) {
            val nums = range.split('-').map { it.toLong() }

            for (i in nums[0]..nums[1]) {
                val len = i.toString().length
                if (len % 2 != 0) continue

                val chunks = i.toString().chunked(len / 2)
                if (chunks.all { it == chunks.first() }) invalid += i
            }
        }

        return invalid
    }

    fun part2(input: List<String>): Long {
        val ranges = input[0].split(',')
        var invalid = 0L

        for (range in ranges) {
            val nums = range.split('-').map { it.toLong() }

            for (i in nums[0]..nums[1]) {
                val len = i.toString().length

                for (j in 2..len) {
                    if (len % j != 0) continue

                    val chunks = i.toString().chunked(len / j)
                    if (chunks.all { it == chunks.first() }) {
                        invalid += i
                        break
                    }
                }
            }
        }

        return invalid
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("2025/Day02_test")
    check(part1(testInput) == 1227775554L)
    check(part2(testInput) == 4174379265L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("2025/Day02")
    part1(input).println()
    part2(input).println()
}
