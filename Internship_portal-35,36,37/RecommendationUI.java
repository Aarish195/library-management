package internshipportal;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class RecommendationUI extends JFrame {

    private JTextField nameField = new JTextField();
    private JTextField branchField = new JTextField();
    private JTextField cgpaField = new JTextField();
    private JTextField skillsField = new JTextField();
    private JTextField interestsField = new JTextField();

    private DefaultTableModel model = new DefaultTableModel(
            new String[]{"Company", "Role", "Type", "Domain", "Score"}, 0);

    private List<Opportunity> opportunities = new ArrayList<>();
    private Skillbased engine = new Skillbased();

    public RecommendationUI() {
        super("Student Skill & Internship Recommendation System");
        loadOpportunities();

        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        form.add(new JLabel("Name:"));
        form.add(nameField);
        form.add(new JLabel("Branch:"));
        form.add(branchField);
        form.add(new JLabel("CGPA:"));
        form.add(cgpaField);
        form.add(new JLabel("Skills (comma separated):"));
        form.add(skillsField);
        form.add(new JLabel("Interests (comma separated):"));
        form.add(interestsField);

        JButton button = new JButton("Get Recommendations");
        button.addActionListener(e -> showRecommendations());

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(button, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(model)), BorderLayout.CENTER);

        setSize(700, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void loadOpportunities() {
        opportunities.add(new Internship("TechNova", "Java Developer", "Software",
                7.0, set("Java", "SQL"), 6, 15000));
        opportunities.add(new Internship("DataSphere", "AI Intern", "AI",
                8.0, set("Python", "SQL"), 3, 12000));
        opportunities.add(new Internship("WebWorks", "Web Developer", "Web",
                7.5, set("JavaScript", "HTML"), 4, 10000));
        opportunities.add(new TrainingProgram("CodeAcademy", "Advanced Java Training",
                "Software", 7.0, set("Java"), 8, 5000));
    }

    private Set<String> set(String... items) {
        return new HashSet<>(Arrays.asList(items));
    }

    private Set<String> toSet(String text) {
        Set<String> result = new HashSet<>();
        for (String part : text.split(",")) {
            if (!part.trim().isEmpty()) {
                result.add(part.trim());
            }
        }
        return result;
    }

    private void showRecommendations() {
        double cgpa;
        try {
            cgpa = Double.parseDouble(cgpaField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid CGPA, e.g. 8.5");
            return;
        }

        Student student = new Student(1, nameField.getText().trim(),
                branchField.getText().trim(), cgpa,
                toSet(skillsField.getText()), toSet(interestsField.getText()),
                new HashSet<String>());

        List<Opportunity> matched = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();

        for (Opportunity opp : opportunities) {
            int score = engine.calculateScore(student, opp);
            if (score > 0) {
                matched.add(opp);
                scores.add(score);
            }
        }

        // sort highest score first
        for (int i = 0; i < matched.size() - 1; i++) {
            int best = i;
            for (int j = i + 1; j < matched.size(); j++) {
                if (scores.get(j) > scores.get(best)) {
                    best = j;
                }
            }
            Collections.swap(matched, i, best);
            Collections.swap(scores, i, best);
        }

        model.setRowCount(0);
        for (int i = 0; i < matched.size(); i++) {
            Opportunity o = matched.get(i);
            model.addRow(new Object[]{o.getCompany(), o.getRole(),
                    o.getType(), o.getDomain(), scores.get(i) + "%"});
        }

        if (matched.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No suitable opportunity found.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecommendationUI().setVisible(true));
    }
}