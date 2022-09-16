package com.in.kistec.Retrofit;

import com.in.kistec.API_Model.Add_Record_Model;
import com.in.kistec.API_Model.All_Record_Model;
import com.in.kistec.API_Model.All_Search_List_Model;
import com.in.kistec.API_Model.Change_Password_Model;
import com.in.kistec.API_Model.Check_Phone_Model;
import com.in.kistec.API_Model.Clear_All_Notification_Model;
import com.in.kistec.API_Model.Clear_Particular_Notification_Model;
import com.in.kistec.API_Model.ContactUsModel;
import com.in.kistec.API_Model.Edit_Record_Model;
import com.in.kistec.API_Model.Login_Model;
import com.in.kistec.API_Model.Logout_Model;
import com.in.kistec.API_Model.Notification_Model;
import com.in.kistec.API_Model.Read_Notification_Model;
import com.in.kistec.API_Model.Record_Details_Model;
import com.in.kistec.API_Model.Record_Details_Search_Model;
import com.in.kistec.API_Model.Registration_Model;
import com.in.kistec.API_Model.Reset_Model;
import com.in.kistec.API_Model.Status_List_Model;
import com.in.kistec.API_Model.Update_Profile_Model;
import com.in.kistec.API_Model.User_Details_Model;
import com.in.kistec.API_Model.notification_Count_Model;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {


    @Multipart
    @POST("registration")
    Call<Registration_Model> registration(@Part("user_name") RequestBody user_name,
                                          @Part("email") RequestBody email,
                                          @Part("mobile") RequestBody mobile,
                                          @Part("password") RequestBody password,
                                          @Part("city_name") RequestBody city_name);


    @FormUrlEncoded
    @POST("readNotification")
    Call<Read_Notification_Model> readNotification(@Header("authorization") String authorization,
                                                   @Field("user_id") String user_id,
                                                   @Field("all") String all);


    @FormUrlEncoded
    @POST("delete_notification")
    Call<Clear_Particular_Notification_Model> delete_particular_notification(@Header("authorization") String authorization,
                                                                             @Field("user_id") String user_id,
                                                                             @Field("id") String id);


    @FormUrlEncoded
    @POST("delete_notification")
    Call<Clear_All_Notification_Model> delete_all_notification(@Header("authorization") String authorization,
                                                               @Field("user_id") String user_id);


    @Multipart
    @POST("update_profile")
    Call<Update_Profile_Model> update_profile(@Header("authorization") String authorization,
                                              @Part("id") RequestBody id,
                                              @Part("name") RequestBody name,
                                              @Part("mobile") RequestBody mobile,
                                              @Part("email") RequestBody email,
                                              @Part("city_name") RequestBody city_name,
                                              @Part MultipartBody.Part image);


    @FormUrlEncoded
    @POST("login")
    Call<Login_Model> login(@Field("device_token") String device_token,
                            @Field("email") String email,
                            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("record_details")
    Call<Record_Details_Search_Model> record_details(@Header("authorization") String authorization,
                                                     @Field("id") String email,
                                                     @Field("record_id") String password,
                                                     @Field("search") String search);

    @FormUrlEncoded
    @POST("user_details")
    Call<User_Details_Model> user_details(@Header("authorization") String authorization,
                                          @Field("id") String id);

    @FormUrlEncoded
    @POST("user_record_list")
    Call<Status_List_Model> user_record_list(@Header("authorization") String authorization,
                                             @Field("id") String id);


    @FormUrlEncoded
    @POST("r_detail")
    Call<Record_Details_Model> r_detail(
            @Header("authorization") String authorization,
            @Field("id") String id,
            @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("changePassword")
    Call<Change_Password_Model> changePassword(
            @Header("authorization") String authorization,
            @Field("id") String id,
            @Field("old_password") String old_password,
            @Field("new_password") String new_password);


    //    @Headers("Accept: application/json")
    @Multipart
    @POST("addRecords")
    Call<Add_Record_Model> addRecords(@Header("authorization") String authorization,
                                      @Part("user_id") RequestBody user_id,
                                      @Part("name") RequestBody name,
                                      @Part("person_id") RequestBody person_id,
                                      @Part("person_type") RequestBody person_type,
                                      @Part("about_person") RequestBody about_person,
                                      @Part("mobile") RequestBody mobile,
                                      @Part MultipartBody.Part profile,
                                      @Part MultipartBody.Part[] image);


    @FormUrlEncoded
    @POST("all_records")
    Call<All_Search_List_Model> all_records(@Header("authorization") String authorization,
                                            @Field("id") String id,
                                            @Field("search") String search);


    // Not using at this place clear all the sharedpreferance memory
    // @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("logout")
    Call<Logout_Model> logout(@Header("authorization") String authorization,
                              @Field("id") String id);


    @Multipart
    @POST("edit_record")
    Call<Edit_Record_Model> edit_record(@Header("authorization") String authorization,
                                        @Part("id") RequestBody id,
                                        @Part("name") RequestBody name,
                                        @Part("mobile") RequestBody mobile,
                                        @Part("person_id") RequestBody person_id,
                                        @Part("person_type") RequestBody person_type,
                                        @Part("user_id") RequestBody user_id,
                                        @Part("about_person") RequestBody about_person,
                                        @Part MultipartBody.Part profile,
                                        @Part MultipartBody.Part[] image);

    @FormUrlEncoded
    @POST("get_notfication")
    Call<Notification_Model> get_notfication(@Header("authorization") String authorization,
                                             @Field("id") String id);

    @FormUrlEncoded
    @POST("noti_count")
    Call<notification_Count_Model> noti_count(@Header("authorization") String authorization,
                                              @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("contact1")
    Call<ContactUsModel> Contaact_Us(@Field("address") String address,
                                     @Field("mail ") String mail,
                                     @Field("phone ") String phone);


    @FormUrlEncoded
    @POST("check_number")
    Call<Check_Phone_Model> check_number(@Field("phone_number") String phone_number);


    @FormUrlEncoded
    @POST("reset_password")
    Call<Reset_Model> reset_password(@Field("phone_number") String phone_number,
                                     @Field("password") String password);

}
