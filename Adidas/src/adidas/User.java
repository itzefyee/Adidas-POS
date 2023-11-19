/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adidas;


/**
 *
 * @author Efye
 */
public abstract class User {
        //Private data
        protected String name;
        protected String phoneNo;
        protected String gender;

        //Constructors
        public User(){
        }

    public User(String name,String phoneNo, String gender) {
        this.name = name;
        this.phoneNo = phoneNo;
    	this.gender = gender;
    }
    
    //Getter
    public String getName() {
        return name;
    }
    public String getPhoneNo(){
        return phoneNo;
    }
    public String getGender(){
        return gender;
    }

    //Setter
    public void setName(String name){
        this.name = name;
    }
    public void setPhoneNo(String phoneNo){
        this.phoneNo = phoneNo;
    }
}




