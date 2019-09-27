package com.socnetw.socnetw.service.Validator;

import com.socnetw.socnetw.exceptions.BadRequestException;
import com.socnetw.socnetw.model.RelationshipStatus;


public abstract class Chain {
    protected RelationshipStatus certainChainStatus;
    private Chain nextChain;

    public Chain setNextChain(Chain nextChain) {
        this.nextChain = nextChain;
        return nextChain;
    }


    public void validate(RelationshipStatus desiredStatus) throws BadRequestException {
        if (this.certainChainStatus == desiredStatus) check();
        if (nextChain != null)
            nextChain.validate(desiredStatus);
    }

    public abstract void check() throws BadRequestException;
}
