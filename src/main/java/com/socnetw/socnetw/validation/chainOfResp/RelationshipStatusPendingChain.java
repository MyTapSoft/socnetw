package com.socnetw.socnetw.validation.chainOfResp;

import com.socnetw.socnetw.exceptions.BadRequestException;
import com.socnetw.socnetw.model.RelationshipStatus;
import com.socnetw.socnetw.validation.chainOfResp.Chain;

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
