package `2024`

import println
import readInput

fun main() {
    fun isValidList(nums: List<Int>): Boolean {
        return (nums == nums.sorted() || nums == nums.sortedDescending())
                && nums.sorted().windowed(2).all { it[1] - it[0] in 1..3 }
    }

    fun part1(input: List<String>): Int {
        return input.count { l -> isValidList(l.split(" ").map { it.toInt() }) }
    }

    fun part2(input: List<String>): Int {
        return input.map { l ->
            val nums = l.split(" ").map { it.toInt() }
            when {
                isValidList(nums) -> 1
                nums.indices.any { num -> isValidList(nums.filterIndexed { i, _ -> i != num }) } -> 1
                else -> 0
            }
        }.sum()
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
