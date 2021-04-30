package com.example.one_fit_all;



public class CustomerClass {

    private String name;
    private int weight;
    private int feet;
    private int inch;
    private int age;
    //private String gender;


    public CustomerClass(String name, int weight, int age, int feet, int inch) {

        this.name = name;
        this.weight = weight;
        this.age = age;
        this.feet = feet;
        this.inch = inch;
        //this.gender = gender;

    }

    @Override
    public String toString() {
        return "CustomerClass{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", age=" + age +
                ", feet=" + feet +
                ", inch=" + inch +
                '}';
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

   /* public String getuserGender() {
        return userGender;
    }

   public void setuserGender(String gender) {
        this.gender = gender;
    }
*/

    public int getFeet() {
        return feet;
    }

    public void setFeet(int feet) {
        this.feet = feet;
    }

    public int getInch() {
        return inch;
    }

    public void setInch(int inch) {
        this.inch = inch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
