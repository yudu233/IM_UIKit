package com.rain.library_netease_sdk.extension;

/**
 * @author : duyu
 * @date : 2019/1/3 15:25
 * @filename : CustomAttachmentType
 * @describe :
 */
public interface CustomAttachmentType {
    // 多端统一
//    int Guess = 1;      //猜拳
//    int SnapChat = 2;   //月后疾风
//    int RTS = 4;
//    int RedPacket = 5;
//    int OpenedRedPacket = 6;
//
//    int STICKER = 1;                //附件贴纸表情
//    int USER_PROFILE = 2;           //用户信息自定义消息
//    int UPDATE_USER = 3;            //服务端发送的全局自定义消息
//    int BUSINESS_CARD = 4;          //名片推荐消息


    //自定义消息（不要改！）
    int DEFAULT = -1;
    int ADD_FRIEND = 0;                //添加好友
    int AGREE_ADD_FRIEND = 1;          //同意好友申请
    int DELETE_FRIEND = 2;             //删除好友
    int CHANGE_FRIEND_INFO = 3;        //好友资料更改
    int RECOMMEND_CARD = 4;            //好友名片推荐
    int RED_PACKET = 10;               //天下乐消息
    int OPEN_RED_PACKET = 11;          //拆天下乐tips消息
    int STICKER = 12;                  //附件贴纸表情


    //分享（13-20  不要改！）
    int SHARE_ARTICLE = 13;            //分享文章到社群
    int SHARE_LIVE = 14;               //分享直播到社群

    //课程分享
    int SHARE_LESSON_LESSON = 15 ;

    //游戏分享
    int SHARE_LESSON_GAME= 16 ;
    //互联互通分享
    int SHARE_LESSON_UNICOM= 17 ;

    //天下商城分享
    int SHARE_SHOP_UNICOM= 18 ;
    //天下共享分享
    int SHARE_WORLD_UNICOM= 19 ;

    //20之后，新的自定义消息

    //直播(不要改！)
    int YUN_LIVE_ChAT = 1001;          //云信直播聊天室自定义消息
    int YUN_LIVE_GIFT = 1002;          //云信直播聊天室自定义送礼物
    int YUN_LIVE_GIFT_SCORE = 1003;    //云信直播聊天室自定义礼物分数
    int YUN_LIVE_PK_ID = 1004;         //云信直播聊天室自定pk_id
    int YUN_LIVE_MEMBER = 1005;         //云信直播聊天室自定发送成员信息
    int YUN_LIVE_CONFIG = 1006;         //直播间基本配置信息
    int YUN_LIVE_USER_MUTE = 1007;         //直播间用户禁言自定义消息
    int YUN_LIVE_USER_ADMIN = 1008;         //直播间用户设置管理员自定义消息
}
