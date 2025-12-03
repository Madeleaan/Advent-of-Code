package `2025`

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0

        for (line in input) {
            var highest = 0

            for (i in 0..line.length - 2) {
                for (j in i+1..line.length - 1) {
                    val jolts = line[i].digitToInt()*10 + line[j].digitToInt()

                    if (jolts > highest) highest = jolts
                }
            }

            sum += highest
        }

        return sum
    }

    fun part2(input: List<String>): Long {
        var sum = 0L

        for (line in input) {
            var jolts = ""
            var set = line

            for (i in 11 downTo 0) {
                val subset = set.dropLast(i).map(Char::digitToInt)
                val maxIdx = subset.indexOf(subset.max())

                jolts += subset[maxIdx]
                set = set.drop(maxIdx + 1)
            }

            sum += jolts.toLong()
        }

        return sum
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("2025/Day03_test")
    check(part1(testInput) == 357)
    check(part2(testInput) == 3121910778619)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("2025/Day03")
    part1(input).println()
    part2(input).println()
}
