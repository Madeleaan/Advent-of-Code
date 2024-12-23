fun main() {

    fun part1(input: List<String>): Int {
        val edges = input.map { it.split("-") }
        val cons = mutableMapOf<String, MutableSet<String>>().withDefault { mutableSetOf() }
        edges.forEach { (a, b) ->
            cons[a] = mutableSetOf(*cons.getValue(a).toTypedArray(), b)
            cons[b] = mutableSetOf(*cons.getValue(b).toTypedArray(), a)
        }
        val groups = mutableSetOf<List<String>>()
        for (x in cons.keys) {
            for (y in cons[x]!!) {
                for (z in cons[y]!!) {
                    if (z != x && x in cons[z]!!) groups.add(listOf(x, y, z).sorted())
                }
            }
        }

        return groups.filter { it.any { t -> t.startsWith('t') } }.size
    }

    fun part2(input: List<String>): String {
        val edges = input.map { it.split("-") }
        val cons = mutableMapOf<String, Set<String>>().withDefault { setOf() }
        edges.forEach { (a, b) ->
            cons[a] = cons.getValue(a) + b
            cons[b] = cons.getValue(b) + a
        }
        val groups = mutableSetOf<List<String>>()

        fun search(node: String, rest: List<String>) {
            val key = rest.sorted()
            if (!groups.add(key)) return
            for (n in cons[node]!!) {
                if (n in rest) continue
                if (!cons[n]!!.containsAll(rest)) continue
                search(n, rest + n)
            }
        }

        cons.keys.forEach {
            search(it, listOf(it))
        }

        return groups.maxBy { it.size }.joinToString(",")
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day23_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == "co,de,ka,ta")

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day23")
    part1(input).println()
    part2(input).println()
}
