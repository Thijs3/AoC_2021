fun main() {
    val openingCharacters = listOf('<', '{', '[', '(')
    val closingCharacters = listOf('>', '}', ']', ')')
    fun isCorrect(line: String): Boolean {
        val chars = line.toCharArray().toMutableList()
        val notClosed = mutableListOf<Char>()
        for (char in chars) {
            if (char in openingCharacters) {
                notClosed.add(char)
            } else {
                if (notClosed.last() == openingCharacters[closingCharacters.indexOfFirst { it == char }]) {
                    notClosed.removeLast()
                } else {
                    return false
                }
            }
        }
        return true
    }

    fun determineOutputChars(chars: MutableList<Char>): List<Char> {
        val required = mutableListOf<Char>()
        for (char in chars) {
            if (char in openingCharacters) {
                required.add(closingCharacters[openingCharacters.indexOfFirst { it == char }])
            } else if (char == required.last()) required.removeLast()
        }
        return required.reversed()
    }

    fun calculateScore(line: String): Long =
        determineOutputChars(line.toCharArray().toMutableList())
            .map {
                when (it) {
                    ')' -> 1L
                    ']' -> 2L
                    '}' -> 3L
                    else -> 4L
                }
            }.fold(0L) { acc, it -> acc * 5L + it }

    fun List<Long>.findMedian(): Long = this.sortedBy{it}[this.size / 2]

    fun part1(input: List<String>): Int {
        var score = 0
        for (line in input) {
            val chars = line.toCharArray().toMutableList()
            val notClosed = mutableListOf<Char>()
            for (char in chars) {
                if (char in openingCharacters) {
                    notClosed.add(char)
                } else {
                    if (notClosed.last() == openingCharacters[closingCharacters.indexOfFirst { it == char }]) {
                        notClosed.removeLast()
                    } else {
                        score += when (char) {
                            ')' -> 3
                            ']' -> 57
                            '}' -> 1197
                            else -> 25137
                        }
                        break
                    }
                }
            }
        }
        return score
    }

    fun part2(input: List<String>): Long =
        input.filter { isCorrect(it) }
            .map { calculateScore(it) }.findMedian()

    val input = readInput("Day10")
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)
    println(part1(input))
    println(part2(input))
}
