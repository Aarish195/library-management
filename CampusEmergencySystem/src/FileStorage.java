
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private static final Path FILE =
            Paths.get("emergencies.txt");

    public static void save(ArrayList<Emergency> emergencies)
            throws IOException {

        List<String> lines = new ArrayList<>();

        for (Emergency e : emergencies) {
            String line = e.getId() + "|" +
                    clean(e.getStudentName()) + "|" +
                    e.getType() + "|" +
                    clean(e.getLocation()) + "|" +
                    clean(e.getDescription()) + "|" +
                    e.getStatus();

            lines.add(line);
        }

        Files.write(FILE, lines, StandardCharsets.UTF_8);
    }

    public static ArrayList<Emergency> load()
            throws IOException {

        ArrayList<Emergency> emergencies = new ArrayList<>();

        if (!Files.exists(FILE)) {
            return emergencies;
        }

        List<String> lines =
                Files.readAllLines(FILE, StandardCharsets.UTF_8);

        for (String line : lines) {
            try {
                String[] p = line.split("\\|", -1);

                if (p.length != 6) {
                    continue;
                }

                int id = Integer.parseInt(p[0]);

                Emergency e;

                switch (p[2]) {
                    case "Medical":
                        e = new MedicalEmergency(
                            id, p[1], p[3], p[4]);
                        break;

                    case "Fire":
                        e = new FireEmergency(
                            id, p[1], p[3], p[4]);
                        break;

                    case "Security":
                        e = new SecurityEmergency(
                            id, p[1], p[3], p[4]);
                        break;

                    case "Infrastructure":
                        e = new InfrastructureEmergency(
                            id, p[1], p[3], p[4]);
                        break;

                    default:
                        continue;
                }

                e.setStatus(p[5]);
                emergencies.add(e);

            } catch (NumberFormatException ex) {
                // Skip a record with an invalid ID.
            }
        }

        return emergencies;
    }

    private static String clean(String value) {
        return value.replace("|", "/")
                    .replace("\n", " ")
                    .replace("\r", " ");
    }
}
