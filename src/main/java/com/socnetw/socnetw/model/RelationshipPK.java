package com.socnetw.socnetw.model;

import lombok.*;

import java.io.Serializable;

@Data
public class RelationshipPK implements Serializable {
    private Long userIdFrom;
    private Long userIdTo;
}
