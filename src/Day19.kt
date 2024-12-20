fun main() {
    fun part1(input: List<String>): Int {
        val patterns = input[0].split(", ")
        val reg = Regex("""(${patterns.joinToString("|")})*""")

        return input.drop(2).count { reg.matches(it) }
    }

    fun part2(input: List<String>): Long {
        // THANKS HYPERNEUTRINO
        val patterns = input[0].split(", ")
        val max = patterns.maxByOrNull { it.length }!!.length
        val cache = mutableMapOf<String, Long>()

        fun isPossible(str: String): Long {
            if (str == "") return 1
            if(cache.containsKey(str)) return cache[str]!!
            var count = 0L
            for (i in 0..minOf(str.length, max)) {
                if (patterns.contains(str.take(i))) {
                    count += isPossible(str.drop(i))
                }
            }
            cache[str] = count
            return count
        }

        return input.drop(2).sumOf { isPossible(it) }
    }


    val testInput = readInput("Day19_test")
    check(part1(testInput) == 6)
    check(part2(testInput) == 16L)


    val input = readInput("Day19")
    part1(input).println()
    part2(input).println()
}
