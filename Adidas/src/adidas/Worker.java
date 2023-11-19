/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adidas;

import java.util.Objects;
import static adidas.PromptUI.UI;

/**
 *
 * @author TAN JA MAN
 */
public class Worker extends User {
    //static Worker currentWorker = Authenticate.getCurrentWorker();
    private String staffID;
    private String staffName;
    private String staffPassword;
    private String staffContact;
    private String gender;
    private String staffEmail;
    private String position;
    
    public Worker(){
        this("", "", "", "","", "", "");
    }
    
    public Worker(String staffID, String staffName, String staffPassword, String staffContact, String gender, String staffEmail, String position){
        this.staffID = staffID;
        this.staffName = staffName;
        this.staffPassword = staffPassword;
        this.staffContact = staffContact;
        this.gender = gender;
        this.staffEmail = staffEmail;
        this.position = position;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffPassword() {
        return staffPassword;
    }

    public void setStaffPassword(String staffPassword) {
        this.staffPassword = staffPassword;
    }

    public String getStaffContact() {
        return staffContact;
    }

    public void setStaffContact(String staffContact) {
        this.staffContact = staffContact;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
