package com.socnetw.socnetw.validation.chainOfResp;

import com.socnetw.socnetw.exceptions.BadRequestException;
import com.socnetw.socnetw.model.RelationshipStatus;
import com.socnetw.socnetw.validation.chainOfResp.Chain;

import java.time.LocalDate;

public class RequestDateChain extends Chain {
    private LocalDate requestDate;

    public RequestDateChain(RelationshipStatus status, LocalDate requestDate) {
        this.certainChainStatus = status;
        this.requestDate = requestDate;
    }

    @Override
    public void check() throws BadRequestException {
        requestDate = requestDate.plusDays(3);
        if (requestDate.compareTo(LocalDate.now()) >= 0) throw new BadRequestException("Date Limit");
    }
}
