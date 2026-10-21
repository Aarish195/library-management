package internshipportal;

import java.util.Set;

public class Internship extends Opportunity {

    private int durationMonths;
    private double stipend;

    public Internship(String company, String role, String domain,
                      double minCgpa, Set<String> requiredSkills,
                      int durationMonths, double stipend) {
        super(company, role, domain, minCgpa, requiredSkills);
        this.durationMonths = durationMonths;
        this.stipend = stipend;
    }

    public int getDurationMonths() { return durationMonths; }
    public double getStipend() { return stipend; }

    @Override
    public String getType() {
        return "Internship";
    }
}