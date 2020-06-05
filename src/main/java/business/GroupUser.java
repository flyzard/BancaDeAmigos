package business;

public class GroupUser {

    private Grupo grupo = null;
    private double credit = 0;
    private String nib = null;


    public GroupUser(Grupo grupo, String nib, double initialCredit) {
        this.grupo = grupo;
        this.nib = nib;
        this.credit = initialCredit;
    }

    public String getGroupName() {
        return grupo.getName();
    }

}
