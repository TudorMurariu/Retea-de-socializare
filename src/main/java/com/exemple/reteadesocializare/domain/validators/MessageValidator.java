package com.exemple.reteadesocializare.domain.validators;

import com.exemple.reteadesocializare.domain.Message;

public class MessageValidator implements Validator<Message> {
    /**
     * FriendShip's user must have different ids
     */
    @Override
    public void validate(Message entity) throws ValidationException {
        if(entity.getText().contains(";"))
            throw new ValidationException("Textul nu poate contine cacarterul ';'");
    }
}
