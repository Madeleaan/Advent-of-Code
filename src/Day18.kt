fun main() {
    fun findPath(walls: List<Coords>, height: Int, width: Int): Int? {
        val directions = listOf(Coords(0, -1), Coords(1, 0), Coords(0, 1), Coords(-1, 0))
        val visited = mutableSetOf(Coords(0, 0))
        val q = ArrayDeque<List<Coords>>()
        q.addLast(listOf(Coords(0, 0)))
        while (q.isNotEmpty()) {
            val cur = q.removeFirst()
            val pos = cur.last()
            visited.add(pos)
            if (pos.first < 0 || pos.first >= width || pos.second < 0 || pos.second >= height || walls.contains(pos)) continue
            if (pos == Coords(width-1, height-1)) {
                return cur.size-1
            }
            for (dir in directions) {
                val new = pos + dir
                if(!visited.contains(new) && q.all { it.last() != new }) {
                    val list = cur.toMutableList()
                    list.add(new)
                    q.addLast(list)
                }
            }
        }
        return null
    }

    fun part1(input: List<String>, width: Int, height: Int, stopAt: Int): Int {
        val walls = mutableListOf<Coords>()
        for (i in 0..<stopAt) {
            val split = input[i].split(",").map { it.toInt() }
            walls.add(Coords(split.first(), split.last()))
        }

        val steps = findPath(walls, width, height)
        return steps ?: 0
    }

    fun part2(input: List<String>, width: Int, height: Int, stopAt: Int): String {
        val walls = mutableListOf<Coords>()
        for (i in 0..<stopAt) {
            val split = input[i].split(",").map { it.toInt() }
            walls.add(Coords(split.first(), split.last()))
        }
        for (i in 0..input.lastIndex-stopAt) {
            val split = input[stopAt+i].split(",").map { it.toInt() }
            walls.add(Coords(split.first(), split.last()))
            if (findPath(walls, width, height) == null) return input[stopAt+i]
        }

        return ""
    }

    val testInput = readInput("Day18_test")
    check(part1(testInput, 7, 7, 12) == 22)
    check(part2(testInput, 7, 7, 12) == "6,1")

    val input = readInput("Day18")
    part1(input, 71, 71, 1024).println()
    part2(input, 71, 71, 1024).println()
}
