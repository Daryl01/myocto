package com.youxi912.yule912.Base;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

import com.youxi912.yule912.model.AccountModel;
import com.youxi912.yule912.model.AwardModel;
import com.youxi912.yule912.model.BannerModel;
import com.youxi912.yule912.model.BaseModel;
import com.youxi912.yule912.model.ChargeModel;
import com.youxi912.yule912.model.ChatRoomModel;
import com.youxi912.yule912.model.CheckInModel;
import com.youxi912.yule912.model.CoinDetailModel;
import com.youxi912.yule912.model.CombineGameModel;
import com.youxi912.yule912.model.CreateLiveModel;
import com.youxi912.yule912.model.GameModel;
import com.youxi912.yule912.model.GameTypeModel;
import com.youxi912.yule912.model.GetRoomNameModel;
import com.youxi912.yule912.model.HistoryModel;
import com.youxi912.yule912.model.IndexGameModel;
import com.youxi912.yule912.model.InviteRecordModel;
import com.youxi912.yule912.model.LiveListModel;
import com.youxi912.yule912.model.LiveModel;
import com.youxi912.yule912.model.MineItemModel;
import com.youxi912.yule912.model.OrderModel;
import com.youxi912.yule912.model.PhoneModel;
import com.youxi912.yule912.model.PsModel;
import com.youxi912.yule912.model.QRImgModel;
import com.youxi912.yule912.model.RecordModel;
import com.youxi912.yule912.model.SignListModel;
import com.youxi912.yule912.model.StringBaseModel;
import com.youxi912.yule912.model.UpgradeModel;
import com.youxi912.yule912.model.UploadAvaterModel;
import com.youxi912.yule912.model.UserLoginModel;
import com.youxi912.yule912.model.VerifyCodeModel;
import com.youxi912.yule912.model.WXSettingModel;

public interface API {


    /***----------用户相关-------------*/

    @FormUrlEncoded
    @POST("user/login")
    Call<UserLoginModel> userLogin(@Field("username") String username, @Field("password") String password);

    //用户退出登录

    @FormUrlEncoded
    @POST("user/logout")
    Call<BaseModel> userLogout(@Field("token") String token);

    //等到用户信息
    @FormUrlEncoded
    @POST("user/info")
    Call<UserLoginModel> getUserInfo(@Field("token") String token);

    //获得用户手机号
    @FormUrlEncoded
    @POST("user/phoneno")
    Call<PhoneModel> getUserPhone(@Field("token") String token);

    //用户重置密码--得到验证码
    @FormUrlEncoded
    @POST("user/resetpassword")
    Call<VerifyCodeModel> getVerifyCode(@Field("username") String username, @Field("password") String password, @Field("step") String step);

    //用户重置密码
    @FormUrlEncoded
    @POST("user/resetpassword")
    Call<BaseModel> resetPass(@Field("username") String username, @Field("password") String password, @Field("verifyid") String verifyid,
                              @Field("verify") String verify, @Field("step") String step);

    //得到章鱼丸明细
    @FormUrlEncoded
    @POST("user/psinfo")
    Call<PsModel> getPsInfor(@Field("token") String token);

    @FormUrlEncoded
    @POST("user/awardhistory")
    Call<AwardModel> getAwardHistory(@Field("token") String token);

    //得到邀请历史
    @FormUrlEncoded
    @POST("user/invitehistory")
    Call<InviteRecordModel> getInviteHistory(@Field("token") String token);

    //用户注册
    @POST("user/sign")
    Call<MineItemModel> userSign();

    //用户绑定手机号，微信等
    @POST("user/bind")
    Call<MineItemModel> userBind(@Body String token, @Body String mobile, @Body String email,
                                 @Body String wechat_id, @Body String verify_code);

    //用户实名认证
    @POST("user/verify")
    Call<MineItemModel> userVerifySelf(@Body String token, @Body String name, @Body String idcard);

    //得到当前用户的交易账户信息
    @FormUrlEncoded
    @POST("trade/account")
    Call<AccountModel> getAccountInfor(@Field("token") String token);

    //用户上传头像
    @FormUrlEncoded
    @POST("user/uploadavatar")
    Call<UploadAvaterModel> uploadAvatar(@Field("token") String token, @Field("avatar") String avatar);

    //设置性别
    @FormUrlEncoded
    @POST("user/setgender")
    Call<BaseModel> setUserGender(@Field("token") String token, @Field("gender") String gender);

    @FormUrlEncoded
    @POST("trade/history")
    Call<RecordModel> getAccoutHistory(@Field("token") String token, @Field("currency") String currency);

    @POST("trade/listturnout")
    Call<MineItemModel> getWithDrawList(@Body String token);

    @POST("trade/exchange")
    Call<MineItemModel> exchangeMoney(@Body RequestBody requestBody);

    @FormUrlEncoded
    @POST("shop/chargephonefare")
    Call<BaseModel> charge(@Field("token") String token, @Field("telephone") String telephone, @Field("id") String id,@Field("password") String password);

