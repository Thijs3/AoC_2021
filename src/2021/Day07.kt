import kotlin.math.abs

fun main() {
    fun part1(input: List<Int>): Int {
        val costPerDistance = List(input.maxOf { it }) { 0 }.mapIndexed { index, _ -> index }
        return (1 until (input.maxOf { it })).minOf { index ->
            input.sumOf { costPerDistance[abs(index - it)] } }
    }

    fun part2(input: List<Int>): Int {
        val costPerDistance = List(input.maxOf { it }) { 0 }.mapIndexed { index, _ -> (index * (index + 1)) / 2 }
        val n = input.sum() / input.size
        val range = (n - 1)..(n + 1)
        return range.minOf { index ->
            input.sumOf { costPerDistance[abs(index - it)] } }
    }

    val input = readIntsOneLine("Day07")
    val testInput = readIntsOneLine("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)
    println(part1(input))
    println(part2(input))
}
