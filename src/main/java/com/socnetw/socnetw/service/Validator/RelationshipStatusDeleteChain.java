package com.socnetw.socnetw.service.Validator;

import com.socnetw.socnetw.exceptions.BadRequestException;
import com.socnetw.socnetw.model.RelationshipStatus;


public class RelationshipStatusDeleteChain extends Chain {
    private RelationshipStatus relationshipStatus;

    public RelationshipStatusDeleteChain(RelationshipStatus relationshipStatus, RelationshipStatus status) {
        this.certainChainStatus = status;
        this.relationshipStatus = relationshipStatus;
    }

    @Override
    public void check() throws BadRequestException {
        if (relationshipStatus != RelationshipStatus.ACCEPTED)
            throw new BadRequestException("You can delete only your friend");
    }
}
