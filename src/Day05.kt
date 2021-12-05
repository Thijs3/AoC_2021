fun main() {

    class Line(private val segment: Pair<Pair<Int, Int>, Pair<Int, Int>>) {
        fun isHorizontal(): Boolean =
            segment.first.second == segment.second.second

        fun isVertical(): Boolean =
            segment.first.first == segment.second.first

        fun isRelevant(): Boolean =
            isVertical() || isHorizontal()

        fun getCoords(): List<Pair<Int, Int>> {
            val result = mutableListOf<Pair<Int, Int>>()
            val (fh, lh, fv, lv) = listOf(segment.first.first, segment.second.first, segment.first.second, segment.second.second)
            if (isHorizontal()) {
                val hRange = if (fh < lh) fh..lh else lh..fh
                for (i in hRange) {
                    result.add(i to fv)
                }
            } else if (isVertical()) {
                val vRange = if (fv < lv) fv..lv else lv..fv
                for (i in vRange) {
                    result.add(fh to i)
                }
            } else {
                val sl = kotlin.math.abs(fh - lh)
                val hStart = minOf(fh, lh)
                val vStart = if (fh < lh) fv else lv
                val goesUp = if (fh < lh) fv < lv else lv <= fv
                if (goesUp) {
                    for (j in 0..sl) {
                        result.add(hStart + j to (vStart + j))
                    }
                } else {
                    for (j in 0..sl) {
                        result.add(hStart + j to (vStart - j))
                    }
                }
            }
            return result.toList()
        }
    }

    fun readLineSegments(name: String) = readInput(name)
        .map { line -> line.split(" -> ") }
        .map { segment ->
            Line(
                (segment[0].split(",")[0].toInt() to segment[0].split(",")[1].toInt()) to (segment[1].split(",")[0].toInt() to segment[1].split(",")[1].toInt())
            )
        }

    class Ocean(private val width: Int) {
        val floor = MutableList(width) { MutableList(width) { 0 } }
        fun countDangerousCells(): Int =
            floor.sumOf { row ->
                row.count { cell -> cell >= 2 }
            }
    }

    fun part1(input: List<Line>): Int {
        val ocean = Ocean(1000)
        val validLines = input.filter { it.isRelevant() }
        for (line in validLines) {
            for (c in line.getCoords()) {
                ocean.floor[c.first][c.second] += 1
            }
        }
        return ocean.countDangerousCells()
    }

    fun part2(input: List<Line>): Int {
        val ocean = Ocean(1000)
        for (line in input) {
            for (c in line.getCoords()) {
                ocean.floor[c.first][c.second] += 1
            }
        }
        return ocean.countDangerousCells()
    }

    val input = readLineSegments("Day05")
    val testInput = readLineSegments("Day05_test")
    check(part1(testInput) == 15)
    println(part1(input))
    println(part2(input))
}
