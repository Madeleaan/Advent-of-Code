package `2024`

import println
import readInput

fun main() {
    fun invalidNums(line: List<Int>, instructions: List<String>): List<Pair<Int, Int>> {
        val out = mutableListOf<Pair<Int, Int>>()
        instructions.forEach { rule ->
            val split = rule.split("|").map { it.toInt() }
            val f1 = line.indexOf(split[0])
            val f2 = line.indexOf(split[1])
            if(f1 != -1 && f2 != -1 && f1 > f2) out.add(Pair(f1, f2))
        }
        return out
    }

    fun part1(input: List<String>): Int {
        val p1 = input.take(input.indexOf(""))
        val p2 = input.drop(input.indexOf("")+1)

        return p2.sumOf {
            val a = it.split(",").map { c -> c.toInt() }
            if (invalidNums(a, p1).isNotEmpty()) return@sumOf 0
            return@sumOf a[a.size/2]
        }
    }

    fun part2(input: List<String>): Int {
        val p1 = input.take(input.indexOf(""))
        val p2 = input.drop(input.indexOf("")+1).toMutableList()

        return p2.sumOf {
            val a = it.split(",").map { c -> c.toInt() }.toMutableList()
            var pairs = invalidNums(a, p1)
            if (pairs.isEmpty()) return@sumOf 0
            do {
                a[pairs[0].first] = a[pairs[0].second].also { a[pairs[0].second] = a[pairs[0].first] }
                pairs = invalidNums(a, p1)
            } while(pairs.isNotEmpty())
            return@sumOf a[a.size/2]
        }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
