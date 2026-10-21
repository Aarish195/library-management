package internshipportal;

import java.util.Set;

public class TrainingProgram extends Opportunity {

    private int durationWeeks;
    private double fee;

    public TrainingProgram(String company, String role, String domain,
                           double minCgpa, Set<String> requiredSkills,
                           int durationWeeks, double fee) {
        super(company, role, domain, minCgpa, requiredSkills);
        this.durationWeeks = durationWeeks;
        this.fee = fee;
    }

    public int getDurationWeeks() { return durationWeeks; }
    public double getFee() { return fee; }

    @Override
    public String getType() {
        return "Training Program";
    }
}