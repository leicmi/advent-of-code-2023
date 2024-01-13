// https://adventofcode.com/2023/day/4
fun main() {
    data class Card(val id: String, val numbers: List<Int>, val winningNubmers: Set<Int>) {
        val matches: List<Int> = numbers.filter { it in winningNubmers }

        val points: Int = matches.fold(0) { acc: Int, i: Int ->
            if (acc == 0) 1 else acc * 2
        }

        var instances = 1 // one original card
    }

    fun createCard(line: String): Card {
        val (cardID, allNumbers) = line.split(":")
        val (numbersText, winningNumbersText) = allNumbers.split("|")
        val numbers = numbersText.split(" ")
            .map(String::trim)
            .filterNot(String::isEmpty)
            .map(String::toInt)
        val winningNumbers = winningNumbersText.split(" ")
            .map(String::trim)
            .filterNot(String::isEmpty)
            .map(String::toInt)
            .toSet()
        return Card(cardID, numbers, winningNumbers)
    }

    fun part1(input: List<String>): Int = input.map(::createCard).sumOf(Card::points)

    fun part2(input: List<String>): Int {
        val cards = input.map(::createCard)
        cards.forEachIndexed { i, card ->
            for (j in 1..card.matches.count()) {
                cards[i + j].instances += card.instances
            }
        }
        return cards.sumOf(Card::instances)
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println() // 20855
    part2(input).println() // 5489600
}
