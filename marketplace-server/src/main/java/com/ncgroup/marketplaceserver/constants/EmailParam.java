package com.ncgroup.marketplaceserver.constants;



import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public class EmailParam {

    private String mess ;
    private String token = generateToken();
    private String link ;



    public void setLink(String link) {
        this.link = link;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public void setToken(String token) { this.token = token; }

    public String getLink() {
        return link;
    }

    public String getMess() {
        return mess;
    }

    public String getToken() {
        return token;
    }


    @Override
    public String toString() {
        return "Mail{" +
                ", link='" + link + '\'' +
                ", token='" + token + '\'' +
                ", message='" + mess + '\'' +
                '}';
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}