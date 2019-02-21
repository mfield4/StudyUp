package edu.studyup.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.studyup.entity.Event;
import edu.studyup.entity.Student;
import edu.studyup.service.EventService;
import edu.studyup.util.DataStorage;
import edu.studyup.util.StudyUpException;

import static edu.studyup.util.DataStorage.*;

public class EventServiceImpl implements EventService {

    @Override
    public Event updateEventName(int eventID, String name) throws StudyUpException {
        Event event = eventData.get(eventID);
        if (event == null) {
            throw new StudyUpException("No event found.");
        }
        if (name.length() == 0) {
            throw new StudyUpException("Empty name. Name must be something");
        }

        if (name.length() > 20) {
            throw new StudyUpException("Length too long. Maximum is 20");
        }
        event.setName(name);
        eventData.put(eventID, event);
        event = eventData.get(event.getEventID());
        return event;
    }

    @Override
    public List<Event> getActiveEvents() {
        return eventData.keySet().stream().map(eventData::get).filter(ithEvent -> ithEvent.getDate().after(new Date())).collect(Collectors.toList());
    }

    @Override
    public List<Event> getPastEvents() {
        return eventData.keySet().stream().map(eventData::get).filter(ithEvent -> ithEvent.getDate().before(new Date())).collect(Collectors.toList());
    }

    @Override
    public Event addStudentToEvent(Student student, int eventID) throws StudyUpException {
        Event event = eventData.get(eventID);
        if (event == null) {
            throw new StudyUpException("No event found.");
        }

        List<Student> presentStudents = event.getStudents();

        if (presentStudents == null) {
            presentStudents = new ArrayList<>();
        }

        if (presentStudents.size() == 2) {
            throw new StudyUpException("Already have 2 students in event. Maximum is 2");
        }

        presentStudents.add(student);
        event.setStudents(presentStudents);
        return eventData.put(eventID, event);
    }

    @Override
    public Event deleteEvent(int eventID) {
        return eventData.remove(eventID);
    }

}
