package com.socnetw.socnetw.service.Validator;


import com.socnetw.socnetw.exceptions.BadRequestException;
import com.socnetw.socnetw.model.RelationshipStatus;

public class RequestAmountChain extends Chain {
    private Long requestAmount;

    public RequestAmountChain(RelationshipStatus status, Long requestAmount) {
        this.certainChainStatus = status;
        this.requestAmount = requestAmount;
    }

    @Override
    public void check() throws BadRequestException {
        if (requestAmount >= 10) throw new BadRequestException("Request Amount Limit");
    }
}
