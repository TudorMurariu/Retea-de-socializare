package domain.validators;

import domain.User;

public class UserValidator implements Validator<User> {
    @Override
    public void validate(User entity) throws ValidationException {
        validateFirstName(entity.getFirstName());
        validateLastName(entity.getLastName());
        validateEmail(entity.getEmail());
    }

    /**
     * The first name must be less than 100 characters
     * it mustn't be empty
     * it's first character must be a letter
     */
    private void validateFirstName(String firstName) throws ValidationException {
        if(firstName.length() >= 100)
            throw new ValidationException("First Name too long");
        else if(firstName.isEmpty())
            throw new ValidationException("First Name must not be empty");
        else if(! Character.isAlphabetic(firstName.charAt(0)))
            throw new ValidationException("First letter of the first name must be a letter");
    }

    /**
     * The first name must be less than 100 characters
     * it mustn't be empty
     * it's first character must be a letter
     */
    private void validateLastName(String lastName) throws ValidationException {
        if(lastName.length() >= 100)
            throw new ValidationException("First Name too long");
        else if(lastName.isEmpty())
            throw new ValidationException("First Name must not be empty");
        else if(! Character.isAlphabetic(lastName.charAt(0)))
            throw new ValidationException("First letter of the last name must be a letter");
    }

    /**
     * The first name must be less than 100 characters
     * it mustn't be empty
     * it must contain one and only one @ character
     */
    private void validateEmail(String email) throws ValidationException {
        if(email.length() >= 100)
            throw new ValidationException("First Name too long");
        else if(email.isEmpty())
            throw new ValidationException("First Name must not be empty");
        else if(email.chars().filter(ch -> ch == '@').count() != 1)
            throw new ValidationException("Wrong email");
    }

}
