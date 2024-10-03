package com.farmstory.controller.Event;

import com.farmstory.dto.EventDTO;
import com.farmstory.entity.Event;
import com.farmstory.repository.event.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/event")
public class EventDataController {

    private final EventRepository eventRepository;

    // 데이터 전송
    @GetMapping("/data")
    public List<EventDTO> getEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(Event::toDTO)
                .collect(Collectors.toList());
    }

    // 이벤트 생성
    @Transactional
    @PostMapping("/create")
    public EventDTO createEvent(@RequestBody EventDTO eventDTO) {
        Event newEvent = eventDTO.toEntity();
        eventRepository.save(newEvent);

        return newEvent.toDTO();
    }

    // 이벤트 업데이트
    @Transactional
    @PutMapping("/update")
    public void updateEvent(@RequestBody EventDTO eventDTO) {
        Event existingEvent = eventRepository.findById((long) eventDTO.getNo())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        existingEvent.setTitle(eventDTO.getTitle());
        existingEvent.setContent(eventDTO.getContent());
        existingEvent.setStart_date(eventDTO.getStart_date());
        existingEvent.setEnd_date(eventDTO.getEnd_date());

    }

    // 이벤트 삭제
    @Transactional
    @DeleteMapping("/delete/{no}")
    public void deleteEvent(@PathVariable int no) {
        eventRepository.deleteById((long) no);
    }
}
