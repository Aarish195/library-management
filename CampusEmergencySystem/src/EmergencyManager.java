
import java.util.ArrayList;

public class EmergencyManager {
    private ArrayList<Emergency> emergencies = new ArrayList<>();
    private int nextId = 1001;

    public void addEmergency(Emergency emergency) {
        emergencies.add(emergency);

        if (emergency.getId() >= nextId) {
            nextId = emergency.getId() + 1;
        }
    }

    public int generateId() {
        return nextId++;
    }

    public ArrayList<Emergency> getAllEmergencies() {
        return emergencies;
    }

    public Emergency findById(int id) {
        for (Emergency e : emergencies) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public boolean updateStatus(int id, String status) {
        Emergency e = findById(id);

        if (e != null) {
            e.setStatus(status);
            return true;
        }

        return false;
    }

    public Emergency createEmergency(int id, String name,
            String type, String location, String description) {

        switch (type) {
            case "Medical":
                return new MedicalEmergency(
                    id, name, location, description);

            case "Fire":
                return new FireEmergency(
                    id, name, location, description);

            case "Security":
                return new SecurityEmergency(
                    id, name, location, description);

            case "Infrastructure":
                return new InfrastructureEmergency(
                    id, name, location, description);

            default:
                throw new IllegalArgumentException(
                    "Invalid emergency type.");
        }
    }

    public void loadEmergencies(ArrayList<Emergency> saved) {
        emergencies.clear();
        emergencies.addAll(saved);
        nextId = 1001;

        for (Emergency e : emergencies) {
            if (e.getId() >= nextId) {
                nextId = e.getId() + 1;
            }
        }
    }
}
