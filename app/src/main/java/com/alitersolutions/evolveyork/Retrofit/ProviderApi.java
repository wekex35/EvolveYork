package com.alitersolutions.evolveyork.Retrofit;

import com.alitersolutions.evolveyork.model.AddBedModel;
import com.alitersolutions.evolveyork.model.BedHistoryResponse;
import com.alitersolutions.evolveyork.model.ResponseModel;
import com.alitersolutions.evolveyork.model.SentModel;
import com.alitersolutions.evolveyork.model.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.alitersolutions.evolveyork.utils.SharedPreferenceUtil.getStringValue;

/**
 * Created by Confiz123 on 11/21/2017.
 */

public interface ProviderApi {

        //Base url
      //  String BASE_SITE = getStringValue(context,BASEURL,base_url);
       // String BASE_SITE = "http://192.168.1.119:5151/";
//        String BASE_SITE = "http://192.168.1.102/aliter/";


        @GET("Items_API/Items_API/testConnection")
        Call<ResponseModel> testConnection();

        @GET("Items_API/get_all_location")
        Call<ResponseModel> getAllLocation();

        @GET("Items_API/get_all_item")
        Call<ResponseModel> getallItems();

        @POST("Items_API/update_item_history")
        Call<ResponseModel>  sentToServer(@Body SentModel sentModel);

//        @Headers({"Content-Type: application/x-www-form-urlencoded"})
        @POST("API/user/login")
        Call<ResponseModel> loginUser(@Body UserModel userModel);


       /* @GET("appMenuList")
        Call<ResponseModel> homeAppList();
        @GET("appMenuList")
        Call<ResponseModel> homeAppList();*/

/*
        @POST("register")
        Call<MultiPurposeModel> registerUser(@Body MultiPurposeModel post);

        @POST("sendotp")
        Call<MobileVerificationModel> sendOtp(@Body MobileVerificationModel mobileVerificationModel);

        @POST("verifyotp")
        Call<MobileVerificationModel> verifyOtp(@Body MobileVerificationModel mobileVerificationModel);
*/




    /*    @GET("categories")
        Call<List<Category>> loadCategories();

        @GET("configs/countries")
        Call<List<Countries>> loadCountries();

        @GET("providers")
        Call<List<ProviderModel>> searchProvider(@Header("accept") String type,
                                                 @Query("user_id") int userid,
                                                 @Query("category_id") int categoryId,
                                                 @Query("keyword") String keyword,
                                                 @Query("geo") String geo,
                                                 @Query("geo_distance") String geo_distance,
                                                 @Query("country") String country,
                                                 @Query("city") String city,
                                                 @Query("zip") String zip,
                                                 @Query("lat") String lat,
                                                 @Query("long") String longitude);
        @GET("providers")
        Call<List<ProviderModel>> searchProvider(@Header("accept") String type,
                                                 @Query("user_id") int userid,
                                                 @Query("keyword") String keyword,
                                                 @Query("geo") String geo,
                                                 @Query("geo_distance") String geo_distance,
                                                 @Query("country") String country,
                                                 @Query("city") String city,
                                                 @Query("zip") String zip,
                                                 @Query("lat") String lat,
                                                 @Query("long") String longitude);
        @GET("providers")
        Call<List<ProviderModel>> getFeaturedProviders(@Query("user_id") int userid, @Query("featured") String featured);

        @POST("providers")
        Call<User> registerUser(@Body RegisterBusiness post);


        @GET("user/profile/{user_id}")
        Call<ProviderModel> getUserProfile(@Path("user_id") int userid);

        @POST("user/profile/{user_id}")
        Call<ApiResponse> updateUserProfile(@Path("user_id") int userid, @Body ProviderModel post);

        @Multipart
        @POST("wp-json/wp/v2/media")
        Call<ImageUploadResponse> upload(
                @Part MultipartBody.Part file
        );

        @POST("providers/appointment")
        Call<BookingResponse> makeAppointment(@Body BookingRequest post);

        @POST("providers/confirm-appointment")
        Call<ApiResponse> confirmAppointment(@Body ConfirmAppointment post);

        @GET("user/appointments")
        Call<List<Appointment>> getUserAppointments(@Query("user_id") long userid);

        @POST("user/reset-password")
        Call<ApiResponse> recoverPassword(@Body ResetPassword post);

        @POST("providers/appointment/slots")
        Call<List<AppointmentSlot>> getSlotsOfDate(@Body RequestSlots post);

        @POST("providers/save-rating")
        Call<ApiResponse> saveProviderRating(@Body ReviewProvider rating);

        @POST("user/token")
        Call<ApiResponse> saveUserToken(@Body TokenRequestParam rating);

        @GET("user/services")
        Call<List<ProfileServices>> getUserServices(@Query("user_id") long userId);

        @POST("user/services")
        Call<ApiResponse> updateUserServices(@Body ManageServicesRequestParam params);

        @POST("providers/reviews")
        Call<List<ProviderReviewListData>> getProviderReviews(@Body ReviewProvider params);

        @GET("user/favorites")
        Call<List<ProviderModel>> getUserFavorites(@Query("user_id") long userId);

        @POST("user/favorites")
        Call<ApiResponse> updateUserFavorites(@Body MarkFavoriteParam params);

        @GET("providers/manage-appointments")
        Call<List<Appointment>> getProviderAppointments(@Query("provider_id") long providerId);

        @POST("providers/appointments-status")
        Call<ApiResponse> updateProviderAppointments(@Body ManageAppointmentRequest params);

        @GET("providers/privacy-settings")
        Call<PrivacySettings> getPrivacySettings(@Query("publisher_id") long providerId);

        @POST("providers/privacy-settings")
        Call<ApiResponse> updatePrivacySettings(@Body ManagePrivacyRequest request);

        @GET("jobs")
        Call<List<JobItem>> getAllJobs();

        @GET("providers/business-hours")
        Call<BusinessHours> getBusinessHour(@Query("publisher_id") long providerId);

        @POST("providers/business-hours")
        Call<ApiResponse> updateBusinessHour(@Body ManagePrivacyRequest request);

        @POST("providers/set_appointment_slot")
        Call<AddSlotCallBack> setSlots(@Body AppointmentSlot slots);

        @POST("providers/get_slot")
        Call<GetSlotList> getSlots(@Body GetSlotList slots);

        @POST("providers/delete_appointment_slot")
        Call<DeleteSlot> deleteSlots(@Body DeleteSlot slots);

        @POST("user/delete_media")
        Call<DeleteMedia> deleteMedia(@Body DeleteMedia deleteMedia);

        @POST("providers/save_appointment_settings")
        Call<DeleteMedia> saveAppointmentSetting(@Body AppointmentSettingModel appointmentSettingModel);

        @POST("providers/get_appointment_settings")
        Call<ProviderModel> getAppointmentSetting(@Body AppointmentSettingModel appointmentSettingModel);
*/




}
