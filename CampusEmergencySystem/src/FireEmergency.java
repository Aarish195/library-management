

public class FireEmergency extends Emergency
        implements ResponseTeam {

    public FireEmergency(int id, String name,
                         String location, String description) {
        super(id, name, location, description);
    }

    @Override
    public String getType() {
        return "Fire";
    }

    @Override
    public String getAssignedTeam() {
        return assignTeam();
    }

    @Override
    public String assignTeam() {
        return "Fire Safety Team";
    }
}

