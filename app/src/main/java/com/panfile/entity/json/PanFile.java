package com.panfile.entity.json;

public class PanFile {
    private String name;
    private int fileType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public enum Type{
        directory("directory",0),
        ;
        private String description;
        private int value;
        Type(String description,int value){
            this.description = description;
            this.value = value;
        }
        public int getValue(){
            return value;
        }
        public static Type getByValue(int value){
            for (Type type:values()){
                if (type.getValue() == value)
                    return type;
            }
            return null;
        }
        public String getDescription(){
            return description;
        }
    }
}
