package org.apache.camel.learn;

public class StudentResult {
    String name;
    String firstName;

    String examName;
    int grade;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getExamName() {
        return examName;
    }
    public void setExamName(String examName) {
        this.examName = examName;
    }
    public int getGrade() {
        return grade;
    }
    public void setGrade(int grade) {
        this.grade = grade;
    }
    
    @Override
    public String toString() {
        return "StudentResult [name=" + name + ", firstName=" + firstName + ", examName=" + examName + ", grade="
                + grade + "]";
    }

}
