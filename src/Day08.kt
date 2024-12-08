fun main() {
    fun part1(input: List<String>): Int {
        val antiNodes = hashSetOf<Pair<Int, Int>>()
        input.forEachIndexed { i, row ->
            row.forEachIndexed { j, value ->
                if(value != '.') {
                    input.forEachIndexed { y, rowSearch ->
                        rowSearch.forEachIndexed { x, valueSearch ->
                            if(value == valueSearch && y != i && x != j) {
                                antiNodes.add(Pair(2*x - j, 2*y - i))
                            }
                        }
                    }
                }
            }
        }

        return antiNodes.count { it.first in 0..input[0].lastIndex && it.second in 0..input.lastIndex }
    }

    fun part2(input: List<String>): Int {
        val antiNodes = hashSetOf<Pair<Int, Int>>()
        input.forEachIndexed { i, row ->
            row.forEachIndexed { j, value ->
                if(value != '.') {
                    input.forEachIndexed { y, rowSearch ->
                        rowSearch.forEachIndexed { x, valueSearch ->
                            if(value == valueSearch && y != i && x != j) {
                                antiNodes.add(Pair(x, y))
                                antiNodes.add(Pair(j, i))
                                var k = 1
                                while((k+1)*x - k*j in 0..input[0].lastIndex && (k+1)*y - k*i in 0..input.lastIndex) {
                                    antiNodes.add(Pair((k+1)*x - k*j, (k+1)*y - k*i))
                                    k++
                                }
                            }
                        }
                    }
                }
            }
        }

        return antiNodes.size
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    val a = part2(testInput)
    check(a == 34)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
