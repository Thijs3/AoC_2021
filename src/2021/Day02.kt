fun main() {
    fun part1(input: List<Pair<String, Int>>): Int {
        val position = mutableListOf(0, 0)
        input.forEach {
            when (it.first) {
                "forward" -> position[0] += it.second
                "up" -> position[1] -= it.second
                "down" -> position[1] += it.second
            }
        }
        return position[0] * position[1]
    }

    fun part2(input: List<Pair<String, Int>>): Int {
        val position = mutableListOf(0, 0)
        var aim = 0
        input.forEach {
            when (it.first) {
                "forward" -> run {
                    position[0] += it.second
                    position[1] += aim * it.second
                }
                "up" -> aim -= it.second
                "down" -> aim += it.second
            }
        }
        return position[0] * position[1]
    }

    val input = readStrAndInt("Day02")
    val testInput = readStrAndInt("Day02_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 90)
    println(part1(input))
    println(part2(input))
}
