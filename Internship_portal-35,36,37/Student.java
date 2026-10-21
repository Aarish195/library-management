package internshipportal;

import java.util.HashSet;
import java.util.Set;

public class Student {

    private int id;
    private String name;
    private String branch;
    private double cgpa;
    private Set<String> skills;
    private Set<String> interests;
    private Set<String> certifications;

    public Student(int id, String name, String branch, double cgpa,
                   Set<String> skills, Set<String> interests,
                   Set<String> certifications) {
        this.id = id;
        this.name = name;
        this.branch = branch;
        this.cgpa = cgpa;
        this.skills = new HashSet<>(skills);
        this.interests = new HashSet<>(interests);
        this.certifications = new HashSet<>(certifications);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getBranch() { return branch; }
    public double getCgpa() { return cgpa; }
    public Set<String> getSkills() { return skills; }
    public Set<String> getInterests() { return interests; }
    public Set<String> getCertifications() { return certifications; }
}