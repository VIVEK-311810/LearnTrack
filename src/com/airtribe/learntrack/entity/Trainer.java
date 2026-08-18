package com.airtribe.learntrack.entity;

public class Trainer extends Person {
    private String specialization;
    private boolean active;

    public Trainer(int id, String firstName, String lastName, String email, String specialization, boolean active) {
        super(id, firstName, lastName, email);
        this.specialization = specialization;
        this.active = active;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " [" + specialization + "]";
    }
}
