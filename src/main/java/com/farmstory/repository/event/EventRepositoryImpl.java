package com.farmstory.repository.event;

import com.farmstory.dto.EventDTO;
import com.farmstory.entity.Event;
import com.farmstory.entity.QEvent;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository

public class EventRepositoryImpl implements EventRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QEvent qEvent = QEvent.event;

    @Override
    public void createEvent(EventDTO eventDTO){
        queryFactory.insert(qEvent).values(qEvent);
    }

    @Override
    public List<Event> selectEvents() {
        return queryFactory
                .select(qEvent)
                .from(qEvent)
                .fetch();
    }

    @Override
    public void updateEvent(EventDTO eventDTO) {
        queryFactory.update(qEvent)
                .where(qEvent.no.eq(eventDTO.getNo()))
                .set(qEvent.title, eventDTO.getTitle())
                .set(qEvent.content, eventDTO.getContent())
                .set(qEvent.Start_date, eventDTO.getStart_date())
                .set(qEvent.End_date, eventDTO.getEnd_date())
                .execute();
    }

    @Override
    public void deleteEvent(int no) {
        queryFactory.delete(qEvent)
                .where(qEvent.no.eq(no))
                .execute();
    }
}
