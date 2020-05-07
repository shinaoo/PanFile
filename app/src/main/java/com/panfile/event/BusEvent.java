package com.panfile.event;

public class BusEvent {
    private Type type;
    private Object data;

    public enum Type{
        UNKNOWN(0),

        ;
        private int value;
        Type(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Type getByValue(int value){
            for (Type type:values()){
                if (type.getValue() == value)
                    return type;
            }
            return null;
        }
    }

    public BusEvent(Type type){
        this.type = type;
    }

    public BusEvent(Type type, Object data) {
        this.type = type;
        this.data = data;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
