package my706project;

public class Record {
    String name, value, type;

    public Record(String name, String value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getTheName() {
        return name;
    }

    public String getTheValue() {
        return value;
    }

    public String getTheType(){
        return type;
    }
}

