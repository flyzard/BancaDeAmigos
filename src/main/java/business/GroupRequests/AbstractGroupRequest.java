package business.GroupRequests;

import java.util.Set;
import business.User;

/**
 * Class abstrata para definição de comportamento de objectos to tipo
 * GroupRequestInterface
 * 
 * @author grupo 5
 * @version 1
 * 
 */
public abstract class AbstractGroupRequest implements GroupRequestInterface {
    
    /**
     * The user that makes the request
     */
    private User from = null;

    /**
     * The statues of the request
     */
    private Status status = null;

    /**
     * The user who canceled the request
     */
    private User canceledBy = null;

    /**
     * Constructor
     * 
     * @param from
     * @param voters
     */
    public AbstractGroupRequest(final User from, final Set<User> voters) {
        this.from = from;
        this.status = Status.PROCESSING;
    }

    /**
     * @return Status - The status of the request
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * Acception of the request
     * 
     * @return Boolean - true if was accepted, false otherwise
     */
    public boolean accept(final User user) {
        if (status == Status.PROCESSING) {
            this.status = Status.ACCEPTED;
            return true;
        }

        return false;
    }

    /**
     * Refuse the request
     * 
     * @return Boolean - true if was refuse, false otherwise
     */
    public boolean refuse(final User user) {
        if (status == Status.PROCESSING) {
            this.status = Status.REFUSED;
            return true;
        }

        return false;
    }

    /**
     * Cancel the request
     * 
     * @return Boolean - true if was refuse, false otherwise
     */
    public boolean cancel(final User user) {
        if (status == Status.PROCESSING) {
            this.canceledBy = user;
            this.status = Status.CANCELED;
            return true;
        }

        return false;
    }

    /**
     * Check if the request is refused
     * 
     * @return boolean
     */
    public boolean isRefused() {
        return this.status == Status.REFUSED;
    }

    /**
     * Check if the request is accepted
     * 
     * @return boolean
     */
    public boolean isAccepted() {
        return this.status == Status.ACCEPTED;
    }

    /**
     * Check if the request is processing
     * 
     * @return boolean
     */
    public boolean isProcessing() {
        return this.status == Status.PROCESSING;
    }

    /**
     * Check if the request is canceled
     * 
     * @return boolean
     */
    public boolean isCanceled() {
        return this.status == Status.CANCELED;
    }

    /**
     * Gets the user who canceled the request
     * 
     * @return boolean
     */
    public User getWhoCanlecedIt() {
        return this.canceledBy;
    }

    /**
     * Gets the user who initiated the request
     * 
     * @return boolean
     */
    public User getFrom() {
        return this.from;
    }
}