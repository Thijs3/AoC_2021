fun main() {
    data class Beacon(val coordinates: MutableList<Int>)
    data class Rotation(val X: List<Int>, val Y: List<Int>, val Z: List<Int>)
    class Scanner(val beacons: MutableList<Beacon>) {
        val alignedBeacons = mutableListOf<Beacon>()
    }

    val rotations: List<Rotation> = listOf(
        Rotation(listOf(1, 0, 0), listOf(0, 1, 0), listOf(0, 0, 1)),
        Rotation(listOf(1, 0, 0), listOf(0, -1, 0), listOf(0, 0, -1)),
        Rotation(listOf(1, 0, 0), listOf(0, 0, 1), listOf(0, -1, 0)),
        Rotation(listOf(1, 0, 0), listOf(0, 0, -1), listOf(0, 1, 0)),
        Rotation(listOf(-1, 0, 0), listOf(0, 1, 0), listOf(0, 0, -1)),
        Rotation(listOf(-1, 0, 0), listOf(0, -1, 0), listOf(0, 0, 1)),
        Rotation(listOf(-1, 0, 0), listOf(0, 0, 1), listOf(0, 1, 0)),
        Rotation(listOf(-1, 0, 0), listOf(0, 0, -1), listOf(0, -1, 0)),
        Rotation(listOf(0, 1, 0), listOf(-1, 0, 0), listOf(0, 0, 1)),
        Rotation(listOf(0, 1, 0), listOf(1, 0, 0), listOf(0, 0, -1)),
        Rotation(listOf(0, 1, 0), listOf(0, 0, -1), listOf(-1, 0, 0)),
        Rotation(listOf(0, 1, 0), listOf(0, 0, 1), listOf(1, 0, 0)),
        Rotation(listOf(0, -1, 0), listOf(1, 0, 0), listOf(0, 0, 1)),
        Rotation(listOf(0, -1, 0), listOf(0, 0, 1), listOf(-1, 0, 0)),
        Rotation(listOf(0, -1, 0), listOf(-1, 0, 0), listOf(0, 0, -1)),
        Rotation(listOf(0, -1, 0), listOf(0, 0, -1), listOf(1, 0, 0)),
        Rotation(listOf(0, 0, 1), listOf(0, 1, 0), listOf(-1, 0, 0)),
        Rotation(listOf(0, 0, 1), listOf(-1, 0, 0), listOf(0, -1, 0)),
        Rotation(listOf(0, 0, 1), listOf(0, -1, 0), listOf(1, 0, 0)),
        Rotation(listOf(0, 0, 1), listOf(1, 0, 0), listOf(0, 1, 0)),
        Rotation(listOf(0, 0, -1), listOf(0, 1, 0), listOf(1, 0, 0)),
        Rotation(listOf(0, 0, -1), listOf(1, 0, 0), listOf(0, -1, 0)),
        Rotation(listOf(0, 0, -1), listOf(0, -1, 0), listOf(-1, 0, 0)),
        Rotation(listOf(0, 0, -1), listOf(-1, 0, 0), listOf(0, 1, 0)),

    )

    fun readScanners(input: List<String>): List<Scanner> {
        val temp = mutableListOf<List<String>>()
        var start = 1
        var end: Int
        for (i in input.withIndex()) {
            if (i.value.isBlank()) {
                end = i.index
                temp.add(input.subList(start, end))
                start = i.index + 2
            }
        }
        temp.add(input.subList(start, input.lastIndex + 1))
        return temp.map { scanner ->
            Scanner(
                scanner.map { beacon ->
                    Beacon(beacon.split(",").map { it.toInt() }.toMutableList())
                }
                    .toMutableList()
            )
        }
    }

    fun Beacon.rotate(rotation: Rotation) {
        val (x, y, z) = coordinates
        coordinates[0] = x * rotation.X[0] + y * rotation.Y[0] + z * rotation.Z[0]
        coordinates[1] = x * rotation.X[1] + y * rotation.Y[1] + z * rotation.Z[1]
        coordinates[2] = x * rotation.X[2] + y * rotation.Y[2] + z * rotation.Z[2]
    }

    fun part1(input: List<String>): Int {
        val scanners = readScanners(input)
        scanners[0].alignedBeacons.addAll(scanners[0].beacons)
        println(scanners[0].beacons)
        println(scanners[0].alignedBeacons)
        return 1
    }

    fun part2(input: List<String>): Int =
        input.size

    val input = readInput("Day19")
    val testInput = readInput("Day19_test")
    val beacons = mutableListOf(Beacon(mutableListOf(1, 2, 3)), Beacon(mutableListOf(4, 5, 6)))
    // check(part1(testInput) == 6)
    println(part1(input))
    println(part2(input))
}

