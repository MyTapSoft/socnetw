package com.socnetw.socnetw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "RELATIONSHIP")
@Getter
@Setter
@Entity
@ToString
@EqualsAndHashCode
@IdClass(RelationshipPK.class)
public class Relationship implements Serializable {
    @Id
    private Long userIdFrom;
    @Id
    private Long userIdTo;
    @Enumerated(EnumType.STRING)
    private RelationshipStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDate friendsRequestDate;
}
