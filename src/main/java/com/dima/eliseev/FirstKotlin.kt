package com.dima.eliseev

fun main(){
    val name = "Dimas"
    var age = 25
    age = 21

    println("name: $name, and age: $age")

    if (age>12) println("Y")
    else println("no")

    println(if (age == 21) "D" else "B")

    when (age){
        1 -> println("qwe")
        2 -> println("lalala")
        3 -> println("govno")
    }

    for (i in 1..10){
        println("step: $i")
    }

    for (i in 1..20 step 2){
        println("step: $i")
    }

    var i = 0;
    while (i<20){
        i++
    }

    fun sayHello(name: String){
        println(name)
    }

    fun SuM(i: Int, j: Int): Int{
        return i+j
    }

    fun MyNameanAge(name: String, age: Int){
        println("My name is $name and age: $age")
    }

    fun DAY(i: Int) {
        var day = when (i) {
            1 -> "M"
            2 -> "T"
            3 -> "W"
            4 -> "T"
            5 -> "F"
            6 -> "S"
            7 -> "S"
            else -> "NO"
        }
        println(day)
    }

    val My = MyFirstKotlinClass("Dimas", 10)
    My.WhoIam()
}

class MyFirstKotlinClass(var name: String,var age: Int){
    fun WhoIam(){
        println("I'm $name and i'm $age old")
    }
}


