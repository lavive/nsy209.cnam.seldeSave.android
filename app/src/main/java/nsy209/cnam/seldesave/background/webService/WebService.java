package nsy209.cnam.seldesave.background.webService;

import nsy209.cnam.seldesave.shared.dto.AssociationDto;
import nsy209.cnam.seldesave.shared.dto.CategoriesDto;
import nsy209.cnam.seldesave.shared.dto.MemberDto;
import nsy209.cnam.seldesave.shared.dto.MembersDto;
import nsy209.cnam.seldesave.shared.dto.NotificationsDto;
import nsy209.cnam.seldesave.shared.dto.RestListStringDto;
import nsy209.cnam.seldesave.shared.dto.SuppliesDemandsDto;
import nsy209.cnam.seldesave.shared.dto.SupplyDemandDto;
import nsy209.cnam.seldesave.shared.dto.TransactionDto;
import nsy209.cnam.seldesave.shared.dto.TransactionsDto;
import nsy209.cnam.seldesave.shared.dto.WealthSheetDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by lavive on 07/07/2017.
 */

public interface WebService {


    /* check first connection */
    @GET("member/get/{mobileId}/{cellNumber}")
    Call<Long> getMyId(@Path("mobileId") String mobileId,@Path("cellNumber") String cellNumber);

    /* check if local database need to update */

    @GET("member/get/{id}/update")
    Call<RestListStringDto> checkUpdates(@Path("id") long id);

    /* update local database */

    @GET("association")
    Call<AssociationDto> getAssociation();

    @GET("member/get/{id}/member")
    Call<MemberDto> getMyProfile(@Path("id") long id);

    @GET("supplyDemand/get/all")
    Call<SuppliesDemandsDto> getAllSuppliesDemands();

    @GET("member/get/all")
    Call<MembersDto> getAllMembers();

    @GET("category")
    Call<CategoriesDto> getAllCategories();

    @GET("member/get/{id}/wealthSheet")
    Call<WealthSheetDto> getWealthSheet(@Path("id") long id);

    @GET("member/get/{id}/notification")
    Call<NotificationsDto> getNotifications(@Path("id") long id);

    @GET("member/get/{id}/transaction")
    Call<TransactionsDto> getTransactions(@Path("id") long id);

    /* update remote database */

    @PUT("member/put")
    Call<Void> updateProfile(@Body MembersDto memberDto);

    @PUT("member/put/{id}/dateLastUpdate")
    Call<Void> updateDateLastUpdate(@Path("id") long id);

    @DELETE("supplyDemand/delete/{id}")
    Call<Void> deleteSupplyDemand(@Path("id") long id);

    @PUT("member/delete/{id}/notification")
    Call<Void> deleteNotificationFromMember(@Path("id") long id,@Body NotificationsDto notificationsDto);

    @PUT("supplyDemand/put")
    Call<Void> updateSupplyDemand(@Body SupplyDemandDto supplyDemandDto);

    @POST("supplyDemand/post")
    Call<Void> addSupplyDemand(@Body SupplyDemandDto supplyDemandDto);

    @POST("transaction")
    Call<Void> addTransaction(@Body TransactionDto transactionDto);


}
