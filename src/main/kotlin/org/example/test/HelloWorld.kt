package org.example.test

class HelloWorld {
}

fun main() {
    println("Hello World")
    var test = Test(12, "Test")
    println(test)
}

data class Test(val id: Long, val name: String)