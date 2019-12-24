package com.example.sofra.data.api;

import com.example.sofra.data.model.category.Category;
import com.example.sofra.data.model.contactUs.ContactUs;
import com.example.sofra.data.model.itemList.ItemList;
import com.example.sofra.data.model.notification.Notification;
import com.example.sofra.data.model.offers.Offers;
import com.example.sofra.data.model.order.Order;
import com.example.sofra.data.model.regions.Region;
import com.example.sofra.data.model.register.ClientCategory;
import com.example.sofra.data.model.register.RegisterRestaurant;
import com.example.sofra.data.model.resetPassword.ResetPassword;
import com.example.sofra.data.model.restaurantList.RestaurantList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @POST("client/sign-up")
    @Multipart
    Call<RegisterRestaurant> getRegisterClient(@Part("name") RequestBody name,
                                               @Part("email") RequestBody email,
                                               @Part("password") RequestBody password,
                                               @Part("password_confirmation") RequestBody passwordConfirmation,
                                               @Part("phone") RequestBody phone,
                                               @Part("region_id") RequestBody regionId,
                                               @Part MultipartBody.Part profileImage);

    @POST("restaurant/sign-up")
    @Multipart
    Call<RegisterRestaurant> getRegisterRestaurant(@Part("name") RequestBody name,
                                                   @Part("email") RequestBody email,
                                                   @Part("password") RequestBody password,
                                                   @Part("password_confirmation") RequestBody passwordConfirmation,
                                                   @Part("phone") RequestBody phone,
                                                   @Part("whatsapp") RequestBody whatsapp,
                                                   @Part("region_id") RequestBody regionId,
                                                   @Part("delivery_cost") RequestBody delivery_cost,
                                                   @Part("minimum_charger") RequestBody minimumCharger,
                                                   @Part MultipartBody.Part profileImage,
                                                   @Part("delivery_time") RequestBody deliveryTime);

    @POST("client/login")

    @FormUrlEncoded
    Call<RegisterRestaurant> getClientLogin(@Field("email") String email,
                                            @Field("password") String password);

    @POST("restaurant/login")
    @FormUrlEncoded
    Call<RegisterRestaurant> getRestaurantLogin(@Field("email") String email,
                                                @Field("password") String password);

    @GET("regions-not-paginated")
    Call<Region> getRegion(@Query("city_id") int cityId);

    @GET("cities-not-paginated")
    Call<Region> getCity();


    @POST("client/reset-password")
    @FormUrlEncoded
    Call<ResetPassword> getClientPasswordCode(@Field("email") String email);


    @POST("client/new-password")
    @FormUrlEncoded
    Call<ResetPassword> getNewClientPassword(@Field("code") String code,
                                             @Field("password") String password,
                                             @Field("password_confirmation") String passwordConfirmation);

    @POST("restaurant/reset-password")
    @FormUrlEncoded
    Call<ResetPassword> getRestaurantPasswordCode(@Field("email") String email);


    @POST("restaurant/new-password")
    @FormUrlEncoded
    Call<ResetPassword> getNewRestaurantPassword(@Field("code") String code,
                                                 @Field("password") String password,
                                                 @Field("password_confirmation") String passwordConfirmation);


    @POST("client/profile")
    @FormUrlEncoded
    Call<RegisterRestaurant> getClientProfile(@Field("api_token") String apiToken);

    @POST("restaurant/profile")
    @FormUrlEncoded
    Call<RegisterRestaurant> getRestaurantProfile(@Field("api_token") String apiToken);

    @GET("client/my-orders")
    Call<Order> getClientOrder(@Query("api_token") String apiToken,
                               @Query("state") String state,
                               @Query("page") int page);

    @GET("restaurant/my-orders")
    Call<Order> getRestaurantOrder(@Query("api_token") String apiToken,
                                   @Query("state") String state,
                                   @Query("page") int page);

    @GET("restaurants")
    Call<RestaurantList> getRestaurantList(@Query("page") int page);

    @GET("restaurants")
    Call<RestaurantList> getRestaurantFilter(@Query("keyword") String keyword,
                                             @Query("city_id") int cityId);

    @GET("categories")
    Call<ClientCategory> getCategory(@Query("restaurant_id") String restaurantId);

    @GET("restaurant/my-categories")
    Call<Category> getRestaurantCategory(@Query("api_token") String api_token,
                                         @Query("page") int page);


    @POST("restaurant/new-category")
    @Multipart
    Call<Category> getAddCategory(@Part("name") RequestBody name,
                                  @Part MultipartBody.Part photo,
                                  @Part("api_token") RequestBody apiToken);

    @POST("restaurant/update-category")
    @Multipart
    Call<Category> editCategory(@Part("name") RequestBody name,
                                  @Part MultipartBody.Part photo,
                                  @Part("api_token") RequestBody apiToken,
                                @Part("category_id") RequestBody categoryId);


    @POST("restaurant/delete-category")
    @FormUrlEncoded
    Call<Category> deleteCategory( @Field("api_token") String apiToken,
                                   @Field("category_id") String categoryId);

    @GET("items")
    Call<ItemList> getItemList(@Query("restaurant_id") int restaurantId,
                               @Query("category_id") int categoryId,
                               @Query("page") int page);

    @GET("restaurant/my-items")
    Call<ItemList> getItem(@Query("api_token") String apiToken,
                           @Query("category_id") int categoryId);

    @POST("restaurant/new-item")
    @Multipart
    Call<ItemList> getAddItem(@Part("api_token") RequestBody apiToken,
                              @Part("category_id") RequestBody categoryId,
                              @Part("name") RequestBody name,
                              @Part("description") RequestBody description,
                              @Part("price") RequestBody price,
                              @Part("offer_price") RequestBody offerPrice,
                              @Part MultipartBody.Part photo);

    @GET("client/notifications")
    Call<Notification> getClientNotification(@Query("api_token") String apToken);


    @GET("restaurant/notifications")
    Call<Notification> getRestaurantNotification(@Query("api_token") String apToken);

@POST("contact")
@FormUrlEncoded
Call<ContactUs> getContact(@Field("name") String name,
                           @Field("email") String email,
                           @Field("phone") String phone,
                           @Field("type") String type,
                           @Field("content") String content);
    @GET("offers")
    Call<Offers> getOffers(@Query("restaurant_id") int restaurantId,
                           @Query("page") int page);
}

