package `2025`

import println
import readInput
import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    fun part1(input: List<String>): Long {
        val ops = mutableListOf<Char>()
        val cols = mutableListOf<Long>()

        for (op in input.last().split(" ").filter { it.isNotEmpty() }) {
            ops.add(op[0])
        }

        for (col in input.first().split(" ").filter { it.isNotEmpty() }) {
            cols.add(col.toLong())
        }

        for (line in input.dropLast(1).drop(1)) {
            line.split(" ").filter { it.isNotEmpty() }.forEachIndexed { idx, num ->
                if (ops[idx] == '*') cols[idx] *= num.toLong()
                else cols[idx] += num.toLong()
            }
        }

        return cols.sum()
    }

    fun part2(input: List<String>): Long {
        val ops = mutableListOf<Char>()
        val nums = mutableListOf<Long>()
        var sum = 0L

        for (op in input.last().split(" ").filter { it.isNotEmpty() }) {
            ops.add(op[0])
        }

        var opIdx = 0
        val maxIdx = input.maxOf { it.length } - 1
        for (idx in 0 .. maxIdx) {
            val col = input.dropLast(1).map { it[idx] }
            
            if (col.all { it.isWhitespace() } || idx == maxIdx) {
                if (idx == maxIdx) nums.add(col.filter { !it.isWhitespace() }.joinToString("").toLong())
                
                var num = nums.removeFirst()
                
                nums.forEach { 
                    if (ops[opIdx] == '*') num *= it
                    else num += it
                }

                sum += num
                opIdx++
                nums.clear()
            } else {
                nums.add(col.filter { !it.isWhitespace() }.joinToString("").toLong())
            }
        }
        
        return sum
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = Path("src/2025/Day06_test.txt").readText().lines()
    check(part1(testInput) == 4277556L)
    check(part2(testInput) == 3263827L)

    // Read the input from the `src/Day01.txt` file.
    val input = Path("src/2025/Day06.txt").readText().lines()
    part1(input).println()
    part2(input).println()
}
