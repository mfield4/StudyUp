package edu.studyup.serviceImpl

import org.junit.jupiter.api.Assertions.*

import java.util.ArrayList
import java.util.Date

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

import edu.studyup.entity.Event
import edu.studyup.entity.Location
import edu.studyup.entity.Student
import edu.studyup.util.DataStorage
import edu.studyup.util.StudyUpException

internal class EventServiceImplTest {

    private var eventServiceImpl: EventServiceImpl = EventServiceImpl()

    @BeforeEach
    @Throws(Exception::class)
    fun setUp() {
        //Create Student
        val student = Student()
        student.firstName = "John"
        student.lastName = "Doe"
        student.email = "JohnDoe@email.com"
        student.id = 1

        //Create Event1
        val event = Event()
        event.eventID = 1
        event.date = Date()
        event.name = "Event 1"
        val location = Location(-122.0, 37.0)
        event.location = location
        val eventStudents = ArrayList<Student>()
        eventStudents.add(student)
        event.students = eventStudents

        DataStorage.eventData[event.eventID] = event
    }

    @AfterEach
    @Throws(Exception::class)
    fun tearDown() {
        DataStorage.eventData.clear()
    }

    @Test
    fun testUpdateEventName_GoodCase() {
        val event = Event()
        event.eventID = 1
        event.name = "Renamed Event 1"
        eventServiceImpl.updateEvent(event)
        assertEquals("Renamed Event 1", DataStorage.eventData[event.eventID]?.name)
    }

    @Test
    @Disabled
    fun testUpdateEvent_badCase() {
        val event: Event? = null
        Assertions.assertThrows(StudyUpException::class.java) {
            if (event != null) {
                eventServiceImpl.updateEvent(event)
            }
        }
    }

    @Test
    @Disabled
    fun testUpdateEvent_Dis() {
        val event: Event? = null
        Assertions.assertThrows(StudyUpException::class.java) {
            if (event != null) {
                eventServiceImpl.updateEvent(event)
            }
        }
    }

    @Test
    fun testBadCase() {
        assertEquals(DataStorage.eventData.size, 1)
    }

    companion object {

        @BeforeAll
        @Throws(Exception::class)
        fun setUpBeforeClass() {
        }

        @AfterAll
        @Throws(Exception::class)
        fun tearDownAfterClass() {
        }
    }
}
