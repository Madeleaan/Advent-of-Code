package `2025`

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val queue = ArrayDeque<Pair<Int, Int>>()
        val splits = mutableSetOf<Pair<Int, Int>>()
        
        input.forEachIndexed { y, line -> 
            line.forEachIndexed { x, char -> 
                if (char == 'S') queue.add(Pair(x, y + 1))
            }
        }
        
        while (queue.isNotEmpty()) {
            val (x, y) = queue.removeFirst()
            if (y >= input.size) break
            
            when (input[y][x]) {
                '.' -> queue.add(Pair(x, y + 1))
                '^' -> {
                    splits.add(Pair(x, y))
                    if (y + 1 < input.size) {
                        val newLeft = Pair(x - 1, y + 1)
                        val newRight = Pair(x + 1, y + 1)
                        
                        if (newLeft.first >= 0 && !queue.contains(newLeft)) queue.add(newLeft)
                        if (newRight.first < input[0].length && !queue.contains(newRight)) queue.add(newRight)
                    }
                }
            }
        }
        
        return splits.size
    }

    fun part2(input: List<String>): Long {
        var blocks = Array(input[0].length) { 0L }

        for (line in input) {
            val newBlocks = Array(input[0].length) { 0L }
            
            line.forEachIndexed { idx, char -> 
                when (char) {
                    'S' -> newBlocks[idx] = 1
                    '.' -> newBlocks[idx] += blocks[idx]
                    '^' -> {
                        if (idx - 1 >= 0) newBlocks[idx - 1] += blocks[idx]
                        if (idx + 1 < input.size) newBlocks[idx + 1] += blocks[idx]
                    }
                }
            }
            
            blocks = newBlocks
        }
        
        return blocks.sum()
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("2025/Day07_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 40L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("2025/Day07")
    part1(input).println()
    part2(input).println()
}
