fun main() {
    fun part1(input: List<Int>): Int =
        input.size

    fun part2(input: List<Int>): Int =
        input.size

    val input = readInts("Day23")
    val testInput = readInts("Day23_test")
    check(part1(testInput) == 6)
    println(part1(input))
    println(part2(input))
}
