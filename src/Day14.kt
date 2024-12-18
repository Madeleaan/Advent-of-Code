import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
    fun part1(input: List<String>, width: Int, height: Int): Int {
        val reg = Regex("""-?\d+""")
        val robots = mutableListOf<Robot>()
        input.forEach {
            val nums = reg.findAll(it).map { n -> n.value.toInt() }.toList()
            robots.add(Robot(Pair(nums[0], nums[1]), Pair(nums[2], nums[3])))
        }

        repeat(100) {
            robots.forEach { it.move() }
        }
        val nw = mutableListOf<Robot>()
        val ne = mutableListOf<Robot>()
        val sw = mutableListOf<Robot>()
        val se = mutableListOf<Robot>()

        robots.forEach {
            var modX = it.pos.first % width
            if (modX < 0) modX += width
            var modY = it.pos.second % height
            if(modY < 0) modY += height

            if(modX < width/2 && modY < height/2) nw.add(it)
            else if(modX > width/2 && modY < height/2) ne.add(it)
            else if(modX < width/2 && modY > height/2) sw.add(it)
            else if(modX > width/2 && modY > height/2) se.add(it)
        }

        return nw.size * ne.size * sw.size * se.size
    }

    fun part2(input: List<String>, width: Int, height: Int): Int {
        /* --------------------------------------------------
         * This day's part 2 is very annoying
         * I am not going to make a solution that checks for the tree
         * Because before I saw it I didn't even know what it looked like
         *
         * THIS SOLUTION ONLY WORKS ON WINDOWS
         * IF YOU ARE ON OTHER PLATFORM YOU PROBABLY KNOW HOW TO EDIT THE CODE
         * --------------------------------------------------
         */
        val reg = Regex("""-?\d+""")
        val robots = mutableListOf<Robot>()
        input.forEach {
            val nums = reg.findAll(it).map { n -> n.value.toInt() }.toList()
            robots.add(Robot(Pair(nums[0], nums[1]), Pair(nums[2], nums[3])))
        }

        File("C:\\Temp\\aoc").mkdir()
        repeat(10_000) { index ->
            robots.forEach {
                it.move()
                var modX = it.pos.first % width
                if(modX < 0) modX += width
                var modY = it.pos.second % height
                if(modY < 0) modY += height
                it.pos = Pair(modX, modY)
            }
            val img = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
            robots.forEach {
                img.setRGB(it.pos.first, it.pos.second, 0xff0000)
            }
            ImageIO.write(img, "png", File("C:\\Temp\\aoc\\$index.png"))
        }
        return 0
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day14_test")
    check(part1(testInput, 11, 7) == 12)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day14")
    part1(input, 101, 103).println()
    part2(input, 101, 103).println()
}

class Robot(startPos: Pair<Int, Int>, velocity: Pair<Int, Int>) {
    var pos = startPos
    private val vel = velocity

    fun move() {
        pos = Pair(pos.first + vel.first, pos.second + vel.second)
    }
}
