package com.ppl2jt.sawadikap_java.model;

public class Requests {

    private int idRequest;
    private int idUser;
    private String requestDate;
    private String status;

    public Requests(int idRequest, int idUser, String requestDate, String status) {
        this.idRequest = idRequest;
        this.idUser = idUser;
        this.requestDate = requestDate;
        this.status = status;
    }

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
