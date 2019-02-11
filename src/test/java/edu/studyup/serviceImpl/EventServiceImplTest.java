package edu.studyup.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;

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
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.updateEventName(eventID, "Renamed Event 3");
		  });
	}
	

	
	@Test
	void testSetName_EmptyName_failCase() {
		int eventID = 1;
		String name = "";
		Assertions.assertThrows(StudyUpException.class, () -> {
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
		
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.addStudentToEvent(student2, eventID);
			eventServiceImpl.addStudentToEvent(student3, eventID);
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
		event.setDate(new Date(0));
		event.setName("Event 2");
		DataStorage.eventData.put(event.getEventID(), event);
		
		List<Event> activeEvents = eventServiceImpl.getActiveEvents();
		assertEquals(1, activeEvents.size());
		
	}
  
    @Test
    void estUpdateEventName_LengthConstraint_goodCase() throws StudyUpException {
        int eventID = 1;
        String name = "01234567890123456789";
        assertEquals(20, name.length());
        Event event = eventServiceImpl.updateEventName(eventID, name);
        assertEquals("01234567890123456789", event.getName());
    }
      
  	@Test
  	void testUpdateEventName_LengthConstraint_badCase_2() {
  		int eventID = 1;
  		String name = "WhenANameHasMoreThenTwentyCharactersItShouldThrow";
  		Assertions.assertThrows(StudyUpException.class, () -> {
  			eventServiceImpl.updateEventName(eventID, name);
  		});
  	}

    // For coverage...
    @Test
    void testGetPastEvents_NoEvents() {
    	int eventID = 1;
    	eventServiceImpl.deleteEvent(eventID);
    	
    	List<Event> pastEvents = eventServiceImpl.getActiveEvents();
		assertEquals(0, pastEvents.size());
    }
    
    @Test
    void testGetPastEvents_NoPastEvents() {
    	int eventID = 1;
    	eventServiceImpl.deleteEvent(eventID);
    	
		Event event = new Event();
		event.setEventID(1);
		event.setDate(new Date(-1));
    	
    	List<Event> pastEvents = eventServiceImpl.getActiveEvents();
		assertEquals(0, pastEvents.size());
    }

    @Test
    void test_deleteEvent_EventExists() {
    }

    @Test
    void test_deleteEvent_EventDNE() {
    }
}
