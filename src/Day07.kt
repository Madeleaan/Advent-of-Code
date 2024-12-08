import kotlin.math.pow

fun main() {
    fun solve(input: List<String>, n: Int): String {
        var total = 0.0
        for (line in input) {
            val final = line.split(": ").first().toLong()
            val nums = line.split(": ").last().split(" ").map { it.toLong() }
            for(i in 0..<n.toDouble().pow(nums.lastIndex).toInt()) {
                val bits = i.toString(n).padStart(nums.lastIndex, '0')

                var num = nums.first()
                for (j in bits.indices) {
                    when (bits[j]) {
                        '0' -> num += nums[j + 1]
                        '1' -> num *= nums[j + 1]
                        '2' -> num = "${num}${nums[j+1]}".toLong()
                    }
                }
                if (num == final) {
                    total += final
                    break
                }
            }
        }
        return total.toBigDecimal().toPlainString()
    }

    fun part1(input: List<String>): String {
        return solve(input, 2)
    }

    fun part2(input: List<String>): String {
        return solve(input, 3)
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == "3749.0")
    check(part2(testInput) == "11387.0")

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
