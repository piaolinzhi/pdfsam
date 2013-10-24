/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 23/ott/2013
 * Copyright 2013 by Andrea Vacondio (andrea.vacondio@gmail.com).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.pdfsam.ui.support;

import static org.pdfsam.support.RequireUtils.requireNotNull;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

import org.pdfsam.support.validation.Validator;

/**
 * Support class allowing validation of a value of the given type and allowing binding to the state of the validation
 * 
 * @author Andrea Vacondio
 * @param <T>
 *            the type to validate
 */
public class FXValidationSupport<T> {
    private ReadOnlyObjectWrapper<ValidationState> validationState = new ReadOnlyObjectWrapper<>(
            ValidationState.NOT_VALIDATED);
    private Validator<T> validator;

    public FXValidationSupport(Validator<T> validator) {
        requireNotNull(validator, "Validator cannot be null");
        this.validator = validator;
    }

    public void validate(T value) {
        if (validator.isValid(value)) {
            validationState.set(ValidationState.VALID);
        } else {
            validationState.set(ValidationState.INVALID);
        }
    }

    public void makeNotValidated() {
        validationState.set(ValidationState.NOT_VALIDATED);
    }

    public final ReadOnlyObjectProperty<ValidationState> validationStateProperty() {
        return validationState.getReadOnlyProperty();
    }

    /**
     * @return a {@link FXValidationSupport} that consider valid any value fed to it.
     */
    public static <T> FXValidationSupport<T> alwaysValid() {
        return new FXValidationSupport<>(new Validator<T>() {

            public boolean isValid(T input) {
                return true;
            }
        });
    }

    /**
     * Possible validation states
     * 
     * @author Andrea Vacondio
     * 
     */
    public static enum ValidationState {
        VALID,
        INVALID,
        NOT_VALIDATED;
    }
}