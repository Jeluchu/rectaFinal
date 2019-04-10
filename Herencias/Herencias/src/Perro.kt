class Perro (val nombre: String, var edad: Int, val raza: String): Animal(false, "ninguno") {

    constructor(nombre: String, raza: String): this(nombre, 0, raza)
    constructor(nombre: String, edad: Int): this(nombre, 0, "nidea")
}