import java.lang.Integer.MAX_VALUE
import kotlin.math.abs

fun main() {
    fun calculateCost(input: List<Int>, costPerDistance: List<Int>): Int {
        var cost: Int = MAX_VALUE
        for (i in 1 until input.maxOf { it }) {
            val newCost = input.sumOf { costPerDistance[abs(i - it)] }
            if (newCost < cost) {
                cost = newCost
            }
        }
        return cost
    }

    fun part1(input: List<Int>): Int {
        val costPerDistance = List(input.maxOf { it }) { 0 }.mapIndexed { index, _ -> index }
        return calculateCost(input, costPerDistance)
    }

    fun part2(input: List<Int>): Int {
        val costPerDistance = List(input.maxOf { it }) { 0 }.mapIndexed { index, _ -> (index * (index + 1)) / 2 }
        return calculateCost(input, costPerDistance)
    }

    val input = readIntsOneLine("Day07")
    val testInput = readIntsOneLine("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)
    println(part1(input))
    println(part2(input))
}
