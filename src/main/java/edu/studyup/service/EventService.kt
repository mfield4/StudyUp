package edu.studyup.service

import edu.studyup.entity.Event
import edu.studyup.entity.Student

/**
 * `EventService` holds all CRUD services for class [Event]
 *
 * @author Shivani
 */
interface EventService {

    /**
     * @return The list of all active `events`. i.e., the events whose date is
     * in the future.
     */
    val activeEvents: List<Event>

    /**
     * @return The list of all past `events`. i.e., the events with date <
     * today.
     */
    val pastEvents: List<Event>

    /**
     * @param event The specific [Event] to be updated.
     * @return The updated `event`
     */
    fun updateEvent(event: Event): Event

    //	/**
    //	 * @return The list of present {@code events}. i.e., the events with date as
    //	 *         today.
    //	 */
    //	public List<Event> getCurrentEvents();

    /**
     * @param student The [Student] to be added.
     * @param eventID The `eventID` of the specific [Event] to be
     * updated.
     * @return `True`, if the student is added else `False`.
     * @throws Exception
     */
    @Throws(Exception::class)
    fun addStudentToEvent(student: Student, eventID: Int): Boolean

    /**
     * @param eventID The `eventID` of the specific [Event] to delete.
     * @return `True`, if the event exists and is deleted, else `False`.
     */
    fun deleteEvent(eventID: Int): Boolean

}
