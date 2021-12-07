import kotlin.math.abs

fun main() {
    fun calculateMinCost(input: List<Int>, costPerDistance: List<Int>): Int =
        (1 until (input.maxOf { it })).minOf { index -> input.sumOf { costPerDistance[abs(index - it)] } }

    fun part1(input: List<Int>): Int {
        val costPerDistance = List(input.maxOf { it }) { 0 }.mapIndexed { index, _ -> index }
        return calculateMinCost(input, costPerDistance)
    }

    fun part2(input: List<Int>): Int {
        val costPerDistance = List(input.maxOf { it }) { 0 }.mapIndexed { index, _ -> (index * (index + 1)) / 2 }
        return calculateMinCost(input, costPerDistance)
    }

    val input = readIntsOneLine("Day07")
    val testInput = readIntsOneLine("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)
    println(part1(input))
    println(part2(input))
}
