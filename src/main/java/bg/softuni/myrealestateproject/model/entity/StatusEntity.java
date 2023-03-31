package bg.softuni.myrealestateproject.model.entity;

import bg.softuni.myrealestateproject.model.enums.StatusTypeEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "status")
public class StatusEntity extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusTypeEnum statusType;

    public StatusEntity() {
    }

    public StatusEntity(StatusTypeEnum statusType) {
        this.statusType = statusType;
    }

    public StatusTypeEnum getStatusType() {
        return statusType;
    }

    public StatusEntity setStatusType(StatusTypeEnum statusType) {
        this.statusType = statusType;
        return this;
    }
}
