package `2025`

import com.microsoft.z3.ArithExpr
import com.microsoft.z3.Context
import com.microsoft.z3.IntNum
import com.microsoft.z3.IntSort
import com.microsoft.z3.Status
import println
import readInput
import java.util.BitSet

fun main() {
    fun part1(input: List<String>): Int {
        val regex = Regex("""\[(.*)] (.*) \{(.*)}""")
        var sum = 0
        
        for (line in input) {
            var minPresses = Int.MAX_VALUE
            val match = regex.find(line)!!
            
            val lights = BitSet(match.groupValues[1].length)
            val expected = BitSet(match.groupValues[1].length)
            
            match.groupValues[1].forEachIndexed { index, char -> 
                if (char == '#') expected.flip(index)
            }
            
            val buttons = match.groupValues[2].split(' ').map { it.removeSurrounding("(", ")").split(',').map { it.toInt() } }
            for (i in 1..buttons.count()) {
                val combs = buttons.combinations(i)
                
                for (comb in combs) {
                    lights.clear()
                    
                    for (j in comb.flatten()) lights.flip(j)
                    if (lights == expected && comb.size < minPresses) minPresses = comb.size 
                }
            }
            
            sum += minPresses
        }
        
        return sum
    }

    fun part2(input: List<String>): Int {
        val regex = Regex("""\[(.*)] (.*) \{(.*)}""")
        var sum = 0

        for (line in input) {
            val match = regex.find(line)!!
            
            val buttons = match.groupValues[2].split(' ').map { it.removeSurrounding("(", ")").split(',').map { it.toInt() } }
            val joltages = match.groupValues[3].split(',').map { it.toInt() }

            Context().use { ctx ->
                val opt = ctx.mkOptimize()
                val vars = buttons.indices.map { ctx.mkIntConst("x$it")}
                // all variables must be positive ints
                for (v in vars) opt.Assert(ctx.mkGe(v, ctx.mkInt(0)))
                
                // make the equations
                joltages.forEachIndexed { idj, jolt -> 
                    var expr: ArithExpr<IntSort> = ctx.mkInt(0) 
                    buttons.forEachIndexed { idb, but -> 
                        if (idj in but) expr = ctx.mkAdd(expr, vars[idb])
                    }
                    opt.Assert(ctx.mkEq(expr, ctx.mkInt(jolt)))
                }
                opt.MkMinimize(ctx.mkAdd(*vars.toTypedArray()))
                
                // validity check
                if (opt.Check() == Status.SATISFIABLE) {
                    for (v in vars) sum += (opt.model.eval(v, false) as IntNum).int
                }
            }
        }

        return sum
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("2025/Day10_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 33)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("2025/Day10")
    part1(input).println()
    part2(input).println()
}

fun <T> List<T>.combinations(n: Int): List<List<T>> {
    if (n == 0) return listOf(emptyList())
    if (n == 1) return this.map { listOf(it) }

    return this.withIndex().flatMap { (index, item) ->
        this.drop(index + 1).combinations(n - 1).map { listOf(item) + it }
    }
}
