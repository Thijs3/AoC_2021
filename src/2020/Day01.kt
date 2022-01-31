package `2020`

import readInput

fun main() {
    fun parseInput(input: List<String>) = input.map { it.toInt() }
    fun part1(input: List<String>): Int {
        val numbers = parseInput(input)
        val required = numbers.map { 2020 - it }.toHashSet()
        for (number in numbers) {
            if (number in required) return number * (2020 - number)
        }
        return -1
    }

    fun part2(input: List<String>): Int {
        val numbers = parseInput(input)
        val pairs = numbers.mapIndexed { i1, n1 -> numbers.mapIndexed { i2, n2 -> if (i1 != i2) 2020 - (n1 + n2) to n1 else - 1 to -1} }.flatten()
        val required = pairs.toMap()
        for (number in numbers) {
            if (required[number] != null) return number * required[number]!! * (2020 - required[number]!! - number)
        }
        return -1
    }

    val input = readInput("Day01", "2020")
    val testInput = readInput("Day01_test", "2020")
    check(part1(testInput) == 514579)
    check(part2(testInput) == 241861950)
    println(part1(input))
    println(part2(input))
}
