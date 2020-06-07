package business.GroupRequests;

import business.User;

/**
 * Contrato para comportamento de objectos do tipo GroupRequestInterface
 * 
 * @author grupo 5
 * @version 1
 * 
 */
public interface GroupRequestInterface {

    /**
     * Estado do pedido
     */
    public enum Status {
        PROCESSING, ACCEPTED, REFUSED, CANCELED
    }

    /**
     * Acception of the request
     * 
     * @return Boolean - true if was accepted, false otherwise
     */
    public boolean accept(User user);

    /**
     * Refuse the request
     * 
     * @return Boolean - true if was refuse, false otherwise
     */
    public boolean refuse(User user);

    /**
     * Cancel the request
     * 
     * @return Boolean - true if was refuse, false otherwise
     */
    public boolean cancel(User user);

    /**
     * Returns the status of the request
     * 
     * @return Status
     */
    public Status getStatus();

    /**
     * Check if the request is refused
     * 
     * @return boolean
     */
    public boolean isRefused();

    /**
     * Check if the request is accepted
     * 
     * @return boolean
     */
    public boolean isAccepted();

    /**
     * Gets the user who initiated the request
     * 
     * @return boolean
     */
    public User getFrom();

    /**
     * Check if the request is processing
     * 
     * @return boolean
     */
    public boolean isProcessing();
}