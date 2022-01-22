package com.company.vegana.model.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAudit<Entity> implements Serializable {

    /** 생성시간 */
    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false)
    protected LocalDateTime createdAt;

    /** 수정시간 */
    @LastModifiedDate
    @Column(name = "UPDATED_AT", nullable = false)
    protected LocalDateTime updatedAt;

    /** 생성자 */
    @CreatedBy
    @Column(name = "CREATED_NAME")
    protected String createdName = "ANONYMOUS";

    /** 수정자 */
    @LastModifiedBy
    @Column(name = "UPDATED_NAME")
    protected String updatedName = "ANONYMOUS";

    public void update(final Entity entity) {
    }
}
