package com.wjx.common.domain.event;

import com.wjx.common.domain.AggregateRoot;
import com.wjx.common.utils.SnowflakeIdGenerator;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DomainEvent<ID> {
    /**
     * 事件id
     */
    private Long id;

    /**
     * 聚合根id
     */
    private ID arId;

    /**
     * 事件类型
     */
    private DomainEventType type;

    /**
     * 事件产生时间
     */
    private LocalDateTime raisedTime;

    protected DomainEvent(DomainEventType type) {
        this.id = SnowflakeIdGenerator.newSnowflakeId();
        this.type = type;
        this.raisedTime = LocalDateTime.now();
    }

    public void setArInfo(AggregateRoot<ID> ar) {
        this.arId = ar.getId();
    }
}
