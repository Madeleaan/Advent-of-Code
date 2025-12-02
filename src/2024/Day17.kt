package `2024`

import println
import readInput

fun main() {
    fun part1(input: List<String>): String {
        val regs = mutableListOf(input[0].split(": ").last().toLong(), 0, 0)
        val ins = input[4].split(": ").last().split(",").map { it.toInt() }
        var ip = 0
        val out = mutableListOf<Long>()

        while (true) {
            try {
                val op = ins[ip + 1].toLong()
                val cop = when (op) {
                    in 0..3 -> op
                    in 4..6 -> regs[(op - 4).toInt()]
                    else -> throw error("Invalid op $op!")
                }


                when (ins[ip]) {
                    0 -> regs[0] = regs[0] shr cop.toInt()
                    1 -> regs[1] = regs[1] xor op
                    2 -> regs[1] = cop % 8L
                    3 -> if (regs[0] != 0L) ip = (op - 2).toInt()
                    4 -> regs[1] = regs[1] xor regs[2]
                    5 -> out.add(cop % 8L)
                    6 -> regs[1] = regs[0] shr cop.toInt()
                    7 -> regs[2] = regs[0] shr cop.toInt()
                }
                ip += 2
            } catch (_: IndexOutOfBoundsException) { break }
        }

        return out.joinToString(",")
    }

    fun part2(input: List<String>): Long {
        val ins = input[4].split(": ").last().split(",").map { it.toInt() }
        fun find(target: List<Long>, ans: Long): Long? {
            if(target.isEmpty()) return ans
            for (t in 0L..7L) {
                val regs = mutableListOf((ans shl 3) + t, 0L, 0L)
                var out: Long? = null

                for (ip in 0..ins.lastIndex-2 step 2) {
                    val op = ins[ip + 1].toLong()
                    val cop = when (op) {
                        in 0..3 -> op
                        in 4..6 -> regs[(op - 4).toInt()]
                        else -> throw error("Invalid op $op!")
                    }
                    when (ins[ip]) {
                        1 -> regs[1] = regs[1] xor op
                        2 -> regs[1] = cop % 8L
                        4 -> regs[1] = regs[1] xor regs[2]
                        5 -> out = (cop % 8L)
                        6 -> regs[1] = regs[0] shr cop.toInt()
                        7 -> regs[2] = regs[0] shr cop.toInt()
                    }
                    if(out == target.last()) {
                        val sub = find(target.dropLast(1), regs[0]) ?: continue
                        return sub
                    }
                }
            }
            return null
        }
        return find(ins.map { it.toLong() }, 0) ?: 0
    }


    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day17_test")
    check(part1(testInput) == "4,6,3,5,6,3,5,2,1,0")

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day17")
    part1(input).println()
    part2(input).println()
}
