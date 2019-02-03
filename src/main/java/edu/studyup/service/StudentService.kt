package edu.studyup.service

import edu.studyup.entity.Student

/**
 * Holds all the CRUD services for [Student] class.
 *
 * @author Shivani
 */
interface StudentService {

    /**
     * Creates the student
     *
     * @param student The [Student] to be added. There are no restriction on a
     * student model.
     * @return The created `Student`.
     */
    fun createStudent(student: Student): Student

    /**
     * Retrieves the student
     *
     * @param studentID Unique Id of the [Student] to retrieve.
     * @return The `Student` with the specified Id.
     */
    fun getStudent(studentID: Int): Student

    /**
     * Deleted the student
     *
     * @param studentID Unique Id of the [Student] to be deleted.
     * @return Nothing.
     */
    fun deleteStudent(studentID: Int)

}
