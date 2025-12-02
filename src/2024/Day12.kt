package `2024`

import println
import readInput
import kotlin.collections.ArrayDeque

fun main() {
    fun part1(input: List<String>): Int {
        val directions = listOf(Pair(0, -1), Pair(1, 0), Pair(0, 1), Pair(-1, 0))
        val visited = mutableSetOf<Pair<Int, Int>>()
        var price = 0

        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, value ->
                if(!visited.contains(Pair(x, y))) {
                    val queue = ArrayDeque<Pair<Int, Int>>()
                    var perimeter = 0
                    var area = 1

                    visited.add(Pair(x, y))
                    queue.addLast(Pair(x, y))
                    while (queue.isNotEmpty()) {
                        val pos = queue.removeFirst()
                        visited.add(pos)
                        var n = 4
                        for (dir in directions) {
                            val newX = pos.first + dir.first
                            val newY = pos.second + dir.second
                            try {
                                if (input[newY][newX] == value) {
                                    n--
                                    if (!visited.contains(Pair(newX, newY)) && !queue.contains(Pair(newX, newY))) {
                                        queue.addLast(Pair(newX, newY))
                                        area++
                                    }
                                }
                            } catch (_: IndexOutOfBoundsException) {}
                        }
                        perimeter += n
                    }
                    price += area*perimeter
                }
            }
        }

        return price
    }

    fun part2(input: List<String>): Int {
        val directions = listOf(Pair(0, -1), Pair(1, 0), Pair(0, 1), Pair(-1, 0))
        val visited = mutableSetOf<Pair<Int, Int>>()
        var price = 0

        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, value ->
                if(!visited.contains(Pair(x, y))) {
                    val queue = ArrayDeque<Pair<Int, Int>>()
                    var corners = 0
                    var area = 1

                    visited.add(Pair(x, y))
                    queue.addLast(Pair(x, y))
                    while (queue.isNotEmpty()) {
                        val pos = queue.removeFirst()
                        visited.add(pos)
                        for (dir in directions) {
                            val newX = pos.first + dir.first
                            val newY = pos.second + dir.second
                            try {
                                if (input[newY][newX] == value) {
                                    if (!visited.contains(Pair(newX, newY)) && !queue.contains(Pair(newX, newY))) {
                                        queue.addLast(Pair(newX, newY))
                                        area++
                                    }
                                }
                            } catch (_: IndexOutOfBoundsException) {}
                        }
                        corners += (directions + directions.first()).windowed(2).filter {
                            val a = input[pos.second][pos.first]
                            val b = try { input[pos.second + it.first().second][pos.first + it.first().first] } catch(_: IndexOutOfBoundsException) { null }
                            val c = try { input[pos.second + it.last().second][pos.first + it.last().first] } catch(_: IndexOutOfBoundsException) { null }
                            val d = try {
                                input[pos.second + it.first().second + it.last().second][pos.first + it.first().first + it.last().first]
                            } catch(_: IndexOutOfBoundsException) { null }
                            (a != b && a != c) || (a == b && a == c && a != d)
                        }.size
                    }
                    price += area*corners
                }
            }
        }

        return price
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 1930)
    check(part2(testInput) == 1206)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
