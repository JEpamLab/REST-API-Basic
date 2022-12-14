package com.epam.validator;

import com.epam.exception.IncorrectParameterException;
import com.epam.model.entity.GiftCertificates;
import com.epam.model.entity.Tag;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import static com.epam.exception.ExceptionIncorrectParameterMessageCodes.*;
import static com.epam.exception.ExceptionIncorrectParameterMessageCodes.BAD_GIFT_CERTIFICATE_DURATION;

@UtilityClass
public class TagValidator {
    private final int MAX_LENGTH_NAME = 50;
    private final int MIN_LENGTH_NAME = 2;

    public Tag validateForUpdate(Tag initialGift,
                                 Tag giftForUpdate) throws IncorrectParameterException {
        String name = giftForUpdate.getName();
        validateName(name);
        initialGift.setName(name);




        return initialGift;
    }

    private void validateName(String name) throws IncorrectParameterException {
        if (name == null || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME || StringUtils.isNumeric(name)) {
            throw new IncorrectParameterException(BAD_GIFT_CERTIFICATE_NAME);
        }
    }





}
