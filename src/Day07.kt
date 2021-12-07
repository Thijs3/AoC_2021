import java.lang.Integer.MAX_VALUE
import kotlin.math.abs

fun main() {

    fun part1(input: List<Int>): Int {
        val sortedInput = input.sorted()
        val sz = input.size
        val mx = sortedInput.maxOf { it }
        var currentIndex = 0
        var cost = sortedInput.sum()
        var newCost = cost
        var smaller: Int
        var larger: Int
        for (i in 1 until mx) {
            while (sortedInput[currentIndex] < i) {
                currentIndex += 1
            }
            smaller = currentIndex
            larger = sz - smaller
            newCost += smaller - larger
            if (newCost < cost) {
                cost = newCost
            }
        }
        return cost
    }

    fun part2(input: List<Int>): Int {
        val mx = input.maxOf { it }
        val costPerDistance = MutableList(mx + 1) { 0 }.mapIndexed { index, _ -> (index * (index + 1)) / 2 }
        var cost: Int = MAX_VALUE
        for (i in 1 until mx) {
            val newCost = input.sumOf { costPerDistance[abs(i - it)] }
            if (newCost < cost) {
                cost = newCost
            }
        }
        println(costPerDistance)
        return cost
    }

    val input = readIntsOneLine("Day07")
    val testInput = readIntsOneLine("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)
    println(part1(input))
    println(part2(input))
}
