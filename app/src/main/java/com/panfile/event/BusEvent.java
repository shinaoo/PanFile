package com.panfile.event;

public class BusEvent {
    private int type;
    private Object data;

    public enum Type{

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

    public BusEvent(int type){
        this.type = type;
    }

    public BusEvent(int type, Object data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
