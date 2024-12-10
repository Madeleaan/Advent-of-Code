fun main() {
    fun solve(input: List<String>, ends: MutableCollection<Pair<Int, Int>>): Int {
        fun hike(pos: Pair<Int, Int>) {
            val cur = input[pos.second][pos.first].digitToInt()
            val directions = listOf(Pair(0, -1), Pair(1, 0), Pair(0, 1), Pair(-1, 0))
            for (direction in directions) {
                try {
                    if (input[pos.second + direction.second][pos.first + direction.first].digitToInt() == cur + 1) {
                        hike(Pair(pos.first + direction.first, pos.second + direction.second))
                    }
                } catch (_: IndexOutOfBoundsException) {}
            }
            if(cur == 9) ends.add(Pair(pos.first, pos.second))
        }

        var total = 0

        input.forEachIndexed { i, row ->
            row.forEachIndexed { j, num ->
                if(num.digitToInt() == 0) hike(Pair(j, i))
                total += ends.size
                ends.clear()
            }
        }

        return total
    }

    fun part1(input: List<String>): Int {
        val ends = mutableSetOf<Pair<Int, Int>>()
        return solve(input, ends)
    }

    fun part2(input: List<String>): Int {
        val ends = mutableListOf<Pair<Int, Int>>()
        return solve(input, ends)
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36)
    check(part2(testInput) == 81)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
