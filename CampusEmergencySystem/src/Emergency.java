
public abstract class Emergency {
    protected int id;
    protected String studentName;
    protected String location;
    protected String description;
    protected String status;

    public Emergency(int id, String studentName,
                     String location, String description) {
        this.id = id;
        this.studentName = studentName;
        this.location = location;
        this.description = description;
        this.status = "Pending";
    }

    public abstract String getType();

    public abstract String getAssignedTeam();

    public int getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

