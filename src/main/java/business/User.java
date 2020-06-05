package business;

import java.security.Principal;
import java.util.Map;

public class User implements Principal {

    private String name = null;
    private String email = null;
    private String password = null;
    private String nib = null;
    private Map<String, Grupo> groups;

    public User(String name, String email, String password, String nib) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nib = nib;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public Boolean checkPassword(String password) {
        return password.equals(this.password);
    }

	// public boolean addGroupUser(GroupUser gUser) {
    //     String groupName = gUser.getGroupName();

    //     if (null == groups.get(groupName)) {
    //         groups.put(groupName, gUser);
    //         return true;
    //     }

    //     return false;
	// }

	public void addGroup(Grupo grupo) {
        groups.put(grupo.getName(), grupo);
	}
}