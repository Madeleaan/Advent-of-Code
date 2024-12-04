fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        for (line in input) { // Horizontal
            total += line.windowed(4).count { it == "XMAS" || it == "SAMX" }
        }
        for (i in input[0].indices) { // Vertical
            var str = ""
            for (j in input) {
                str += j[i]
            }
            total += str.windowed(4).count { it == "XMAS" || it == "SAMX" }
        }
        for (i in input[0].indices) { // / Diagonal top
            var x = i
            var y = 0
            var str = ""
            while (y <= i) {
                str += input[y][x]
                x--
                y++
            }
            total += str.windowed(4).count { it == "XMAS" || it == "SAMX" }
        }
        for (i in input.indices.drop(1).reversed()) { // / Diagonal bottom
            var x = i
            var y = input[0].length-1
            var str = ""
            while (y >= i) {
                str += input[y][x]
                x++
                y--
            }
            total += str.windowed(4).count { it == "XMAS" || it == "SAMX" }
        }
        for (i in input[0].indices.reversed()) { // \ Diagonal top
            var x = i
            var y = 0
            var str = ""
            while (y < input[0].length-i) {
                str += input[y][x]
                x++
                y++
            }
            total += str.windowed(4).count { it == "XMAS" || it == "SAMX" }
        }
        for (i in input[0].indices.reversed().drop(1).reversed()) { // \ Diagonal bottom
            var x = i
            var y = input[0].length-1
            var str = ""
            while (y >= input[0].length-1-i) {
                str += input[y][x]
                x--
                y--
            }
            total += str.windowed(4).count { it == "XMAS" || it == "SAMX" }
        }
        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0
        for (y in input.indices) {
            for (x in input[0].indices) {
                if (input[x][y] == 'A') {
                    try {
                        if(((input[x-1][y-1] == 'M' && input[x+1][y+1] == 'S') || (input[x-1][y-1] == 'S' && input[x+1][y+1] == 'M'))
                            && ((input[x-1][y+1] == 'M' && input[x+1][y-1] == 'S') || (input[x-1][y+1] == 'S' && input[x+1][y-1] == 'M')))
                            total++
                    } catch (e: Exception) {}
                }
            }
        }
        return total
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
