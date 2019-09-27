package com.socnetw.socnetw.service.Validator;

import com.socnetw.socnetw.exceptions.BadRequestException;
import com.socnetw.socnetw.model.RelationshipStatus;

public class RelationshipStatusDeclineChain extends Chain {
    private RelationshipStatus relationshipStatus;

    public RelationshipStatusDeclineChain(RelationshipStatus status, RelationshipStatus relationshipStatus) {
        this.certainChainStatus = status;
        this.relationshipStatus = relationshipStatus;
    }

    @Override
    public void check() throws BadRequestException {
        if (relationshipStatus != RelationshipStatus.PENDING)
            throw new BadRequestException("You can't decline this request");
    }
}
