package edu.studyup.serviceImpl

import java.util.ArrayList
import java.util.Date

import edu.studyup.entity.Event
import edu.studyup.entity.Student
import edu.studyup.service.EventService
import edu.studyup.util.DataStorage
import edu.studyup.util.DataStorage.eventData
import edu.studyup.util.StudyUpException

class EventServiceImpl : EventService {

    override// Checks if an event date is before today, if no, then add to the active event list.
    val activeEvents: List<Event>
        get() {
            val eventData = eventData
            val activeEvents = ArrayList<Event>()
            for (i in 0 until eventData.size) {
                val ithEvent = eventData[i]
                if (ithEvent != null) {
                    if (!ithEvent.date!!.before(Date())) {
                        activeEvents.add(ithEvent)
                    }
                }
            }
            return activeEvents
        }

    override// Checks if an event date is before today, if no, then add to the active event list.
    val pastEvents: List<Event>
        get() {
            val eventData = eventData
            val pastEvents = ArrayList<Event>()
            for (i in 0 until eventData.size) {
                val ithEvent = eventData[i]
                if (ithEvent != null) {
                    if (ithEvent.date!!.before(Date())) {
                        pastEvents.add(ithEvent)
                    }
                }
            }
            return pastEvents
        }

    override fun updateEvent(event: Event): Event {
        var event: Event? = event
        if (event != null) {
            eventData[event.eventID] = event
            event = eventData[event.eventID]
        }
        return event!!
    }

    @Throws(Exception::class)
    override fun addStudentToEvent(student: Student, eventID: Int): Boolean {
        val event = eventData[eventID] ?: throw StudyUpException("No event found.")
        val presentStudents: MutableList<Student> = event.students as MutableList<Student>
        //Todo Check
        presentStudents.add(student)
        event.students = presentStudents
        eventData[eventID] = event
        return false
    }

    override fun deleteEvent(eventID: Int): Boolean {
        eventData.remove(eventID)
        //Todo error
        return false
    }

}
