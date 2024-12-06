fun main() {
    fun part1(input: List<String>): Int {
        var dir = Pair(0, -1)
        val posY = input.indexOfFirst { it.contains('^') }
        val posX = input[posY].indexOf('^')
        var pos = Pair(posX, posY)
        val steps = mutableSetOf <Pair<Int, Int>>()
        while (true) {
            try {
                while (input[pos.second][pos.first] != '#') {
                    steps.add(pos)
                    pos = Pair(pos.first + dir.first, pos.second + dir.second)
                }
                steps.remove(pos)
                pos = Pair(pos.first - dir.first, pos.second - dir.second)
                dir = Pair(-dir.second, dir.first)
            } catch (e: IndexOutOfBoundsException) { return steps.size }
        }
    }

    fun part2(input: List<String>): Int {
        var found = 0
        for (x in input[0].indices) {
            for(y in input.indices) {
                var dir = Pair(0, -1)
                val posY = input.indexOfFirst { it.contains('^') }
                val posX = input[posY].indexOf('^')
                var pos = Pair(posX, posY)
                val steps = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

                if (Pair(x, y) == Pair(posX, posY) || input[y][x] == '#') continue

                val inputModified = input.toMutableList()
                val sb = StringBuilder(inputModified[y])
                sb.setCharAt(x, '#')
                inputModified[y] = sb.toString()

                brute@ while (true) {
                    try {
                        while (inputModified[pos.second][pos.first] != '#') {
                            steps.add(Pair(pos, dir))
                            pos = Pair(pos.first + dir.first, pos.second + dir.second)
                        }
                        steps.remove(Pair(pos, dir))
                        pos = Pair(pos.first - dir.first, pos.second - dir.second)
                        dir = Pair(-dir.second, dir.first)
                        if (steps.contains(Pair(pos, dir))) {
                            found++
                            break@brute
                        }
                    } catch (e: IndexOutOfBoundsException) { break@brute }
                }
            }
        }
        return found
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
