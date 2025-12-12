package `2025`

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var lines = input.toList()
        val sizes = mutableListOf<Int>()
        var ok = 0
        
        while (lines.isNotEmpty()) {
            val section = lines.takeWhile(String::isNotEmpty)
            lines = lines.drop(section.size + 1)
            
            if ('x' !in section.first()) {
                sizes.add(section.drop(1).sumOf { it.count { c -> c == '#' } })
            } else {
                for (line in section) {
                    val nums = line.replace(Regex("[^0-9]"), " ")
                        .split(" ")
                        .filter(String::isNotEmpty)
                        .map(String::toInt)
                    
                    if (nums.take(2).reduce(Int::times) >= nums.drop(2).sumOf { it * 9 }) ok++
                }
            }
        } 
        
        return ok
    }

    fun part2(input: List<String>): Int {
        return 67
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("2025/Day12")
    part1(input).println()
    part2(input).println()
}
