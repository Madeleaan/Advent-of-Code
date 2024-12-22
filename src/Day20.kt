import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>, atLeast: Int): Int {
        var startPos = Coords(0, 0)
        var endPos = Coords(0, 0)
        input.forEachIndexed { y, row -> row.forEachIndexed { x, c ->
            when (c) {
                'S' -> startPos = Coords(x, y)
                'E' -> endPos = Coords(x, y)
            }
        }}
        val path = mutableListOf(startPos)
        val dirs = listOf(Coords(0, -1), Coords(1, 0), Coords(0, 1), Coords(-1, 0))
        do {
            try {
                dirs.forEach {
                    val pos = path.last() + it
                    if(pos !in path && input[pos.second][pos.first] != '#') path.add(pos)
                }
            } catch (_: IndexOutOfBoundsException) {}
        } while (path.last() != endPos)
        val cheats = listOf(Coords(0, -2), Coords(2, 0), Coords(0, 2), Coords(-2, 0))
        val skipped = mutableListOf<Int>()
        path.forEach {
            cheats.forEach { cheat ->
                if (it + cheat in path) {
                    skipped.add(path.indexOf(it + cheat) - path.indexOf(it) - 2)
                }
            }
        }

        return skipped.count { it >= atLeast }
    }

    fun part2(input: List<String>, atLeast: Int): Int {
        var startPos = Coords(0, 0)
        var endPos = Coords(0, 0)
        input.forEachIndexed { y, row -> row.forEachIndexed { x, c ->
            when (c) {
                'S' -> startPos = Coords(x, y)
                'E' -> endPos = Coords(x, y)
            }
        }}
        val path = mutableListOf(startPos)
        val dirs = listOf(Coords(0, -1), Coords(1, 0), Coords(0, 1), Coords(-1, 0))
        do {
            try {
                dirs.forEach {
                    val pos = path.last() + it
                    if(pos !in path && input[pos.second][pos.first] != '#') path.add(pos)
                }
            } catch (_: IndexOutOfBoundsException) {}
        } while (path.last() != endPos)
        val skipped = mutableListOf<Int>()
        path.forEachIndexed { index, cheatStart ->
            path.drop(index+1).forEach { cheatEnd ->
                val dist = (cheatStart.first - cheatEnd.first).absoluteValue + (cheatStart.second - cheatEnd.second).absoluteValue
                if(dist <= 20) {
                    skipped.add(path.indexOf(cheatEnd) - path.indexOf(cheatStart) - dist)
                }
            }
        }
        return skipped.count { it >= atLeast }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day20_test")
    check(part1(testInput, 20) == 5)
    check(part2(testInput, 72) == 29)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day20")
    part1(input, 100).println()
    part2(input, 100).println()
}
