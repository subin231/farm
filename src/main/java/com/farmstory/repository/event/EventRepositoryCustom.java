package com.farmstory.repository.event;

import com.farmstory.dto.EventDTO;
import com.farmstory.entity.Event;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepositoryCustom {

    public void createEvent(EventDTO eventDTO);
    public List<Event> selectEvents();
    public void updateEvent(EventDTO eventDTO);
    public void deleteEvent(int no);

}
