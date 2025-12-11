package `2025`

import println
import readInput

val paths = mutableMapOf<String, List<String>>()
val cache = mutableMapOf<Pair<String, String>, Long>()

fun main() {
    fun part1(input: List<String>): Long {
        paths.clear()
        cache.clear()
        
        for (line in input) {
            val split = line.split(": ")
            paths[split[0]] = split[1].split(' ')
        }
        
        return getPaths("you", "out")
    }

    fun part2(input: List<String>): Long {
        paths.clear()
        paths["out"] = listOf()
        cache.clear()

        for (line in input) {
            val split = line.split(": ")
            paths[split[0]] = split[1].split(' ')
        }

        val fft_dac = getPaths("svr", "fft") * getPaths("fft", "dac") * getPaths("dac", "out")
        val dac_fft = getPaths("svr", "dac") * getPaths("dac", "fft") * getPaths("fft", "out")
        
        return fft_dac + dac_fft
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    check(part1(readInput("2025/Day11_test_1")) == 5L)
    check(part2(readInput("2025/Day11_test_2")) == 2L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("2025/Day11")
    part1(input).println()
    part2(input).println()
}

fun getPaths(start: String, end: String): Long {
    return cache.getOrPut(Pair(start, end)) {
        if (end in paths[start]!!) 1
        else paths[start]!!.sumOf { getPaths(it, end) }
    }
}
