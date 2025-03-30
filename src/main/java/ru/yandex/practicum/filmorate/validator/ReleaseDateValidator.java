package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDate, LocalDate> {

    @Override
    public void initialize(ReleaseDate releaseDate) {
        ConstraintValidator.super.initialize(releaseDate);
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        LocalDate validDate = LocalDate.of(1895, 12, 28);
        return date.isEqual(validDate) || date.isAfter(validDate);

    }
}
