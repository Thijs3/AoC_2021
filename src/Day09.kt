fun main() {
    fun part1(input: List<Int>): Int =
        input.size

    fun part2(input: List<Int>): Int =
        input.size

    val input = readInts("Day09")
    val testInput = readInts("Day09_test")
    check(part1(testInput) == 6)
    println(part1(input))
    println(part2(input))
}
