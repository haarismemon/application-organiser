/**
 * This class represents a Company that the user has applied to.
 */
public class Company {

    private String name;

    public Company(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
