fun main() {

    fun part1(input: List<String>): Int =
        input.size

    fun part2(input: List<String>): Int =
        input.size

    val input = readInput("Day06")
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 3)
    println(part1(input))
    println(part2(input))
}
