fun main() {

    fun List<String>.findNeighbours(row: Int, col: Int, char: Char): List<Char> {
        val result = mutableListOf<Char>()
        for (r in (row - 1)..(row + 1)) {
            for (c in (col - 1)..(col + 1)) {
                val new = this.getOrNull(r)?.getOrNull(c)
                result.add(new ?: char)
            }
        }
        return result
    }

    fun List<String>.padWithChar(char: Char): List<String> =
        map { it.padStart(it.length + 1, char).padEnd(it.length + 2, char) }
            .toMutableList()
            .apply {
                add(char.toString().repeat(this[0].length))
                add(0, char.toString().repeat(this[0].length))
            }

    fun List<Char>.enhance(enhancer: String) =
        enhancer[joinToString("").toInt(2)]

    fun List<String>.enhance(enhancer: String): List<String> {
        val defaultChar = this.first().first()
        val paddedImage = this.padWithChar(defaultChar)
        val enhancedImage = paddedImage.mapIndexed { row, string ->
            string.mapIndexed { col, _ ->
                paddedImage.findNeighbours(row, col, defaultChar)
                    .enhance(enhancer)
            }.joinToString("")
        }
        return enhancedImage
    }

    fun List<String>.countLightPixels() = sumOf { string ->
        string.count { it == '1' }
    }

    fun List<String>.enhanceTimes(enhancer: String, times: Int): List<String> =
        (0 until times).fold(this) { acc, _ -> acc.enhance(enhancer) }

    fun List<String>.prettyPrint() = forEach {
        println(
            it.replace('0', '.')
                .replace('1', '#')
        )
    }

    fun String.toBinary() = replace('.', '0').replace('#', '1')

    fun solve(input: List<String>, times: Int): Int {
        val enhancer = input.first().toBinary()
        val image = input.drop(2).map { it.toBinary() }
        val paddedImage = image.padWithChar('0')
        val enhanced = paddedImage.enhanceTimes(enhancer, times)
        // enhanced.prettyPrint() // sadly does not look like much :(
        return enhanced.countLightPixels()
    }
    fun part1(input: List<String>): Int = solve(input, 2)

    fun part2(input: List<String>): Int = solve(input, 50)

    val input = readInput("Day20")
    val testInput = readInput("Day20_test")
    check(part1(testInput) == 35)
    check(part2(testInput) == 3351)
    println(part1(input))
    println(part2(input))
}
