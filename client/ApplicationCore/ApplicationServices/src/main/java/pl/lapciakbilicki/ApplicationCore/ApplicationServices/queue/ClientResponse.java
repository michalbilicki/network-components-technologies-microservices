package pl.lapciakbilicki.ApplicationCore.ApplicationServices.queue;

public class ClientResponse {
    private String id;
    private boolean status;

    public ClientResponse() {}

    public ClientResponse(String id, boolean status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClientResponse{" +
                "id='" + id + '\'' +
                ", status=" + status +
                '}';
    }
}
