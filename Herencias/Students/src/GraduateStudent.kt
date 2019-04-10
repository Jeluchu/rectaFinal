class GraduateStudent (firstName: String, lastName: String, thesis: String) : Student(firstName, lastName, thesis) {

    constructor(firstName: String, lastName: String) : this(firstName, lastName, "suspensa")

}