package com.socnetw.socnetw.service.Validator;

import com.socnetw.socnetw.exceptions.BadRequestException;
import com.socnetw.socnetw.model.RelationshipStatus;

public class RelationshipStatusPendingChain extends Chain {
    private RelationshipStatus relationshipStatus;

    public RelationshipStatusPendingChain(RelationshipStatus status, RelationshipStatus relationshipStatus) {
        this.certainChainStatus = status;
        this.relationshipStatus = relationshipStatus;
    }

    @Override
    public void check() throws BadRequestException {
        if (relationshipStatus != RelationshipStatus.DELETED && relationshipStatus != RelationshipStatus.CANCELED)
            throw new BadRequestException("You can't send request to this person");
    }
}
