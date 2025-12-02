package `2024`

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val reg = Regex("mul\\(\\d+,\\d+\\)")
        return input.sumOf { line ->
            reg.findAll(line).sumOf sumLine@{
                val nums = it.value.replace(Regex("[^0-9,]"), "")
                    .split(",").map { num -> num.toInt() }
                return@sumLine nums[0] * nums[1]
            }
        }
    }

    fun part2(input: List<String>): Int {
        val reg = Regex("""(mul\(\d+,\d+\)|do(n't)?\(\))""")
        var count = true
        return input.sumOf { line ->
            reg.findAll(line).sumOf sumLine@{
                count = when (it.value) {
                    "do()" -> true
                    "don't()" -> false
                    else -> {
                        if (!count) return@sumLine 0
                        val nums = it.value.replace(Regex("[^0-9,]"), "")
                            .split(",").map { num -> num.toInt() }
                        return@sumLine nums[0] * nums[1]
                    }
                }
                return@sumLine 0
            }
        }
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("Day03_test_01")) == 161)
    check(part2(readInput("Day03_test_02")) == 48)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
