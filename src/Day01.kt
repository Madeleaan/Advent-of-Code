import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()
        for(line in input) {
            val split = line.split("   ")
            list1.add(split[0].toInt())
            list2.add(split[1].toInt())
        }

        list1.sort()
        list2.sort()

        var sum = 0
        for (i in list1.indices) {
            sum += abs(list1[i] - list2[i])
        }
        return sum

    }

    fun part2(input: List<String>): Int {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()
        for(line in input) {
            val split = line.split("   ")
            list1.add(split[0].toInt())
            list2.add(split[1].toInt())
        }

        var sum = 0
        for (num in list1) {
            sum += num * list2.count { it == num }
        }

        return sum
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("Day01_test")) == 11)
    check(part2(readInput("Day01_test")) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
