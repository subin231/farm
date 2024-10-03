package com.farmstory.controller.Event;

import com.farmstory.repository.event.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@AllArgsConstructor
@Controller
public class EventController {

    private final EventRepository eventRepository;

    @GetMapping("/event/EventList")
    public String showEventListPage() {
        return "event/EventList";
    }

}
