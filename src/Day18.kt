fun main() {
    data class SnailfishNumber(val value: Int, val depth: Int)

    fun readSnailfishNumbers(string: String): MutableList<SnailfishNumber> {
        val snailfishNumbers = mutableListOf<SnailfishNumber>()
        var depth = 0
        for (index in string.indices) {
            when (val char = string[index]) {
                '[' -> depth += 1
                ']' -> depth -= 1
                ',' -> {}
                else -> snailfishNumbers.add(SnailfishNumber(char.toString().toInt(), depth))
            }
        }
        return snailfishNumbers
    }

    fun MutableList<SnailfishNumber>.nest(): MutableList<SnailfishNumber> = map { SnailfishNumber(it.value, it.depth + 1) }.toMutableList()

    fun MutableList<SnailfishNumber>.add(other: MutableList<SnailfishNumber>): MutableList<SnailfishNumber> =
        listOf(this, other).flatten().toMutableList().nest()

    fun MutableList<SnailfishNumber>.hasBomb() = any { it.depth > 4 }

    fun MutableList<SnailfishNumber>.shouldSplit() = any { it.value > 9 }

    fun MutableList<SnailfishNumber>.explode() {
        val index = indexOfFirst { it.depth > 4 }
        val value = this[index].value
        val depth = this[index].depth
        if (index > 0) {
            this[index - 1] = SnailfishNumber(this[index - 1].value + value, this[index - 1].depth)
        }
        if (index < size - 2) {
            this[index + 2] = SnailfishNumber(this[index + 2].value + this[index + 1].value, this[index + 2].depth)
        }
        this[index + 1] = SnailfishNumber(0, depth - 1)
        this.removeAt(index)
    }

    fun MutableList<SnailfishNumber>.split() {
        val index = indexOfFirst { it.value > 9 }
        val value = this[index].value
        val depth = this[index].depth
        this[index] = SnailfishNumber(value / 2, depth + 1)
        this.add(index + 1, SnailfishNumber((value + 1) / 2, depth + 1))
    }

    fun MutableList<SnailfishNumber>.reduce(): MutableList<SnailfishNumber> {
        while (true) {
            when {
                hasBomb() -> explode()
                shouldSplit() -> split()
                else -> return this
            }
        }
    }

    fun MutableList<SnailfishNumber>.magnitude(): Int {
        while (true) {
            val maxDepth = maxOf { it.depth }
            if (maxDepth == 0) {
                return sumOf { it.value }
            }

            for (index in indices) {
                val value = this[index].value
                val depth = this[index].depth
                if (depth == maxDepth) {
                    this[index] = SnailfishNumber(value * 3 + this[index + 1].value * 2, depth - 1)
                    this.removeAt(index + 1)
                    break
                }
            }
        }
    }

    fun part1(input: List<String>): Int =
        input.map { readSnailfishNumbers(it) }
            .reduce { acc, next ->
                val merged = acc.add(next)
                merged.reduce()
            }
            .magnitude()

    fun part2(input: List<String>): Int {
        val numbers = input.map { readSnailfishNumbers(it) }
        val answers = mutableListOf<Int>()
        numbers.forEachIndexed { i, nums1 ->
            numbers.forEachIndexed { j, nums2 ->
                if (i != j) answers.add(nums1.add(nums2).reduce().magnitude())
            }
        }
        return answers.maxOf { it }
    }

    val input = readInput("Day18")
    val testInput = readInput("Day18_test")
    check(part1(testInput) == 4140)
    check(part2(testInput) == 3993)
    println(part1(input))
    println(part2(input))
}
