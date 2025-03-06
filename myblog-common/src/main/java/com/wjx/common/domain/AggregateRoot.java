package com.wjx.common.domain;

import com.wjx.common.domain.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class AggregateRoot<ID> {
    /**
     * id 聚合根id
     */
    protected ID id;

    private List<DomainEvent<ID>> events;

    protected AggregateRoot() {
    }

    public AggregateRoot(ID id) {
        this.id = id;
    }

    protected void raiseEvent(DomainEvent<ID> event) {
        event.setArInfo(this);
        allEvents().add(event);
    }

    public void clearEvents() {
        this.events = null;
    }

    private List<DomainEvent<ID>> allEvents() {
        if (this.events == null) {
            this.events = new ArrayList<>();
        }
        return events;
    }
}
