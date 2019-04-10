fun main() {

    val graduateStudent = GraduateStudent("Raúl", "López", "Biología")
    val graduateStudent2 = GraduateStudent("Laura", "Jiménez")

    // SIN CONSTRUCTOR
    println("Alumno: ${graduateStudent.lastName}, ${graduateStudent.firstName} ha realizado la tésis de ${graduateStudent.thesis}")

    // CONSTRUCTOR
    println("Alumno: ${graduateStudent2.lastName}, ${graduateStudent2.firstName} ha realizado la tésis de ${graduateStudent2.thesis}")

    val dataclass = Dataclass("hola")
    val nodataclass = Nodataclass("GOLA")

    print(dataclass)
    print(nodataclass)

}