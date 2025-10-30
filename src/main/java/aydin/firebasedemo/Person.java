package aydin.firebasedemo;

public class Person {
    private String name;
    private int age;
    private String phone; // added phone field

    public Person(String name, int age, String phone) { // added phone to constructor
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    // old constructor kept for compatibility if used elsewhere
    public Person(String name, int age) {
        this(name, age, "");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() { // getter for phone
        return phone;
    }

    public void setPhone(String phone) { // setter for phone
        this.phone = phone;
    }
}