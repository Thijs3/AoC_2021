fun main() {

    class Line(private val segment: Pair<Pair<Int, Int>, Pair<Int, Int>>) {
        val isHorizontal = segment.first.second == segment.second.second
        val isVertical = segment.first.first == segment.second.first

        fun getCoordinates(): List<Pair<Int, Int>> {
            val result = mutableListOf<Pair<Int, Int>>()
            val (fh, lh, fv, lv) =
                listOf(segment.first.first, segment.second.first, segment.first.second, segment.second.second)
            if (isHorizontal) {
                val hRange = if (fh < lh) fh..lh else lh..fh
                for (i in hRange) {
                    result.add(i to fv)
                }
            } else if (isVertical) {
                val vRange = if (fv < lv) fv..lv else lv..fv
                for (i in vRange) {
                    result.add(fh to i)
                }
            } else {
                val segmentLength = kotlin.math.abs(fh - lh)
                val horizontalStart = minOf(fh, lh)
                val verticalStart = if (fh < lh) fv else lv
                val goesUp = if (fh < lh) fv < lv else lv <= fv
                if (goesUp) {
                    for (i in 0..segmentLength) {
                        result.add(horizontalStart + i to (verticalStart + i))
                    }
                } else {
                    for (i in 0..segmentLength) {
                        result.add(horizontalStart + i to (verticalStart - i))
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
                (segment[0].split(",")[0].toInt() to segment[0].split(",")[1].toInt()) to
                    (segment[1].split(",")[0].toInt() to segment[1].split(",")[1].toInt())
            )
        }

    fun solve(lines: List<Line>, excludeDiagonals: Boolean): Int {
        val oceanFloor = MutableList(1000) { MutableList(1000) { 0 } }
        val filteredLines: List<Line> = if (excludeDiagonals) lines.filter { it.isVertical || it.isHorizontal } else lines
        for (line in filteredLines) {
            for (c in line.getCoordinates()) {
                oceanFloor[c.first][c.second] += 1
            }
        }
        return oceanFloor.sumOf {
            it.count { value -> value >= 2 }
        }
    }

    fun part1(input: List<Line>): Int = solve(input, true)

    fun part2(input: List<Line>): Int = solve(input, false)

    val input = readLineSegments("Day05")
    val testInput = readLineSegments("Day05_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 17)
    println(part1(input))
    println(part2(input))
}
