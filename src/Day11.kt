fun main() {
    fun solve(input: String, blinks: Int): Long {
        var dict = input.split(" ").groupingBy { it }.eachCount().mapValues { it.value.toLong() }
        repeat(blinks) {
            val newDict = mutableMapOf<Long, Long>()
            dict.forEach { (n, count) ->
                val num = n.toLong()
                if(n == "0") {
                    newDict[1] = newDict.getOrDefault(1, 0) + count
                } else if (n.length % 2 == 0) {
                    newDict[n.dropLast(n.length/2).toLong()] = newDict.getOrDefault(n.dropLast(n.length/2).toLong(), 0) + count
                    newDict[n.drop(n.length/2).toLong()] = newDict.getOrDefault(n.drop(n.length/2).toLong(), 0) + count
                } else {
                    newDict[num*2024] = newDict.getOrDefault(num*2024, 0) + count
                }
            }
            dict = newDict.mapKeys { it.key.toString() }
        }

        var total = 0L
        dict.values.forEach {
            total += it
        }
        return total
    }

    fun part1(input: String): Long {
        return solve(input, 25)
    }

    fun part2(input: String): Long {
        return solve(input, 75)
    }

    // Test if implementation meets criteria from the description, like:
    check(solve("0 1 10 99 999", 1) == 7L)
    check(solve("125 17", 6) == 22L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day11")
    part1(input.first()).println()
    part2(input.first()).println()
}
