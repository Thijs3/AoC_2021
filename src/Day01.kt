fun main() {
    fun part1(input: List<Int>): Int =
        input.zipWithNext()
            .count {
                it.second > it.first
            }

    fun part2(input: List<Int>): Int =
        input.windowed(3)
            .map { window -> window.sum() }
            .zipWithNext()
            .count {
                it.second > it.first
            }

    val input = readInts("Day01")
    val testInput = readInts("Day01_test")
    check(part1(testInput) == 5)
    println(part1(input))
    println(part2(input))
}
