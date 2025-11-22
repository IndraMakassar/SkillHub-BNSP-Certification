package com.indramakassar.entity;

import java.time.LocalDateTime;

public class Class {
    private int classId;
    private String className;
    private String description;
    private String instructor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Class() {
    }

    public Class(int classId, String className, String description, String instructor) {
        this.classId = classId;
        this.className = className;
        this.description = description;
        this.instructor = instructor;
    }

    public Class(String className, String description, String instructor) {
        this.className = className;
        this.description = description;
        this.instructor = instructor;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Class{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", description='" + description + '\'' +
                ", instructor='" + instructor + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
