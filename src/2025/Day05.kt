package `2025`

import println
import readInput
import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        val ranges = mutableListOf<Pair<Long, Long>>()
        var fresh = 0

        for (line in input) {
            if (line.contains('-')) {
                ranges.add(Pair(line.split('-')[0].toLong(), line.split('-')[1].toLong()))
            } else {
                if (line == "") continue

                val num = line.toLong()
                for (range in ranges) {
                    if (num in range.first .. range.second) {
                        fresh++
                        break
                    }
                }
            }
        }

        return fresh
    }

    fun part2(input: List<String>): Long {
        var intervals = mutableListOf<Pair<Long, Long>>()
        var total = 0L

        for (line in input) {
            if (line.contains('-')) {
                val split = line.split('-').map(String::toLong)
                intervals.add(Pair(split[0], split[1]))
            }
        }

        intervals = intervals.sortedBy { it.first }.toMutableList()

        while (intervals.size > 1) {
            val int1 = intervals.removeFirst()
            val int2 = intervals.first()

            if (int1.second >= int2.first) {
                intervals.removeFirst()
                intervals.addFirst(Pair(int1.first, max(int1.second, int2.second)))
            } else {
                total += int1.second - int1.first + 1
            }
        }
        total += intervals.first().second - intervals.first().first + 1

        return total
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("2025/Day05_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 14L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("2025/Day05")
    part1(input).println()
    part2(input).println()
}
