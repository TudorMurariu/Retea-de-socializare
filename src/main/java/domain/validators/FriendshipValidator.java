package domain.validators;

import domain.FriendShip;


public class FriendshipValidator implements Validator<FriendShip> {

    /**
     * FriendShip's user must have different ids
     */
    @Override
    public void validate(FriendShip entity) throws ValidationException {
        if(entity.getUser1().getId() == entity.getUser2().getId())
            throw new ValidationException("You cannot add yourself as a friend :(, sorry you got that desperate, it's gonna be fine! trust us! just go outside and touch some grass");
    }
}
