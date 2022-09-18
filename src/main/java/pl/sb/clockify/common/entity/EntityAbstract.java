package pl.sb.clockify.common.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Getter
@FieldNameConstants
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class EntityAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false)
    @Builder.Default
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false)
    @EqualsAndHashCode.Include
    @Builder.Default
    private final UUID uuid = UUID.randomUUID();

}