package com.socnetw.socnetw.validation.chainOfResp;


import com.socnetw.socnetw.exceptions.BadRequestException;
import com.socnetw.socnetw.model.RelationshipStatus;
import com.socnetw.socnetw.validation.chainOfResp.Chain;

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
