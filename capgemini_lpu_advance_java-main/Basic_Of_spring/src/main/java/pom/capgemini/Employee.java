package pom.capgemini;

public class Employee {
    private  int id;
    private  String name;
    private  double salary;


    public Employee() {
            this.id = 0;
            this.name = "Unknown";
            this.salary = 0.0;
        }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }


    public  void  msg(){
        System.out.println("Employee [id=" + id + ", name=" + name + ", salary=" + salary + "]");
    }
}
