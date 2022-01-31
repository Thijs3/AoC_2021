fun main() {
    data class DisplayPuzzle(val examples: List<String>, val answer: List<String>)

    fun readDisplayPuzzles(name: String) = readInput(name)
        .map { line ->
            line.split("|")
        }
        .map { DisplayPuzzle(it[0].split(" "), it[1].split(" ")) }

    fun numberOfCommonLetters(s1: String, examples: List<String>, len: Int): Int {
        val s2 = examples.find { it.length == len }
        val intersection = (s1).toCharArray().toMutableSet()
        intersection.retainAll(s2!!.toCharArray().toMutableSet())
        return intersection.size
    }

    fun solve(puzzle: DisplayPuzzle): Int {
        val result = puzzle.answer.map { digit ->
            when (digit.length) {
                2 -> "1"
                3 -> "7"
                4 -> "4"
                7 -> "8"
                5 -> if (numberOfCommonLetters(digit, puzzle.examples, 4) == 2) "2"
                else if (numberOfCommonLetters(digit, puzzle.examples, 2) == 2) "3"
                else "5"

                else -> if (numberOfCommonLetters(digit, puzzle.examples, 2) == 1) "6"
                else if (numberOfCommonLetters(digit, puzzle.examples, 4) == 4) "9"
                else "0"
            }
        }
        return result.joinToString("").toInt()
    }

    fun part1(input: List<DisplayPuzzle>): Int =
        input.sumOf { it.answer.count { digit -> digit.length in listOf(2, 3, 4, 7) } }

    fun part2(input: List<DisplayPuzzle>): Int =
        input.sumOf { puzzle -> solve(puzzle) }

    val input = readDisplayPuzzles("Day08")
    val testInput = readDisplayPuzzles("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)
    println(part1(input))
    println(part2(input))
}
