package com.socnetw.socnetw.validation.chainOfResp;

import com.socnetw.socnetw.exceptions.BadRequestException;
import com.socnetw.socnetw.model.RelationshipStatus;
import com.socnetw.socnetw.validation.chainOfResp.Chain;


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
