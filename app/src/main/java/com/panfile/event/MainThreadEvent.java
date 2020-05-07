package com.panfile.event;

public class MainThreadEvent {
    private BusEvent.Type type;
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

        public static MainThreadEvent.Type getByValue(int value){
            for (MainThreadEvent.Type type:values()){
                if (type.getValue() == value)
                    return type;
            }
            return null;
        }
    }

    public MainThreadEvent(BusEvent.Type type){
        this.type = type;
    }

    public MainThreadEvent(BusEvent.Type type, Object data) {
        this.type = type;
        this.data = data;
    }

    public BusEvent.Type getType() {
        return type;
    }

    public void setType(BusEvent.Type type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}