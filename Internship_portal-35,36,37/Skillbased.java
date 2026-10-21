package internshipportal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Skillbased implements Recommendation {

    @Override
    public void recommend(Student student, List<Opportunity> opportunities) {

        System.out.println("\nRecommended Opportunities for " + student.getName());
        System.out.println("----------------------------------------");

        List<Opportunity> matched = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();

        for (Opportunity opp : opportunities) {

            // Step 1: eligibility check
            if (student.getCgpa() < opp.getMinCgpa()) {
                continue;
            }

            // Step 2: score matching skills and interests
            int score = 0;
            for (String skill : student.getSkills()) {
                if (opp.getRequiredSkills().contains(skill)) {
                    score += 20;
                }
            }
            if (student.getInterests().contains(opp.getDomain())) {
                score += 20;
            }

            if (score > 0) {
                matched.add(opp);
                scores.add(score);
            }
        }

        if (matched.isEmpty()) {
            System.out.println("No suitable opportunity found.");
            return;
        }

        // Step 3: sort by highest score first (simple selection sort)
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

        // Step 4: display
        for (int i = 0; i < matched.size(); i++) {
            Opportunity opp = matched.get(i);
            System.out.println(opp.getCompany() + " - " + opp.getRole()
                    + " (" + opp.getType() + ")");
            System.out.println("Domain: " + opp.getDomain());
            System.out.println("Match Score: " + scores.get(i) + "%");
            System.out.println();
        }
    }
    
 // Used by the UI: returns -1 if not eligible, otherwise the score
    public int calculateScore(Student student, Opportunity opp) {
        if (student.getCgpa() < opp.getMinCgpa()) {
            return -1;
        }
        int score = 0;
        for (String skill : student.getSkills()) {
            if (opp.getRequiredSkills().contains(skill)) {
                score += 20;
            }
        }
        if (student.getInterests().contains(opp.getDomain())) {
            score += 20;
        }
        return score;
    }
}