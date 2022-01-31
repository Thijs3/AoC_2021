import java.math.BigInteger

fun main() {
    class Packet(private val digits: String) {
        val length = digits.length
        val versionNumber = digits.substring(0, 3).toInt(2)
        val typeID: Int = digits.substring(3, 6).toInt(2)
        val isLiteral = typeID == 4
        val lengthIdIsZero = if (isLiteral) null else (digits[6] == '0')
        fun getChildren(): List<Packet> {
            val children: MutableList<Packet> = mutableListOf()
            if (isLiteral) return children
            var toParse = digits.substring(if (lengthIdIsZero != true) 18 else 22)
            while (toParse.contains('1')) {
                val nextChild = parseNextChild(toParse)
                children.add(Packet(nextChild))
                toParse = toParse.drop(children.last().length)
            }
            return children
        }
        fun parseNextChild(toParse: String): String {
            if (toParse.substring(3, 6).toInt(2) == 4) {
                var curr = 6
                while (toParse[curr] == '1') {
                    curr += 5
                }
                return toParse.substring(0, curr + 5)
            } else {
                if (toParse[6] == '0') {
                    val length = toParse.substring(7, 22).toInt(2)
                    return toParse.substring(0, 22 + length)
                } else {
                    var left = toParse.substring(7, 18).toInt(2)
                    var curr = 18
                    while (left > 0) {
                        curr += parseNextChild(toParse.substring(curr)).length
                        left -= 1
                    }
                    return toParse.substring(0, curr)
                }
            }
        }
        fun getVersionSum(): Int {
            var sum = 0
            for (c in getChildren()) {
                sum += c.getVersionSum()
            }
            return sum + versionNumber
        }
        fun parseLiteral(toParse: String): Long {
            val valueBits = toParse.substring(6).chunked(5)
            val answer = mutableListOf<String>()
            for (chunk in valueBits) {
                answer.add(chunk.drop(1))
                if (chunk[0] == '0') break
            }
            return answer.joinToString("").toLong(2)
        }

        fun getValue(): Long =
            when (digits.substring(3, 6).toInt(2)) {
                0 -> getChildren().sumOf { it.getValue() }
                1 -> getChildren().map { it.getValue() }.reduce { acc, i -> acc * i }
                2 -> getChildren().minOf { it.getValue() }
                3 -> getChildren().maxOf { it.getValue() }
                4 -> parseLiteral(digits)
                5 -> if (getChildren()[0].getValue() > getChildren()[1].getValue()) 1L else 0L
                6 -> if (getChildren()[0].getValue() < getChildren()[1].getValue()) 1L else 0L
                7 -> if (getChildren()[0].getValue() == getChildren()[1].getValue()) 1L else 0L
                else -> throw Error("unknown type: $typeID")
            }
    }
    fun hexToBinary(hex: String): String =
        BigInteger(hex, 16).toString(2).padStart(4, '0')

    fun part1(input: String): Int {
        val binaryString = hexToBinary(input)
        val zeroPadding = binaryString.length + (4 - binaryString.length % 4)
        val parentPacket = Packet(binaryString.padStart(zeroPadding, '0'))
        return parentPacket.getVersionSum()
    }

    fun part2(input: String): Long {
        val binaryString = hexToBinary(input)
        val zeroPadding = binaryString.length + (4 - binaryString.length % 4)
        val parentPacket = Packet(binaryString.padStart(zeroPadding, '0'))
        return parentPacket.getValue()
    }

    val input = readInput("Day16").first()
    val testInput = readInput("Day16_test").first()
    check(part1(testInput) == 5)
    check(part2(testInput) == 9L)
    println(part1(input))
    println(part2(input))
}
