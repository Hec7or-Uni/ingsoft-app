package eina.unizar.ingsoftapp;

/**
 *
 */
public class DropDownData {

    private String id;
    private String name;

    public DropDownData(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
