
public class SecurityEmergency extends Emergency
        implements ResponseTeam {

    public SecurityEmergency(int id, String name,
                             String location, String description) {
        super(id, name, location, description);
    }

    @Override
    public String getType() {
        return "Security";
    }

    @Override
    public String getAssignedTeam() {
        return assignTeam();
    }

    @Override
    public String assignTeam() {
        return "Campus Security Team";
    }
}