    @FormUrlEncoded
    @POST("shop/phonefares")
    Call<ChargeModel> getFaresInfor(@Field("token") String token);

    @FormUrlEncoded
    @POST("wxpay/payconfiginfo")
    Call<WXSettingModel> getWxSetting(@Field("token") String token);

    //用户签到
    @FormUrlEncoded
    @POST("user/checkin")
    Call<CheckInModel> checkIn(@Field("token") String token);

    //用户签到列表
    @FormUrlEncoded
    @POST("user/diglist")
    Call<SignListModel> getSignList(@Field("token") String token);


    /***--------------------游戏相关----------------------**/
    //得到最近玩的游戏列表
    @FormUrlEncoded
    @POST("game/recently")
    Observable<IndexGameModel> getRecentlyGames(@Field("token") String token);

    //得到推荐的游戏列表
    @FormUrlEncoded
    @POST("game/suggest")
    Observable<IndexGameModel> getRecommendGames(@Field("token") String token);

    //得到热门游戏列表
    @FormUrlEncoded
    @POST("game/hot")
    Call<IndexGameModel> getHotGames(@Field("token") String token, @Field("page") int page
            , @Field("page_size") int page_size);

    //得到付费游戏列表
    @FormUrlEncoded
    @POST("game/expense")
    Observable<IndexGameModel> getExpenseGames(@Field("token") String token);


    //得到banner
    @FormUrlEncoded
    @POST("mobile/banners")
    Observable<BannerModel> getBanners(@Field("token") String token);

    //得到游戏的分类
    @FormUrlEncoded
    @POST("game/category")
    Observable<GameTypeModel> getGameTypes(@Field("token") String token);



    //根据分类得到游戏列表
    @FormUrlEncoded
    @POST("game/lists")
    Call<IndexGameModel> getGamesByType(@Field("token") String token, @Field("page") int page,
                                        @Field("page_size") int page_size, @Field("cate_id") int cate_id);

    //查看游戏详情
    @FormUrlEncoded
    @POST("game/detail")
    Call<GameModel> getGameDetails(@Field("token") String token, @Field("id") int id);

    //添加游戏访问记录
    @FormUrlEncoded
    @POST("game/access")
    Call<BaseModel> gameAccess(@Field("token") String token, @Field("gameid") int gameid);

    //各类游戏列表/导航栏/
    @FormUrlEncoded
    @POST("combine/game")
    Call<CombineGameModel> getCombineGame(@Field("token") String token);

    @FormUrlEncoded
    @POST("trade/currencyaccount")
    Call<CoinDetailModel> getCurrencyDetail(@Field("token") String token,
                                            @Field("currency") String currency);

    @FormUrlEncoded
    @POST("wxpay/preorder")
    Call<OrderModel> createOrder(@Field("token") String token, @Field("pay_type") int type, @Field("product_id") int id);

    @FormUrlEncoded
    @POST("trade/turnout")
    Call<BaseModel> beeTransferOut(@Field("token") String token, @Field("currency") String currency,
                                   @Field("address") String address, @Field("amount") String amount, @Field("pass") String pass);

    @FormUrlEncoded
    @POST("trade/transfer")
    Call<StringBaseModel> transferOut(@Field("token") String token, @Field("target") String target,
                                      @Field("amount") String amount, @Field("pass") String pass, @Field("step") String step);

    @FormUrlEncoded
    @POST("trade/exchange")
    Call<StringBaseModel> exchange(@Field("token") String token, @Field("amount") String amount);

    //获取专属二维码
    @FormUrlEncoded
    @POST("user/share")
    Call<QRImgModel> getQRImg(@Field("token") String token);

    @GET("http://api.fir.im/apps/latest/com.youxi912.yule912")
    Call<UpgradeModel> queryUpgrade(@Query("api_token") String api_token,
                                    @Query("type") String type);
    @FormUrlEncoded
    @POST("chatroom/lists")
    Call<ChatRoomModel> getChatRooms(@Field("token") String token);


    //创建直播
    @FormUrlEncoded
    @POST("room/create")
    Call<CreateLiveModel> create_live(@Field("accid") String accid, @Field("name") String name, @Field("token") String token);

    //获取用户个人直播
    @FormUrlEncoded
    @POST("room/get")
    Call<GetRoomNameModel> get_roomInfor(@Field("accid") String accid, @Field("token") String token);

    //获取直播列表
    @FormUrlEncoded
    @POST("room/get")
    Call<GetRoomNameModel> get_other_roomInfor(@Field("accid") String accid);


    @FormUrlEncoded
    @POST("room/lists")
    Call<LiveListModel> getLiveList(@Field("page") String page, @Field("page_size") String page_size
            , @Field("field") String ctime, @Field("sort") String sort, @Field("token") String token);

    @FormUrlEncoded
    @POST("room/stats")
    Call<LiveModel> getLiveStats(@Field("token") String token, @Field("cid") String cid);

}
