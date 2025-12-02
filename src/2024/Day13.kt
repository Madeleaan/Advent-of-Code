package `2024`

import println
import readInput
import kotlin.math.ceil
import kotlin.math.floor

fun main() {
    fun solve(input: List<String>, part: Int): Long {
        return input.filter { it != ""}.chunked(3).sumOf {
            val regex = Regex("""\d+""")
            val numsA = regex.findAll(it[0]).map { res -> res.value.toInt() }.toList()
            val (aX, aY) = numsA
            val numsB = regex.findAll(it[1]).map { res -> res.value.toInt() }.toList()
            val (bX, bY) = numsB
            val numsRes = regex.findAll(it[2]).map { res -> res.value.toLong() + if(part == 2) 10000000000000 else 0  }.toList()
            val (x, y) = numsRes

            val a = (x*bY-bX*y)/(aX*bY-aY*bX).toDouble()
            val b = (aX*y-x*aY)/(aX*bY-aY*bX).toDouble()

            if(floor(a) != ceil(a) || floor(b) != ceil(b)) return@sumOf 0L

            return@sumOf (3*a + b).toLong()
        }
    }

    fun part1(input: List<String>): Long {
        return solve(input, 1)
    }

    fun part2(input: List<String>): Long {
        return solve(input, 2)
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    check(part1(readInput("Day13_test_1")) == 480L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
