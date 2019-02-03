package edu.studyup.entity

import java.util.Date

/**
 *
 * The Event class holds all attributes related to an event. There are few
 * restrictions applied on the attributes:
 *
 * @name The length of event name has to be less than(<) 20 characters.
 * @students There could at most be `3 students` in an event.
 * @date Event with past dates can not be created or updated.
 * @author shvz
 */
class Event {

    var eventID: Int = 0
    var name: String? = null
    var location: Location? = null
    var students: List<Student>? = null
    var date: Date? = null

}
