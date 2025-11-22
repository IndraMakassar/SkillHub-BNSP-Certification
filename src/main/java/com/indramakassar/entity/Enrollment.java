package com.indramakassar.entity;

import java.time.LocalDateTime;

public class Enrollment {
    private int studentId;
    private int classId;
    private String status;
    private LocalDateTime enrolledAt;
    private String studentName;
    private String className;

    // Constructors
    public Enrollment() {
    }

    public Enrollment(int studentId, int classId, String status) {
        this.studentId = studentId;
        this.classId = classId;
        this.status = status;
    }

    public Enrollment(int studentId, int classId) {
        this.studentId = studentId;
        this.classId = classId;
        this.status = "active";
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "studentId=" + studentId +
                ", classId=" + classId +
                ", status='" + status + '\'' +
                ", enrolledAt=" + enrolledAt +
                ", studentName='" + studentName + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
