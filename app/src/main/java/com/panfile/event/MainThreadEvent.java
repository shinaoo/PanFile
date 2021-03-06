package com.panfile.event;

public class MainThreadEvent {
    private Type type;
    private Object data;

    public enum Type{
        UNKNOWN(0),

        ERR_LOGIN(100),
        ERR_GETFILES(101),

        ACT_LOGIN_2_FILES(1000),
        FILES_SHOW_FILES(1001),
        FILES_FILE_CLICK(1002),
        FILES_FILE_LONGCLICK(1003),


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

    public MainThreadEvent(Type type){
        this.type = type;
    }

    public MainThreadEvent(Type type, Object data) {
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
