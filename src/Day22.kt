fun main() {
    fun part1(input: List<String>): Long {
        return input.map { it.toLong() }.sumOf {
            var num = it
            repeat(2000) {
                num = (num*64) xor num
                num %= 16777216
                num = (num/32) xor num
                num %= 16777216
                num = (num*2048) xor num
                num %= 16777216
            }
            return@sumOf num
        }
    }

    fun part2(input: List<String>): Int {
        val profits = mutableMapOf<List<Int>, Int>().withDefault { 0 }
        input.map { it.toLong() }.forEach {
            var num = it
            val prices = mutableListOf((num % 10).toInt())
            repeat(2000) {
                num = (num*64) xor num
                num %= 16777216
                num = (num/32) xor num
                num %= 16777216
                num = (num*2048) xor num
                num %= 16777216
                prices.add((num % 10).toInt())
            }
            val changes = prices.zipWithNext { a, b -> b - a }
            val added = HashSet<List<Int>>()
            changes.windowed(4).forEachIndexed { i, seq ->
                if (added.add(seq)) profits[seq] = profits.getValue(seq) + prices[i+4]
            }
        }

        return profits.maxOf { it.value }
    }

    check(part1(listOf("1", "10", "100", "2024")) == 37327623L)
    check(part2(listOf("1", "2", "3", "2024")) == 23)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day22")
    part1(input).println()
    part2(input).println()
}
