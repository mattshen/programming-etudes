package learn.youtubetutorial

fun main(args: Array<String>) {

    val numList2 = 1..20

    val times7 = numList2.map { it * 7 }

    println("New Number List: $times7")

}