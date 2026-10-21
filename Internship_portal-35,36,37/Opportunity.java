package internshipportal;

import java.util.HashSet;
import java.util.Set;

public abstract class Opportunity {

    private String company;
    private String role;
    private String domain;
    private double minCgpa;
    private Set<String> requiredSkills;

    public Opportunity(String company, String role, String domain,
                       double minCgpa, Set<String> requiredSkills) {
        this.company = company;
        this.role = role;
        this.domain = domain;
        this.minCgpa = minCgpa;
        this.requiredSkills = new HashSet<>(requiredSkills);
    }

    public String getCompany() { return company; }
    public String getRole() { return role; }
    public String getDomain() { return domain; }
    public double getMinCgpa() { return minCgpa; }
    public Set<String> getRequiredSkills() { return requiredSkills; }

    // Each child class must define its own type
    public abstract String getType();
}