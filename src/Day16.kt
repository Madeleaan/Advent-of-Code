import java.util.*
import kotlin.collections.ArrayDeque

fun main() {
    fun part1(input: List<String>): Int {
        var startPos = Coords(0, 0)
        var endPos = Coords(0, 0)
        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, col ->
                when (col) {
                    'S' -> startPos = Coords(x, y)
                    'E' -> endPos = Coords(x, y)
                }
            }
        }
        val pq = PriorityQueue<Triple<Int, Coords, Coords>>(compareBy { it.first })
        val seen = mutableSetOf<Pair<Coords, Coords>>()
        pq.add(Triple(0, startPos, Coords(1, 0)))
        seen.add(Pair(startPos, Coords(1, 0)))

        while (pq.isNotEmpty()) {
            val (cost, pos, dir) = pq.poll()
            seen.add(Pair(pos, dir))
            if(pos == endPos) return cost

            val options: List<Triple<Int, Coords, Coords>> = listOf(
                Triple(cost + 1, pos + dir, dir),
                Triple(cost + 1000, pos, Pair(-dir.second, dir.first)),
                Triple(cost + 1000, pos, Pair(dir.second, -dir.first))
            )
            for ((newCost, newPos, newDir) in options) {
                if (input[newPos.second][newPos.first] == '#') continue
                if (seen.contains(Pair(newPos, newDir))) continue
                pq.add(Triple(newCost, newPos, newDir))
            }
        }

        return 0
    }

    fun part2(input: List<String>): Int {
        var startPos = Coords(0, 0)
        var endPos = Coords(0, 0)
        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, col ->
                when (col) {
                    'S' -> startPos = Coords(x, y)
                    'E' -> endPos = Coords(x, y)
                }
            }
        }
        val pq = PriorityQueue<Triple<Int, Coords, Coords>>(compareBy { it.first })
        val lowest = mutableMapOf<Pair<Coords, Coords>, Int>().withDefault { Int.MAX_VALUE }
        val back = mutableMapOf<Pair<Coords, Coords>, MutableSet<Pair<Coords, Coords>>>().withDefault { mutableSetOf() }
        var best = Int.MAX_VALUE
        val ends = mutableSetOf<Pair<Coords, Coords>>()
        pq.add(Triple(0, startPos, Coords(1, 0)))
        lowest[Pair(startPos, Coords(1, 0))] = 0

        while (pq.isNotEmpty()) {
            val (cost, pos, dir) = pq.poll()
            if (cost > lowest.getValue(Pair(pos, dir))) continue
            if(pos == endPos) {
                if (cost > best) break
                best = cost
                ends.add(Pair(pos, dir))
            }

            val options: List<Triple<Int, Coords, Coords>> = listOf(
                Triple(cost + 1, pos + dir, dir),
                Triple(cost + 1000, pos, Pair(-dir.second, dir.first)),
                Triple(cost + 1000, pos, Pair(dir.second, -dir.first))
            )
            for ((newCost, newPos, newDir) in options) {
                if (input[newPos.second][newPos.first] == '#') continue
                val low = lowest.getValue(Pair(newPos, newDir))
                if (newCost > low) continue
                if (newCost < low) {
                    back[Pair(newPos, newDir)] = mutableSetOf()
                    lowest[Pair(newPos, newDir)] = newCost
                }
                back.getValue(Pair(newPos, newDir)).add(Pair(pos, dir))
                pq.add(Triple(newCost, newPos, newDir))
            }
        }

        val states = ArrayDeque<Pair<Coords, Coords>>()
        val seen = mutableSetOf<Pair<Coords, Coords>>()
        ends.forEach {
            states.add(it)
            seen.add(it)
        }
        while (states.isNotEmpty()) {
            val state = states.removeFirst()
            for (last in back.getValue(state)) {
                if(seen.contains(last)) continue
                seen.add(last)
                states.add(last)
            }
        }

        val final = seen.map { it.first }
        return final.toSet().size
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 7036)
    check(part2(testInput) == 45)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}

typealias Coords = Pair<Int, Int>
operator fun Coords.plus(other: Coords) = Coords(this.first + other.first, this.second + other.second)

