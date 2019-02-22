package edu.studyup.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.studyup.entity.Event;
import edu.studyup.entity.Location;
import edu.studyup.entity.Student;
import edu.studyup.util.DataStorage;
import edu.studyup.util.StudyUpException;

/*
 *  3 total bugs to find, one in each of the following methods:
 *       updateEventName()
 *       getActiveEvents()
 *       addStudentToEvent()
 * */
class EventServiceImplTest {
	EventServiceImpl eventServiceImpl;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		eventServiceImpl = new EventServiceImpl();
		//Create Student
		Student student = new Student();
		student.setFirstName("John");
		student.setLastName("Doe");
		student.setEmail("JohnDoe@email.com");
		student.setId(1);

		//Create Event1
		Event event = new Event();
		event.setEventID(1);
		event.setDate(new Date());
		event.setName("Event 1");
		Location location = new Location(-122, 37);
		event.setLocation(location);
		List<Student> eventStudents = new ArrayList<>();
		eventStudents.add(student);
		event.setStudents(eventStudents);

		DataStorage.eventData.put(event.getEventID(), event);
	}

	@AfterEach
	void tearDown() throws Exception {
		DataStorage.eventData.clear();
	}

	@Test
	void testUpdateEventName_GoodCase() throws StudyUpException {
		int eventID = 1;
		eventServiceImpl.updateEventName(eventID, "Renamed Event 1");
		assertEquals("Renamed Event 1", DataStorage.eventData.get(eventID).getName());
	}

	@Test
	void testUpdateEvent_WrongEventID_badCase() {
		int eventID = 3;
        assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.updateEventName(eventID, "Renamed Event 3");
		  });
	}


    @Test
    void testUpdateEventName_EmptyName_failCase() {
		int eventID = 1;
		String name = "";
        assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.updateEventName(eventID, name);
		});
	}

    @Test
    void testUpdateEventName_LengthConstraint_failCase() {
        int eventID = 1;
        String name = "01234567890123456789";
        assertEquals(20, name.length());
        assertDoesNotThrow(() -> {
            eventServiceImpl.updateEventName(eventID, name);
        });
        assertEquals("01234567890123456789", DataStorage.eventData.get(eventID).getName());
    }

    @Test
    void testUpdateEventName_LengthConstraint_failCase_2() {
        int eventID = 1;
        String name = "WhenANameHasMoreThenTwentyCharactersItShouldThrow";
        assertThrows(StudyUpException.class, () -> {
            eventServiceImpl.updateEventName(eventID, name);
        });
    }

	@Test
	void testAddStudentToEvent_SizeConstraint_failCase() {
		int eventID = 1;

		Student student2 = new Student();
		student2.setFirstName("Jane");
		student2.setLastName("Doe");
		student2.setEmail("JaneDoe@email.com");
		student2.setId(2);

		Student student3 = new Student();
		student3.setFirstName("Joe");
		student3.setLastName("Doe");
		student3.setEmail("JoeDoe@email.com");
		student3.setId(3);

        assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.addStudentToEvent(student2, eventID);
			eventServiceImpl.addStudentToEvent(student3, eventID);
		});
	}

    @Test
    void testAddStudentToEvent_NullEvent() {
        int eventID = 1;
        eventServiceImpl.deleteEvent(eventID);

        Student student = new Student();
        student.setFirstName("Jane");
        student.setLastName("Doe");
        student.setEmail("JaneDoe@email.com");
        student.setId(2);

        assertThrows(StudyUpException.class, () -> {
            eventServiceImpl.addStudentToEvent(student, eventID);
        });
    }

    @Test
    void testAddStudentToEvent_NullStudents() {
        int eventID = 2;
        Event event = new Event();
        event.setEventID(eventID);
        event.setDate(new Date(0));  // set as epoch (jan 1 1970)
        event.setName("Event 2");
        DataStorage.eventData.put(event.getEventID(), event);

        Student student = new Student();
        student.setFirstName("Jane");
        student.setLastName("Doe");
        student.setEmail("JaneDoe@email.com");
        student.setId(2);

        assertDoesNotThrow(() -> {
            assertEquals(eventServiceImpl.addStudentToEvent(student, eventID).getEventID(), eventID);
        });
    }


    @Test
    void testGetActiveEvents_NoEvents() {
		int eventID = 1;
		eventServiceImpl.deleteEvent(eventID);

        List<Event> activeEvents = eventServiceImpl.getActiveEvents();
		assertEquals(0, activeEvents.size());
	}

    @Test
	void testGetActiveEvents_FilterPastEvents() {
        Event event = new Event();
        event.setEventID(2);
        event.setDate(new Date(0));  // set as epoch (jan 1 1970)

        event.setName("Event 1");
		    DataStorage.eventData.put(event.getEventID(), event);

        Event event2 = new Event();
        event.setEventID(2);
        event.setDate(new Date(Instant.now().toEpochMilli() + 100000));  // set as epoch (jan 1 1970)
        event.setName("Event 2");
        DataStorage.eventData.put(event.getEventID(), event);
    
        List<Event> activeEvents = eventServiceImpl.getActiveEvents();
		    assertEquals(1, activeEvents.size());
	}

    // For coverage...
    @Test
    void testGetPastEvents_NoEvents() {
        int eventID = 1;
        eventServiceImpl.deleteEvent(eventID);

        List<Event> pastEvents = eventServiceImpl.getPastEvents();
        assertEquals(0, pastEvents.size());
    }

    @Test
    void testGetPastEvents_NoPastEvents() {
        int eventID = 1;
        eventServiceImpl.deleteEvent(eventID);

        Event event = new Event();
        event.setEventID(1);
        event.setDate(new Date(Instant.now().toEpochMilli() + 100000)); // How many milliseconds since epoch + 100 seconds
        DataStorage.eventData.put(event.getEventID(), event);


        List<Event> pastEvents = eventServiceImpl.getPastEvents();
        assertEquals(0, pastEvents.size());
    }

    @Test
    void testGetPastEvents_FilterPastEvents() {
        Event event = new Event();
        event.setEventID(1);
        event.setDate(new Date(0)); // How many milliseconds since epoch + 100 seconds

        DataStorage.eventData.put(event.getEventID(), event);

        List<Event> pastEvents = eventServiceImpl.getPastEvents();
        assertEquals(1, pastEvents.size());
    }

    @Test
    void test_deleteEvent_EventExists() {
        int eventID = 1;
        Event event = eventServiceImpl.deleteEvent(eventID);
        assertEquals(event.getEventID(), eventID);
    }

    @Test
    void test_deleteEvent_EventDNE() {
        int eventID = 3;
        Event event = eventServiceImpl.deleteEvent(eventID);
        assertNull(event);
    }
}
