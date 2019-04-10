fun main() {

    val perro1 = Perro("Lucas", 12)
    val perro2 = Perro("Perrito", "Husky")
    val perro3 = Perro("Toby", 5, "Husky")

    println("${perro1.nombre} tiene ${perro1.edad} años y es un ${perro1.raza} además su dueño es ${perro1.nombreDueno}")
    println("${perro2.nombre} tiene ${perro2.edad} años y es un ${perro2.raza} además su dueño es ${perro2.nombreDueno}")
    println("${perro3.nombre} tiene ${perro3.edad} años y es un ${perro3.raza} además su dueño es ${perro3.nombreDueno}")


}