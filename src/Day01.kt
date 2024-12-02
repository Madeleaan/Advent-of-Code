import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()
        for(line in input) {
            val split = line.split("   ").map { it.toInt() }
            list1.add(split[0])
            list2.add(split[1])
        }

        list1.sort()
        list2.sort()

        return list1.zip(list2).sumOf { abs(it.first - it.second) }
    }

    fun part2(input: List<String>): Int {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()
        for(line in input) {
            val split = line.split("   ").map { it.toInt() }
            list1.add(split[0])
            list2.add(split[1])
        }

        return list1.sumOf { it * list2.count { i -> i == it } }
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("Day01_test")) == 11)
    check(part2(readInput("Day01_test")) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
