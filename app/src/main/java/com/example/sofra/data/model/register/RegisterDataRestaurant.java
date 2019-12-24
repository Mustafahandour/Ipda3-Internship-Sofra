
package com.example.sofra.data.model.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterDataRestaurant {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("user")
    @Expose
    private UserDataRestaurant user;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public UserDataRestaurant getUserRestaurant() {
        return user;
    }

    public void setUserRestaurant(UserDataRestaurant user) {
        this.user = user;
    }

}
