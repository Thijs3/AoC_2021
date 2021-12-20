fun main() {

    fun List<String>.findNeighbours(row: Int, col: Int, char: Char): List<Char> =
        listOf(
            if (row > 0 && col > 0) this[row - 1][col - 1] else char,
            if (row > 0) this[row - 1][col] else char,
            if (row > 0 && col < this[0].lastIndex) this[row - 1][col + 1] else char,
            if (col > 0) this[row][col - 1] else char,
            this[row][col],
            if (col < this[0].lastIndex) this[row][col + 1] else char,
            if (row < this.size - 1 && col > 0) this[row + 1][col - 1] else char,
            if (row < this.size - 1) this[row + 1][col] else char,
            if (row < this.size - 1 && col < this[0].lastIndex) this[row + 1][col + 1] else char
        )

    fun List<String>.padWithChar(char: Char): List<String> =
        map { it.padStart(it.length + 1, char).padEnd(it.length + 2, char) }
            .toMutableList()
            .apply {
                add(char.toString().repeat(this[0].length))
                add(0, char.toString().repeat(this[0].length))
            }

    fun List<Char>.enhance(enhancer: String): Char {
        val index = this.joinToString("").toInt(2)
        return enhancer[index]
    }

    fun List<String>.enhance(enhancer: String): List<String> {
        val defaultChar = this[0][0]
        val enhancedImage = mapIndexed { row, string ->
            string.mapIndexed { col, _ ->
                findNeighbours(row, col, defaultChar)
                    .enhance(enhancer)
            }.joinToString("")
        }
        val newDefaultChar = enhancedImage[0][0]
        return enhancedImage.padWithChar(newDefaultChar)
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
        val enhancer = input[0].toBinary()
        val image = input.subList(2, input.size).map { it.toBinary() }
        val paddedImage = image.padWithChar('0').padWithChar('0')
        val enhanced = paddedImage.enhanceTimes(enhancer, times)
        //enhanced.prettyPrint() // sadly does not look like much :(
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
