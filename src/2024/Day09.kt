package `2024`

import println
import readInput

fun main() {
    fun part1(input: String): Long {
        val files = mutableListOf<String>()
        input.map { it.digitToInt() }.forEachIndexed { index, n ->
            if(index % 2 == 0) {
                repeat(n) {
                    files.add("${index/2}")
                }
            } else {
                repeat(n) {
                    files.add(".")
                }
            }
        }
        files.forEachIndexed { index, n ->
            if(n == ".") {
                val last = files.indexOfLast { it != "." }
                if(last < index) return@forEachIndexed
                files[index] = files[last]
                files[last] = "."
            }
        }
        return files.filter { it != "." }.mapIndexed { index, n -> index*n.toLong() }.sum()
    }

    fun part2(input: String): Long {
        var files = mutableListOf<String>()
        input.map { it.digitToInt() }.forEachIndexed { index, n ->
            if (n == 0) return@forEachIndexed
            if (index % 2 == 0) {
                files.add(Char(10_000+index / 2).toString().repeat(n))
            } else {
                files.add(".".repeat(n))
            }
        }
        val checkedNums = mutableSetOf<String>()
        do {
            var swapped = false
            loop@ for (index in 0..files.lastIndex) {
                val n = files[files.lastIndex - index]
                if (!n.all { it == '.' } && !checkedNums.contains(n)) {
                    checkedNums.add(n)
                    for (i in 0..files.lastIndex - index) {
                        val s = files[i]
                        if (s.all { it == '.' }) {
                            if (n.length == s.length) {
                                files[files.lastIndex - index] =
                                    files[i].also { files[i] = files[files.lastIndex - index] }
                                swapped = true
                                break@loop
                            } else if (s.length > n.length) {
                                files[i] = files[files.lastIndex - index]
                                files.add(i + 1, ".".repeat(s.length - n.length))
                                files[files.lastIndex - index] = ".".repeat(n.length)
                                swapped = true
                                break@loop
                            }
                        }
                    }
                }
            }
            val compact = mutableListOf<String>()
            val sb = StringBuilder()
            for(frag in files) {
                if(frag.all { it == '.' }) sb.append(frag)
                else {
                    if(sb.isNotEmpty()) {
                        compact.add(sb.toString())
                        sb.clear()
                    }
                    compact.add(frag)
                }
            }
            if(sb.isNotEmpty()) compact.add(sb.toString())
            files = compact
        } while (swapped)
        var total = 0L
        var index = 0
        files.forEach { n ->
            n.forEach { c ->
                if(c != '.') total += index*(c.code-10_000)
                index++
            }
        }
        return total
    }

    // Test if implementation meets criteria from the description, like:
    check(part1("2333133121414131402") == 1928L)
    check(part2("2333133121414131402") == 2858L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day09").first()
    part1(input).println()
    part2(input).println()
}
