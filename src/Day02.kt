import kotlin.math.abs

fun main() {
    fun isValidList(nums: List<Int>): Boolean {
        val asc = nums[0] < nums[1]
        for (i in 0 until nums.size - 1) {
            val dif = abs(nums[i] - nums[i+1])
            if (dif < 1 || dif > 3) {
                return false
            }
            if ((asc && nums[i] > nums[i + 1]) || (!asc && nums[i] < nums[i + 1])) {
                return false
            }
        }
        return true
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        for(line in input) {
            val nums = line.split(" ").map { it.toInt() }
            if (isValidList(nums)) sum++
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for(line in input) {
            val nums = line.split(" ").map { it.toInt() }
            if (!isValidList(nums)) {
                for (num in nums.indices) {
                    if (isValidList(nums.filterIndexed { i, _ -> i != num })) {
                        sum++
                        break
                    }
                }
            } else sum++
        }
        return sum
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
