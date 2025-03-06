package com.wjx.common.domain.entity;

import com.wjx.common.domain.AggregateRoot;
import com.wjx.common.utils.SnowflakeIdGenerator;
import lombok.Data;

/**
 * @author wangjx
 * @date 2025/03/06
 * @description 领域实体类
 */
@Data
public class DomainEntity<ID> {
    /**
     * 实体id
     */
    private Long id;

    /**
     * 聚合根id
     */
    private ID arId;

    protected DomainEntity() {
        this.id = SnowflakeIdGenerator.newSnowflakeId();
    }

    public void setArInfo(AggregateRoot<ID> ar) {
        this.arId = ar.getId();
    }
}
