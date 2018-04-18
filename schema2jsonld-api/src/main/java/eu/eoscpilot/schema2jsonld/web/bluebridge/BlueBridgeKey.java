package eu.eoscpilot.schema2jsonld.web.bluebridge;

import eu.eoscpilot.schema2jsonld.web.common.Key;

public class BlueBridgeKey implements Key {

    public enum KeyType {
        DATASET;

        public static final KeyType values[] = values();

        public static final Boolean isValid(Integer ordinal) {
            if (ordinal == null) return false;
            if (ordinal < 0 || ordinal >= KeyType.values.length) return false;
            return true;
        }

        public static final Boolean isValid(String name) {
            if (name == null || name.isEmpty()) return false;
            for(KeyType k : KeyType.values){
                if(k.toString().toUpperCase().equals(name.toUpperCase())) return true;
            }
            return false;
        }

        public static final KeyType from(String name) {
            if (name == null || name.isEmpty()) return null;
            return KeyType.valueOf(name.toUpperCase());
        }

        public static final KeyType from(Integer ordinal) {
            if (ordinal == null) return null;
            if (ordinal < 0 || ordinal >= KeyType.values.length) throw new RuntimeException("ordinal out of bounds");
            return KeyType.values[ordinal];
        }
    }

    public BlueBridgeKey(){}

    public BlueBridgeKey(KeyType type, String id) {
        this.setType(type);
        this.setId(id);
    }

    private KeyType type;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public KeyType getType() {
        return type;
    }

    public void setType(KeyType type) {
        this.type = type;
    }
}
