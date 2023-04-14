package directories.model;

public class Update {
    private String newName;
    private String lastName;

    public Update() {}

    public Update(String newName, String lastName) {
        this.newName = newName;
        this.lastName = lastName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
