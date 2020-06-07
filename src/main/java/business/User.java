package business;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

public class User implements Principal, Comparable {

    private String name = null;
    private String email = null;
    private String password = null;
    private String nib = null;
    private Map<String, Grupo> groups;

    public User(final String name, final String email, final String password, final String nib) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nib = nib;
        this.groups = new HashMap<String, Grupo>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public Boolean checkPassword(final String password) {
        return password.equals(this.password);
    }

    public void addGroup(final Grupo grupo) {
        groups.put(grupo.getName(), grupo);
    }
    
    @Override
    public int compareTo(Object o) {
        if (o.equals(this)) {
            return 0;
        }

        return -1;
    }

}