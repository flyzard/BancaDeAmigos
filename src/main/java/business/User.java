package business;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a User on the app
 * 
 * @author grupo 5
 * @version 1
 * 
 */
public class User implements Principal, Comparable<User> {

    /**
     * Name of the user
     */
    private String name = null;

    /**
     * Email of the user
     */
    private String email = null;

    /**
     * Password of the user
     */
    private String password = null;

    /**
     * NIB of the user
     */
    private String nib = null;

    /**
     * The groups the user is in
     */
    private final Map<String, Grupo> groups;

    /**
     * Constructor
     * 
     * @param name
     * @param email
     * @param password
     * @param nib
     */
    public User(final String name, final String email, final String password, final String nib) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nib = nib;
        this.groups = new HashMap<String, Grupo>();
    }

    /**
     * Get the name of the user
     * 
     * @return String
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * get the email of the user
     * 
     * @return String
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * get the NIB of the user
     * 
     * @return String
     */
    public String getNIB() {
        return this.nib;
    }

    /**
     * Checks if the given password is the right for this user
     * 
     * @param password
     * @return Boolean
     */
    public Boolean checkPassword(final String password) {
        return password.equals(this.password);
    }

    /**
     * Add a group for this user
     * 
     * @param grupo
     */
    public void addGroup(final Grupo grupo) {
        groups.put(grupo.getName(), grupo);
    }

    /**
     * Compare one User object to another
     */
    @Override
    public int compareTo(User o) {
        if (o.equals(this)) {
            return 0;
        }

        return -1;
    }

}