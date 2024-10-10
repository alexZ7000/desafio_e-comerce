package com.example.comerce.shared.helpers.validators.enums;

public final class EnumValidator {

    public static boolean validateEnum(Enum<?>[] enumValues, String value) {
        for (Enum<?> enumValue : enumValues) {
            if (enumValue.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}

