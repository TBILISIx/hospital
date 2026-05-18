package com.solvd.hospital.dao.impl;

// DTO (Data Transfer Object) – carries columns from the big JOIN query. A flat result holder not a model.
public class PatientAdmissionReport {

    private Long patientId;
    private String patientFirstName;
    private String patientLastName;
    private String bloodType;
    private Long admissionId;
    private String admissionReason;
    private String admittedAt;
    private String dischargedAt;
    private String roomNumber;
    private String roomType;
    private String departmentName;
    private String clinicName;
    private String totalAmount;
    private String paidAmount;
    private boolean paid;

    public PatientAdmissionReport() {
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String v) {
        this.patientFirstName = v;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String v) {
        this.patientLastName = v;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String v) {
        this.bloodType = v;
    }

    public Long getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(Long v) {
        this.admissionId = v;
    }

    public String getAdmissionReason() {
        return admissionReason;
    }

    public void setAdmissionReason(String v) {
        this.admissionReason = v;
    }

    public String getAdmittedAt() {
        return admittedAt;
    }

    public void setAdmittedAt(String v) {
        this.admittedAt = v;
    }

    public String getDischargedAt() {
        return dischargedAt;
    }

    public void setDischargedAt(String v) {
        this.dischargedAt = v;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String v) {
        this.roomNumber = v;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String v) {
        this.roomType = v;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String v) {
        this.departmentName = v;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String v) {
        this.clinicName = v;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String v) {
        this.totalAmount = v;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String v) {
        this.paidAmount = v;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "PatientAdmissionReport{" +
                "patient=" + patientFirstName + " " + patientLastName +
                ", bloodType=" + bloodType +
                ", room=" + roomNumber + "(" + roomType + ")" +
                ", dept=" + departmentName +
                ", clinic=" + clinicName +
                ", admitted=" + admittedAt +
                ", discharged=" + dischargedAt +
                ", paid=" + paid + '}';
    }

}
