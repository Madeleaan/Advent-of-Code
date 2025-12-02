package `2024`

import println
import readInput

fun main() {
    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = Pair(this.first + other.first, this.second + other.second)
    operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>) = Pair(this.first - other.first, this.second - other.second)

    fun part1(input: List<String>): Int {
        val width = input[0].length
        val height = input.indexOfFirst { it == "" }
        val instructions = input.drop(height + 1)

        val boxes = mutableSetOf<Pair<Int, Int>>()
        val walls = mutableSetOf<Pair<Int, Int>>()
        var pos = Pair(0, 0)

        for (y in 0..<height) for (x in 0..<width) {
            when (input[y][x]) {
                'O' -> boxes.add(Pair(x, y))
                '#' -> walls.add(Pair(x, y))
                '@' -> pos = Pair(x, y)
            }

        }

        val moves = mapOf(Pair('^', Pair(0, -1)), Pair('>', Pair(1, 0)), Pair('v', Pair(0, 1)), Pair('<', Pair(-1, 0)))

        instructions.forEach { line ->
            line.forEach {
                val nextPos = pos + moves[it]!!
                if (!walls.contains(nextPos)) {
                    if(!boxes.contains(nextPos)) {
                        pos = nextPos
                    } else {
                        var boxPos = nextPos
                        while(boxes.contains(boxPos + moves[it]!!)) {
                            boxPos += moves[it]!!
                        }
                        if (!walls.contains(boxPos + moves[it]!!)) {
                            boxes.add(boxPos + moves[it]!!)
                            boxes.remove(nextPos)
                            pos = nextPos
                        }
                    }
                }
            }
        }
        return boxes.sumOf { it.first + it.second*100 }
    }

    fun part2(input: List<String>): Int {
        val width = input[0].length*2
        val height = input.indexOfFirst { it == "" }
        val instructions = input.drop(height + 1)

        val lBoxes = mutableSetOf<Pair<Int, Int>>()
        val rBoxes = mutableSetOf<Pair<Int, Int>>()
        val walls = mutableSetOf<Pair<Int, Int>>()
        var pos = Pair(0, 0)

        for (y in 0..<height) for (x in 0..<width/2) {
            when (input[y][x]) {
                'O' -> lBoxes.add(Pair(x*2, y)).also { rBoxes.add(Pair(x*2 + 1, y)) }
                '#' -> walls.add(Pair(x*2, y)).also { walls.add(Pair(x*2 + 1, y)) }
                '@' -> pos = Pair(x*2, y)
            }

        }

        val moves = mapOf(Pair('^', Pair(0, -1)), Pair('>', Pair(1, 0)), Pair('v', Pair(0, 1)), Pair('<', Pair(-1, 0)))

        var i = 0
        instructions.forEach { line ->
            line.forEach ins@ {
                i++
                val nextPos = pos + moves[it]!!
                if (!walls.contains(nextPos)) {
                    if(!lBoxes.contains(nextPos) && !rBoxes.contains(nextPos)) {
                        pos = nextPos
                    } else if (moves[it]!!.second == 0) {
                        val boxes = mutableListOf<Pair<Int, Int>>()
                        boxes.addAll(lBoxes)
                        boxes.addAll(rBoxes)
                        var boxPos = nextPos
                        while(boxes.contains(boxPos + moves[it]!!)) {
                            boxPos += moves[it]!!
                        }
                        if (!walls.contains(boxPos + moves[it]!!)) {
                            boxes.add(boxPos + moves[it]!!)
                            boxes.remove(nextPos)
                            pos = nextPos
                        }
                        boxes.sortBy { b -> b.first }
                        val lToMove = mutableListOf<Pair<Int, Int>>()
                        val rToMove = mutableListOf<Pair<Int, Int>>()
                        boxes.forEach { b ->
                            if(!lToMove.contains(b + Pair(-1, 0))) lToMove.add(b)
                            else rToMove.add(b)
                        }
                        lToMove.forEach { b ->
                            lBoxes.add(b)
                            lBoxes.remove(b - moves[it]!!)
                        }
                        rToMove.forEach { b ->
                            rBoxes.add(b)
                            rBoxes.remove(b - moves[it]!!)
                        }
                    } else {
                        if (i == 22) {
                            "a".println()
                        }
                        var toMove = mutableListOf(nextPos)
                        toMove.add(nextPos + Pair(if (lBoxes.contains(nextPos)) 1 else -1, 0 ))
                        var scanY = nextPos.second
                        do {
                            var added = 0
                            toMove.filter { b -> b.second == scanY }.forEach { b ->
                                if(walls.contains(b + moves[it]!!)) return@ins
                                if(lBoxes.contains(b + moves[it]!!)) {
                                    toMove.add(b + moves[it]!!)
                                    toMove.add(b + moves[it]!! + Pair(1, 0))
                                    added++
                                }
                                if(rBoxes.contains(b + moves[it]!!)) {
                                    toMove.add(b + moves[it]!!)
                                    toMove.add(b + moves[it]!! + Pair(-1, 0))
                                    added++
                                }
                            }
                            scanY += moves[it]!!.second
                        } while (added != 0)
                        toMove = toMove.sortedBy { b -> b.first }.toSet().toMutableList()
                        val lToMove = mutableListOf<Pair<Int, Int>>()
                        val rToMove = mutableListOf<Pair<Int, Int>>()
                        toMove.forEach { b ->
                            if(!lToMove.contains(b + Pair(-1, 0))) lToMove.add(b)
                            else rToMove.add(b)
                        }
                        lToMove.sortBy { b -> b.second }
                        rToMove.sortBy { b -> b.second }
                        if (moves[it]!!.second == 1) {
                            lToMove.reverse()
                            rToMove.reverse()
                        }
                        lToMove.forEach { b ->
                            lBoxes.add(b + moves[it]!!)
                            lBoxes.remove(b)
                        }
                        rToMove.forEach { b ->
                            rBoxes.add(b + moves[it]!!)
                            rBoxes.remove(b)
                        }
                        pos = nextPos
                    }
                }
            }
        }
        return lBoxes.sumOf { it.first + it.second*100 }
    }

    check(part1(readInput("Day15_test_1")) == 2028)
    check(part2(readInput("Day15_test_2")) == 9021)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
