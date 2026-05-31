//here we have a coplex code which violates the SOLID principle 
package com.tcs.talent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Map;

public class TcsEmployeePortal {

    private Connection dbConnection;

    public TcsEmployeePortal() {
        try {
            // Direct coupling to low-level DB driver configuration
            this.dbConnection = DriverManager.getConnection("jdbc:sqlite:tcs_talent.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Violation: Passing a raw map/JSON equivalent instead of an abstraction, mixing parsing logic
    public void processCandidateOnboarding(Map<String, Object> data) {
        try {
            String candidateId = (String) data.get("id");
            String name = (String) data.get("name");
            String roleType = (String) data.get("role_type"); // "Experienced", "Fresher", "Contractor"
            String currentAddress = (String) data.get("current_address");
            String permanentAddress = (String) data.get("permanent_address");

            String bgvStatus;
            
            // Violation 1: Single Responsibility Principle (SRP)
            // Validation rules are hardcoded right inside the core workflow method
            if (currentAddress.equals(permanentAddress)) {
                System.out.println("[ALERT] Candidate " + name + " filled identical addresses. Flagging yellow.");
                bgvStatus = "Yellow";
            } else {
                bgvStatus = "Initiated";
            }

            // Violation 2: Dependency Inversion Principle (DIP)
            // High-level onboarding flow depends directly on a low-level SQL API/database instance
            String sql = "INSERT INTO candidates VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = dbConnection.prepareStatement(sql);
            pstmt.setString(1, candidateId);
            pstmt.setString(2, name);
            pstmt.setString(3, roleType);
            pstmt.setString(4, currentAddress);
            pstmt.setString(5, bgvStatus);
            pstmt.executeUpdate();

            // Violation 3: Open/Closed Principle (OCP)
            // If TCS introduces a new hiring category (e.g., Intern, Consultant), 
            // you must modify this existing, compiled method body.
            if (roleType.equals("Experienced")) {
                double currentCtc = (Double) data.get("current_ctc");
                double salaryPackage = currentCtc * 1.35; // Hardcoded 35% hike logic
                System.out.println("Generating Experienced Lateral Offer for " + name + " with package: " + salaryPackage);
                
                // Violation 4: Tight Coupling
                // Direct dependence on an imaginary notification logic/email vendor inside this processing block
                System.out.println("Sending automated BGV trigger email to external_agency@matrix.com for " + name);
                
            } else if (roleType.equals("Fresher")) {
                double salaryPackage = 400000; // Fixed 4 LPA
                System.out.println("Generating Mass Onboarding Offer for Fresher " + name + " with package: " + salaryPackage);
                System.out.println("Adding " + name + " to the standard ILP training queue batch.");
                
            } else if (roleType.equals("Contractor")) {
                System.out.println("Processing vendor-managed vendor compliance for Contractor: " + name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Violation 5: Single Responsibility Principle (SRP)
    // Why is the portal class responsible for generating analytical report rendering structures?
    public String runReportingAndAnalytics(String formatType) {
        try {
            // Pretend we fetch aggregated group metrics here...
            String queryData = "Yellow: 5, Initiated: 12"; 
            
            if (formatType.equalsIgnoreCase("JSON")) {
                return "{ \"metrics\": \"" + queryData + "\" }";
            } else if (formatType.equalsIgnoreCase("HTML")) {
                return "<table><tr><td>" + queryData + "</td></tr></table>";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

//below we will correct and code will follow the solid principle 
