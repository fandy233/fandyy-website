package com.fandy.personalwebsite.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Document(collection = "cats")
public class CatProfile {
    @Id
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private String breed;
    private String gender;
    private String ownerId;
    private String momId;
    private String dadId;
    private String momName;
    private String dadName;
    private Boolean neuteredOrSprayed;
    private Integer vaccination;
    private LocalDate dateOfBirth;
    private Boolean microchip;
    private String certificate; //None, CFA, TICA
    private String price;
    private Boolean forSale;
    private String grade;
    private String color;
    private String contact;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBreed() {
        return breed;
    }

    public String getGender() {
        return gender;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getMomId() {
        return momId;
    }

    public void setMomId(String momId) {
        this.momId = momId;
    }

    public String getDadId() {
        return dadId;
    }

    public void setDadId(String dadId) {
        this.dadId = dadId;
    }

    public String getMomName() {
        return momName;
    }

    public void setMomName(String momName) {
        this.momName = momName;
    }

    public String getDadName() {
        return dadName;
    }

    public void setDadName(String dadName) {
        this.dadName = dadName;
    }

    public Boolean getNeuteredOrSprayed() {
        return neuteredOrSprayed;
    }

    public void setNeuteredOrSprayed(Boolean neuteredOrSprayed) {
        this.neuteredOrSprayed = neuteredOrSprayed;
    }

    public Integer getVaccination() {
        return vaccination;
    }

    public void setVaccination(Integer vaccination) {
        this.vaccination = vaccination;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean getMicrochip() {
        return microchip;
    }

    public void setMicrochip(Boolean microchip) {
        this.microchip = microchip;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getForSale() {
        return forSale;
    }

    public void setForSale(Boolean forSale) {
        this.forSale = forSale;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}

