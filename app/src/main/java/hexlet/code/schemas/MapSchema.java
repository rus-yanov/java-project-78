package hexlet.code.schemas;

import java.util.Map;
import java.util.function.Predicate;

public final class MapSchema extends BaseSchema {

    public MapSchema() {
        Predicate<Object> isMap = x -> x instanceof Map<?, ?>;
        addPredicate(isMap);
    }

    public MapSchema required() {
        setRequired(true);
        return this;
    }

    public void sizeof(int size) {
        Predicate<Map<?, ?>> requiredSize = x -> x.size() == size;
        addPredicate(requiredSize);
    }

    public void shape(Map<?, BaseSchema> schemas) {
        Predicate<Map<?, ?>> shape = x -> validateValue(x, schemas);
        addPredicate(shape);
    }

    public boolean validateValue(Map<?, ?> data, Map<?, BaseSchema> schemas) {
        for (Map.Entry<?, BaseSchema> schema : schemas.entrySet()) {
            Object key = schema.getKey();
            if (!data.containsKey(key)) {
                return false;
            } else if (!schema.getValue().isValid(data.get(key))) {
                return false;
            }
        }
        return true;
    }
}
