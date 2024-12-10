package kab.auca.skilllink.response;

public class MessageResponse {

    private String message;
    private String status;

    // Constructor
    public MessageResponse(String message) {
        this.message = message;
    }

    public MessageResponse(String message, String status) {
        this.message = message;
        this.status = status;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
