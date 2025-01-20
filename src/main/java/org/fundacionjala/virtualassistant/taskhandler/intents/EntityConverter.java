package org.fundacionjala.virtualassistant.taskhandler.intents;

import org.fundacionjala.virtualassistant.taskhandler.exception.ConverterException;
import org.fundacionjala.virtualassistant.user_intetions.client.response.IntentEntity;
import java.util.List;

public class EntityConverter {
    private EntityConverter() throws ConverterException {
        throw new ConverterException(ConverterException.CONVERTER_CLASS);
    }

    public static EntityArgs convert(List<IntentEntity> list) {
        EntityArgs.EntityArgsBuilder entityBuilder = EntityArgs.builder();
        int i = 0;
        for (IntentEntity entity : list) {
            if (i == 0) {
                entityBuilder.primaryArg(entity);
            } else if (i == 1) {
                entityBuilder.secondaryArg(entity);
                break;
            }
            i++;
        }
        return entityBuilder.build();
    }
}
