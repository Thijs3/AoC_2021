fun main() {
    data class SnailfishNumber(val value: Long, val depth: Int)

    fun String.readSnailfishNumbers(): MutableList<SnailfishNumber> {
        val snailfishNumbers = mutableListOf<SnailfishNumber>()
        var index = 0
        var depth = 0
        while (index < length) {
            when (val char = get(index)) {
                '[' -> depth += 1
                ']' -> depth -= 1
                ',' -> {}
                else -> snailfishNumbers.add(SnailfishNumber(char.toString().toLong(), depth))
            }
            index += 1
        }
        return snailfishNumbers
    }

    fun MutableList<SnailfishNumber>.hasBomb() = any { it.depth >= 5 }

    fun MutableList<SnailfishNumber>.shouldSplit() = any { it.value >= 10 }

    fun MutableList<SnailfishNumber>.nest(): MutableList<SnailfishNumber> = map { SnailfishNumber(it.value, it.depth + 1) }.toMutableList()

    fun MutableList<SnailfishNumber>.add(other: MutableList<SnailfishNumber>): MutableList<SnailfishNumber> =
        listOf(this, other).flatten().toMutableList().nest()

    fun MutableList<SnailfishNumber>.explode() {
        val index = this.indexOfFirst { it.depth >= 5 }
        val bomb = this[index]
        if (index > 0) {
            this[index - 1] = SnailfishNumber(this[index - 1].value + bomb.value, this[index - 1].depth)
        }
        if (index < this.lastIndex - 1) {
            this[index + 2] = SnailfishNumber(this[index + 2].value + this[index + 1].value, this[index + 2].depth)
        }
        this[index+1] = SnailfishNumber(0, bomb.depth - 1)
        this.removeAt(index)
    }

    fun MutableList<SnailfishNumber>.split() {
        val index = this.indexOfFirst { it.value >= 10 }
        val number = this[index].value
        val depth = this[index].depth
        this[index] = SnailfishNumber(number / 2L, depth + 1)
        this.add(index + 1, SnailfishNumber(number / 2 + 1L, depth + 1))
    }

    fun MutableList<SnailfishNumber>.reduce(): MutableList<SnailfishNumber> {
        while (true) {
            when {
                hasBomb() -> explode().also {
                    println("hasBomb")}
                shouldSplit() -> split().also { println("shouldSplit") }
                else -> return this
            }
        }
    }

    fun List<SnailfishNumber>.magnitude(): Long {
        val numbers = toMutableList()
        while (true) {
            val maxDepth = numbers.maxOf { it.depth }
            if (maxDepth == 0) {
                return numbers.sumOf { it.value }
            }

            for (i in numbers.indices) {
                val (value, depth) = numbers[i]
                if (depth == maxDepth) {
                    numbers[i] = SnailfishNumber(value * 3 + numbers[i+1].value * 2, depth - 1)
                    numbers.removeAt(i + 1)
                    break
                }
            }
        }
    }

    fun part1(input: List<String>): Long =
        input.map { it.readSnailfishNumbers().reduce() }
            .reduce { acc, next ->
                val merged = acc.add(next)
                merged.reduce()
            }
            .magnitude()

    fun part2(input: List<String>): Int =
        input.size

    val input = readInput("Day18")
    val testInput = readInput("Day18_test")
    check(part1(testInput) == 4140L)
    println(part1(input))
    println(part2(input))
}

