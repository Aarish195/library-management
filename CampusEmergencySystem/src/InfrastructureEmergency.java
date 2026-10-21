
public class InfrastructureEmergency extends Emergency
        implements ResponseTeam {

    public InfrastructureEmergency(int id, String name,
                                   String location,
                                   String description) {
        super(id, name, location, description);
    }

    @Override
    public String getType() {
        return "Infrastructure";
    }

    @Override
    public String getAssignedTeam() {
        return assignTeam();
    }

    @Override
    public String assignTeam() {
        return "Maintenance Team";
    }
}

