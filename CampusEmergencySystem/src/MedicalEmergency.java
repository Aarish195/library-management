
public class MedicalEmergency extends Emergency
        implements ResponseTeam {

    public MedicalEmergency(int id, String name,
                            String location, String description) {
        super(id, name, location, description);
    }

    @Override
    public String getType() {
        return "Medical";
    }

    @Override
    public String getAssignedTeam() {
        return assignTeam();
    }

    @Override
    public String assignTeam() {
        return "Medical Team";
    }
}

