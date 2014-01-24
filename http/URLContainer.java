package com.youku.http;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Base64;
import com.youku.YKAnTracker.data.Device;
import com.youku.YKAnTracker.tool.SessionUnitil;
import com.youku.phone.Youku;
import com.youku.util.Logger;
import com.youku.util.YoukuUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.openudid.OpenUDID_manager;

public class URLContainer
{
  public static final String FEEDBACK_URL = "http://api.3g.youku.com/issue_report/ios";
  public static final String FEEDBACK_WEBVIEW_URL = "http://www.youku.com/service/hfeed";
  public static String MMA_CONFIG_HOST;
  public static String MQTT_HOST;
  public static final String OFFICAL_MMA_CONFIG_HOST = "http://valf.atm.youku.com/sdkconfig.xml";
  public static final String OFFICIAL_MQTT_HOST = "mqtt.m.youku.com";
  public static final String OFFICIAL_YOUKU_DOMAIN = "http://api.3g.youku.com/";
  public static final String OFFICIAL_YOUKU_PUSH_DOMAIN = "http://push.m.youku.com/";
  public static final String OFFICIAL_YOUKU_USER_DOMAIN = "http://user.api.3g.youku.com/";
  public static final String OFFICIAL_YOUKU_WIRELESS_DOMAIN = "http://api.3g.youku.com/openapi-wireless/";
  public static final String OFFICIAL_YOUKU_WIRELESS_LAYOUT_DOMAIN = "http://api.3g.youku.com/layout/";
  public static final String PROMOTED_APP_URL = "http://m.youku.com/ykhybrid/apps2";
  public static final int PZ = 30;
  public static String REVIEW_URL;
  public static final String SHARE_URL = "http://m.youku.com/ykhybrid/share";
  public static final String SURVEY_PAD_WEBVIEW_URL = "http://static.youku.com/pub/youku/fragment/panel_pad.html";
  public static final String SURVEY_PHONE_WEBVIEW_URL = "http://static.youku.com/pub/youku/fragment/panel_phone.html";
  public static final String TEST_MQTT_HOST = "211.151.146.168";
  public static final String TEST_PROMOTED_APP_URL = "http://10.10.151.33/ykhybrid/apps2";
  public static final String TEST_YOUKU_DOMAIN = "http://test1.api.3g.youku.com/";
  public static final String TEST_YOUKU_PUSH_DOMAIN = "http://10.103.13.33/";
  public static final String TEST_YOUKU_WIRELESS_DOMAIN = "http://test1.api.3g.youku.com/openapi-wireless/";
  public static final String TEST_YOUKU_WIRELESS_LAYOUT_DOMAIN = "http://test1.api.3g.youku.com/layout/";
  public static String YOUKU_AD_DOMAIN;
  public static String YOUKU_DOMAIN;
  public static String YOUKU_FEEDBACK_URL;
  public static String YOUKU_PROMOTED_APP_URL;
  public static String YOUKU_PUSH_DOMAIN;
  public static String YOUKU_USER_DOMAIN;
  public static String YOUKU_WIRELESS_CMS = "http://api.3g.youku.com/openapi-cms/ipad/";
  public static String YOUKU_WIRELESS_DOMAIN;
  public static String YOUKU_WIRELESS_LAYOUT_DOMAIN;
  public static final int audiolang = 1;
  private static String initData;

  static
  {
    REVIEW_URL = "https://play.google.com/store/apps/details?id=com.youku.phone&write_review=true";
    YOUKU_AD_DOMAIN = "http://ad.api.3g.youku.com/";
    MMA_CONFIG_HOST = "http://10.10.221.200/manage/sdkconfig.xml";
    YOUKU_USER_DOMAIN = "http://test1.api.3g.youku.com/";
  }

  private static String URLEncoder(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
      return "";
    try
    {
      String str = URLEncoder.encode(paramString, "UTF-8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      return "";
    }
    catch (NullPointerException localNullPointerException)
    {
    }
    return "";
  }

  public static String addUserTag(String paramString)
  {
    return YOUKU_USER_DOMAIN + "user/add/tag?" + getStatisticsParameter() + "&tag=" + URLEncoder(paramString);
  }

  public static String getAdStartpageUrl(int paramInt1, int paramInt2)
  {
    StringBuilder localStringBuilder = new StringBuilder().append(YOUKU_AD_DOMAIN).append("adv/startpage?site=1").append("&width=").append(paramInt1).append("&height=").append(paramInt2).append("&device_brand=").append(Device.BRAND).append("&version=1.0").append("&ouid=");
    if (OpenUDID_manager.isInitialized());
    for (String str = OpenUDID_manager.getOpenUDID(); ; str = "")
      return str + "&" + getStatisticsParameter();
  }

  public static String getAddCommentURL(String paramString)
  {
    return YOUKU_WIRELESS_DOMAIN + "videos/" + paramString + "/comments/add?" + getStatisticsParameter();
  }

  public static String getAddCommentURL(String paramString1, String paramString2)
  {
    return YOUKU_USER_DOMAIN + "v3/videos/" + paramString1 + "/comment/add?" + getStatisticsParameter() + "&content=" + URLEncoder(paramString2);
  }

  public static String getAddFavURL(String paramString)
  {
    return YOUKU_USER_DOMAIN + "user/v3/favorite/add?" + getStatisticsParameter() + "&vid=" + paramString;
  }

  public static String getAddFavouriteChannelURL(String paramString)
  {
    return YOUKU_USER_DOMAIN + "user/add/favorite?" + getStatisticsParameter() + "&cid=" + paramString;
  }

  public static String getAddHistoryURL(String paramString1, String paramString2, int paramInt)
  {
    return YOUKU_WIRELESS_DOMAIN + "user/history/add?" + getStatisticsParameter() + "&vid=" + paramString1 + "&showid=" + paramString2 + "&point=" + paramInt;
  }

  public static String getAlbumsURL(String paramString, int paramInt1, int paramInt2)
  {
    return YOUKU_DOMAIN + "albums/" + paramString + "/videos/v3?" + getStatisticsParameter() + "&fields=vid|titl&pg=" + paramInt1 + "&pz=" + paramInt2;
  }

  public static String getAlipayLoginUrl(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(YOUKU_DOMAIN);
    localStringBuilder.append("user/alipay/login?");
    localStringBuilder.append(getStatisticsParameter());
    localStringBuilder.append("&code=" + paramString);
    return localStringBuilder.toString();
  }

  public static String getChannelRankCategoryURL()
  {
    return YOUKU_WIRELESS_DOMAIN + "shows/rank/list?client_type=0&" + getStatisticsParameter();
  }

  public static String getChannelRankURL(int paramInt1, int paramInt2)
  {
    return YOUKU_WIRELESS_DOMAIN + "android_v3/shows/category/" + paramInt1 + "/rank?" + getStatisticsParameter() + "&image_hd=1&pz=" + paramInt2 + "&pg=";
  }

  public static String getChannelTabsUrl(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(YOUKU_WIRELESS_LAYOUT_DOMAIN);
    localStringBuilder.append("android/channel/subtabs?");
    localStringBuilder.append(getStatisticsParameter());
    localStringBuilder.append("&cid=" + paramString);
    return localStringBuilder.toString();
  }

  public static String getChannelURL(String paramString1, String paramString2, String paramString3)
  {
    return YOUKU_WIRELESS_LAYOUT_DOMAIN + "android3_0/item_list?" + getStatisticsParameter() + "&cid=" + paramString1 + "&filter=" + paramString2 + "&image_hd=3&ob=" + paramString3 + "&pg=";
  }

  public static String getChannelVideoUrl(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    localStringBuilder1.append(YOUKU_WIRELESS_LAYOUT_DOMAIN);
    localStringBuilder1.append("android/channel/subpage?");
    localStringBuilder1.append(getStatisticsParameter());
    localStringBuilder1.append("&cid=" + paramString1);
    localStringBuilder1.append("&sub_channel_id=" + paramString2);
    localStringBuilder1.append("&sub_channel_type=" + paramString3);
    StringBuilder localStringBuilder2 = new StringBuilder().append("&filter=");
    if (TextUtils.isEmpty(paramString4));
    for (String str = ""; ; str = URLEncoder.encode(paramString4))
    {
      localStringBuilder1.append(str);
      localStringBuilder1.append("&ob=" + paramString5);
      localStringBuilder1.append("&pg=");
      return localStringBuilder1.toString();
    }
  }

  public static String getDelFavoriteURL(String paramString)
  {
    return YOUKU_USER_DOMAIN + "user/v3/favorite/del?" + getStatisticsParameter() + "&vid=" + paramString;
  }

  public static String getDelFavouriteChannelURL(String paramString)
  {
    return YOUKU_USER_DOMAIN + "user/del/favorite?" + getStatisticsParameter() + "&cid=" + paramString;
  }

  public static String getDelUploadedURL(String paramString)
  {
    return YOUKU_WIRELESS_DOMAIN + "user/uploads/del?" + getStatisticsParameter() + "&vid=" + paramString;
  }

  public static String getDelUserTagURL(String paramString)
  {
    return YOUKU_USER_DOMAIN + "user/del/tag?" + getStatisticsParameter() + "&tagid=" + paramString;
  }

  public static String getDeleteHistory(String paramString1, String paramString2)
  {
    String str = YOUKU_WIRELESS_DOMAIN + "user/history/del?" + getStatisticsParameter() + "&vid=" + paramString1 + "&showid=";
    if (paramString2 != null)
      str = str + paramString2;
    return str;
  }

  public static String getDetailLayout(String paramString)
  {
    return YOUKU_WIRELESS_LAYOUT_DOMAIN + "android3_0/play/detail?" + getStatisticsParameter() + "&id=" + paramString + "&format=" + Youku.getCurrentFormat();
  }

  public static String getDetailSpecialLayout(String paramString1, String paramString2)
  {
    Logger.e("-----------------TAGAGAGAGA", YOUKU_WIRELESS_LAYOUT_DOMAIN + "android3_0/play/detail?" + getStatisticsParameter() + "&id=" + paramString1 + "&video_id=" + paramString2 + "&format=" + Youku.getCurrentFormat());
    return YOUKU_WIRELESS_LAYOUT_DOMAIN + "android3_0/play/detail?" + getStatisticsParameter() + "&id=" + paramString2 + "&video_id=" + paramString1 + "&format=" + Youku.getCurrentFormat();
  }

  public static String getDoPayUrl(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(YOUKU_DOMAIN);
    localStringBuilder.append("common/v3/do_pay?");
    localStringBuilder.append(getStatisticsParameter());
    localStringBuilder.append("&id=" + paramString);
    localStringBuilder.append("&pay_channel=100");
    return localStringBuilder.toString();
  }

  public static String getDownVideoURL(String paramString)
  {
    return YOUKU_DOMAIN + "videos/" + paramString + "/evaluation/down" + "?" + getStatisticsParameter();
  }

  public static String getDownloadURL(String paramString1, int paramInt, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder().append(YOUKU_WIRELESS_DOMAIN).append("videos/").append(paramString1).append("/download?").append(getStatisticsParameter()).append("&format=").append(paramInt).append("&language=");
    if (TextUtils.isEmpty(paramString2))
      paramString2 = "";
    return paramString2;
  }

  public static String getFavoriteURLHD(String paramString)
  {
    return YOUKU_USER_DOMAIN + "user/favorites/hd?" + getStatisticsParameter() + "&fields=titl|imghd|dura|repu|vid|stat&&pz=" + paramString + "&pg=";
  }

  public static String getFeedbackWebViewURL(Context paramContext)
  {
    StringBuilder localStringBuilder1 = new StringBuilder().append("ai:6f81431b00e5b30a|an:YA|anw:").append(Device.NETWORK).append("|av:").append(Youku.versionName).append("|di:").append(Device.guid).append("|do:Android").append("|dov:").append(Build.VERSION.RELEASE).append("|dt:");
    String str1;
    String str2;
    StringBuilder localStringBuilder2;
    if (Youku.isTablet)
    {
      str1 = "pad";
      str2 = Base64.encodeToString(str1.getBytes(), 0);
      localStringBuilder2 = new StringBuilder().append(YOUKU_FEEDBACK_URL).append("?subtype=50&uid=");
      if (!Youku.isLogined)
        break label157;
    }
    label157: for (String str3 = Youku.getPreference("uid"); ; str3 = "0")
    {
      return str3 + "&appinfo=" + str2;
      str1 = "phone";
      break;
    }
  }

  public static String getFilterURL(String paramString)
  {
    return YOUKU_WIRELESS_LAYOUT_DOMAIN + "android3_0/channel/filter?" + getStatisticsParameter() + "&cid=" + paramString;
  }

  public static String getFilterURL(String paramString1, String paramString2)
  {
    return YOUKU_WIRELESS_LAYOUT_DOMAIN + "android3_0/channel/filter?" + getStatisticsParameter() + "&sort_type=" + paramString1 + "&cid=" + paramString2;
  }

  public static String getForgetPasswordPageUrl()
  {
    return "http://www.youku.com/user_getPwd/";
  }

  public static String getGuessLike()
  {
    int i = 30;
    if (!Youku.isLogined)
      i = 16;
    return YOUKU_USER_DOMAIN + "layout/v3/like?" + getStatisticsParameter() + "&pz=" + i;
  }

  public static String getHistoryURL(int paramInt)
  {
    return YOUKU_USER_DOMAIN + "user/historys?" + getStatisticsParameter() + "&fields=titl|vid|stg|isstage|point|playend|hwclass|dura|sid|imghd|lastupdate&pz=" + paramInt;
  }

  public static String getHomePageRecommendURL()
  {
    return YOUKU_WIRELESS_LAYOUT_DOMAIN + "android3_0/top?with_nav=1&with_topic=1&show_paid_video=1&show_slider=1&" + getStatisticsParameter();
  }

  public static String getHotWordsURL()
  {
    return YOUKU_WIRELESS_DOMAIN + "keywords/recommend?pz=10&" + getStatisticsParameter();
  }

  public static String getInitURL()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(YOUKU_WIRELESS_DOMAIN).append("initial?").append(getStatisticsParameter()).append("&brand=").append(Device.BRAND).append("&btype=").append(Device.BTYPE).append("&os=").append(Device.OS).append("&os_ver=").append(Device.OS_VER).append("&wt=").append(Device.WT).append("&ht=").append(Device.HT).append("&imsi=").append(Device.IMSI).append("&uuid=&deviceid=&time=").append(Youku.ACTIVE_TIME);
    return localStringBuilder.toString();
  }

  public static String getLoginURL(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(YOUKU_WIRELESS_DOMAIN).append("user/login?").append(getStatisticsParameter()).append("&uname=").append(URLEncoder(paramString1)).append("&pwd=").append(URLEncoder(paramString2));
    return localStringBuilder.toString();
  }

  public static String getMutilVideoPlayUrl(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    return YOUKU_DOMAIN + "v3/play/address?point=1&id=" + paramString1 + "&local_time=" + paramString2 + "&local_vid=" + paramString3 + "&format=" + paramString5 + "&videoseq=" + paramInt + "&language=" + paramString4 + "&audiolang=" + 1 + "&local_point=" + paramString6 + "&" + getStatisticsParameter();
  }

  public static String getMyCenter()
  {
    return YOUKU_WIRELESS_DOMAIN + "layout/android3_0/user/profile?" + getStatisticsParameter();
  }

  public static String getMyYoukuData(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder().append(YOUKU_USER_DOMAIN).append("user/newcenterinfo?").append(getStatisticsParameter());
    if (TextUtils.isEmpty(paramString))
      paramString = "";
    return paramString;
  }

  public static String getNextSeries(String paramString1, String paramString2)
  {
    return YOUKU_DOMAIN + "shows/" + paramString1 + "/next_series?showid=" + paramString1 + "&vid=" + paramString2 + "&" + getStatisticsParameter();
  }

  public static String getOneVideoPlayUrl(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    return YOUKU_DOMAIN + "v3/play/address?point=1&id=" + paramString1 + "&local_time=" + paramString2 + "&local_vid=" + paramString3 + "&format=" + paramString5 + "&language=" + paramString4 + "&local_point=" + paramString6 + "&audiolang=" + 1 + "&" + getStatisticsParameter();
  }

  public static String getPayDataListUrl()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(YOUKU_DOMAIN);
    localStringBuilder.append("common/v3/paylist?");
    localStringBuilder.append(getStatisticsParameter());
    localStringBuilder.append("&pz=50");
    localStringBuilder.append("&pg=1");
    return localStringBuilder.toString();
  }

  public static String getPromotedAppWebViewURL()
  {
    return (YOUKU_PROMOTED_APP_URL + "?type=802&version=" + Youku.versionName + "&" + getStatisticsParameter()).toLowerCase();
  }

  public static String getPushCollectionURL(int paramInt, String paramString)
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    StringBuilder localStringBuilder2 = localStringBuilder1.append(YOUKU_PUSH_DOMAIN + "collect-api/v1/guid_devices?&app=1&platform=");
    int i;
    String str1;
    label156: StringBuilder localStringBuilder4;
    if (Youku.isTablet)
    {
      i = 4;
      StringBuilder localStringBuilder3 = localStringBuilder2.append(i).append("&action=").append(paramInt).append("&gdid=").append(Device.gdid).append("&guid=").append(Device.guid).append("&pid=").append("6f81431b00e5b30a").append("&version=").append(Youku.versionName).append("&token=").append(Device.DEVICEID + 1).append("&grade=");
      if (!Youku.isHighEnd)
        break label251;
      str1 = "1";
      localStringBuilder4 = localStringBuilder3.append(str1).append("&test=");
      if ((0x2 & Youku.flags) == 0)
        break label259;
    }
    label259: for (String str2 = "0"; ; str2 = "0")
    {
      localStringBuilder4.append(str2);
      if ((paramInt == 2) || (paramInt == 3))
        localStringBuilder1.append("&user_id=").append(Youku.getPreference("uid"));
      if ((paramInt == 4) && (paramString != null))
        localStringBuilder1.append("&status=").append(paramString);
      return localStringBuilder1.toString();
      i = 3;
      break;
      label251: str1 = "9";
      break label156;
    }
  }

  public static String getPushFeedbackForOpenURL(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(YOUKU_PUSH_DOMAIN).append("feedback/active/").append(paramString).append("?").append(getStatisticsParameter()).append("&token=").append(Device.DEVICEID + 1);
    return localStringBuilder.toString();
  }

  public static String getPushFeedbackForReceiveURL(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(YOUKU_PUSH_DOMAIN).append("feedback/recv/").append(paramString).append("?").append(getStatisticsParameter()).append("&token=").append(Device.DEVICEID + 1);
    return localStringBuilder.toString();
  }

  public static String getRecommandUrl(String paramString)
  {
    return YOUKU_WIRELESS_LAYOUT_DOMAIN + "android3_0/tag_list?" + getStatisticsParameter() + "&cid=" + paramString;
  }

  public static String getRegistURL(String paramString1, String paramString2, String paramString3)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(YOUKU_WIRELESS_DOMAIN).append("user/register?").append(getStatisticsParameter()).append("&puid=").append(URLEncoder(paramString1)).append("&email=").append(URLEncoder(paramString3)).append("&pwd=").append(YoukuUtil.md5(URLEncoder(paramString2)));
    return localStringBuilder.toString();
  }

  public static String getRelatedVideoURL(String paramString, int paramInt)
  {
    if ((309 == paramInt) || (404 == paramInt) || (406 == paramInt) || (405 == paramInt));
    for (String str = "videos"; ; str = "shows")
      return YOUKU_DOMAIN + str + "/" + paramString + "/related?pg=1&pz=16&fields=sid|snam&" + getStatisticsParameter();
  }

  public static String getReviewURL()
  {
    return REVIEW_URL.toLowerCase();
  }

  public static String getSaosaoTvLoginUrl(String paramString1, String paramString2, String paramString3)
  {
    return YOUKU_DOMAIN + "thirdpart/snapshot?" + getStatisticsParameter() + "&source_guid=" + paramString1 + "&account=" + paramString2 + "&password=" + paramString3;
  }

  public static String getSaosaoUrl(String paramString)
  {
    return YOUKU_DOMAIN + "videos/qrcode?" + getStatisticsParameter() + "&tiny=" + paramString;
  }

  public static String getSearchDirectAllUrl(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
      paramString = URLEncoder(paramString);
    return YOUKU_DOMAIN + "layout/android/v3/search/direct_all/" + paramString + "?" + getStatisticsParameter() + "&format=" + Youku.getCurrentFormat();
  }

  public static String getSearchGenre()
  {
    return YOUKU_WIRELESS_LAYOUT_DOMAIN + "android3_3/searchfilters?" + getStatisticsParameter();
  }

  public static String getSearchKeys(String paramString)
  {
    return YOUKU_WIRELESS_DOMAIN + "keywords/suggest?" + getStatisticsParameter() + "&keywords=" + URLEncoder(paramString);
  }

  public static String getSearchOtherSiteVideoDataUrl(String paramString1, int paramInt, String paramString2)
  {
    return YOUKU_DOMAIN + "layout/site/show/series?" + getStatisticsParameter() + "&programmeid=" + paramString1 + "&siteid=" + paramInt + "&completed=" + paramString2;
  }

  public static String getSearchVideosUrl()
  {
    return YOUKU_WIRELESS_LAYOUT_DOMAIN + "android3_0/recommend/searchvideos/hd?pz=" + 30 + "&" + getStatisticsParameter();
  }

  public static String getSeriesDescURL(String paramString, int paramInt1, int paramInt2)
  {
    return YOUKU_DOMAIN + "shows/" + paramString + "/reverse/videos?" + getStatisticsParameter() + "&fields=vid|comm&pg=" + paramInt1 + "&pz=" + paramInt2;
  }

  public static String getShowSeriesListURL(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    return YOUKU_DOMAIN + "shows/" + paramString + "/reverse/videos?" + getStatisticsParameter() + "&fields=vid|titl|lim|is_new&pg=" + paramInt1 + "&pz=" + paramInt2 + "&order=" + paramInt3;
  }

  public static String getStatVVBegin(String paramString)
  {
    return YOUKU_WIRELESS_DOMAIN + "statis/vv?" + getStatisticsParameter() + paramString;
  }

  public static String getStatVVEnd(String paramString)
  {
    return YOUKU_WIRELESS_DOMAIN + "statis/vv?" + getStatisticsParameter() + paramString;
  }

  public static String getStatisOtherSiteAllSearchUrl(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    return YOUKU_WIRELESS_DOMAIN + "statis/allsearch?" + getStatisticsParameter() + "&pk_odshowid=" + paramString1 + "&pk_odshowname=" + URLEncoder(paramString2) + "&siteid=" + paramString3 + "&gourl=" + URLEncoder(paramString4) + "&playcontrol=" + paramString5;
  }

  public static String getStatistics4HomepageSlider(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("http://statis.api.3g.youku.com/openapi-wireless/statis/index_loop?");
    localStringBuilder.append(getStatisticsParameter());
    localStringBuilder.append(paramString);
    return localStringBuilder.toString();
  }

  public static String getStatisticsParameter()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(initData);
    if (!TextUtils.isEmpty(Device.OPERATOR))
      localStringBuilder.append("&operator=").append(Device.OPERATOR);
    if (!TextUtils.isEmpty(Device.NETWORK))
      localStringBuilder.append("&network=").append(URLEncoder(Device.NETWORK));
    return localStringBuilder.toString();
  }

  public static String getTradeUrl(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(YOUKU_DOMAIN);
    localStringBuilder.append("common/v3/get_trade?");
    localStringBuilder.append(getStatisticsParameter());
    localStringBuilder.append("&trade_id=" + paramString);
    return localStringBuilder.toString();
  }

  public static String getUpAndDownURL(String paramString, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder().append(YOUKU_WIRELESS_DOMAIN).append("videos/").append(paramString).append("/evaluation/");
    if (paramInt == 1);
    for (String str = "up"; ; str = "down")
      return str + "?" + getStatisticsParameter();
  }

  public static String getUpVideoURL(String paramString)
  {
    return YOUKU_DOMAIN + "videos/" + paramString + "/evaluation/up" + "?" + getStatisticsParameter();
  }

  public static String getUploadCategoryURL()
  {
    return "http://kuapi.youku.com/api_ptcategory/pid_XOA==_catpid_0_tp_0.html";
  }

  public static String getUploadedURLHD(String paramString)
  {
    return YOUKU_WIRELESS_DOMAIN + "user/uploads/image_hd?" + getStatisticsParameter() + "&fields=titl|imghd|dura|repu|vid&pz=" + paramString + "&pg=";
  }

  public static String getUrlSearch(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4)
  {
    if ((paramString1 != null) && (paramString1.length() != 0))
      paramString1 = URLEncoder(paramString1);
    return YOUKU_WIRELESS_LAYOUT_DOMAIN + "v3/search/" + paramString1 + "?" + getStatisticsParameter() + "&formats=" + Youku.getCurrentFormat();
  }

  public static String getUrlVideoSearch(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if ((paramString1 != null) && (paramString1.length() != 0))
      paramString1 = URLEncoder(paramString1);
    return YOUKU_WIRELESS_DOMAIN + "videos/search/" + paramString1 + "?" + getStatisticsParameter() + "&format=" + Youku.getCurrentFormat() + "&cid=" + paramString2 + "&seconds=" + paramString4 + "&seconds_end=" + paramString5 + "&ob=" + paramString3 + "&pg=";
  }

  public static String getUserIconUrl()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(YOUKU_USER_DOMAIN);
    localStringBuilder.append("user/newprofile?");
    localStringBuilder.append(getStatisticsParameter());
    return localStringBuilder.toString();
  }

  public static String getUserTags()
  {
    return YOUKU_WIRELESS_LAYOUT_DOMAIN + "android3_0/user/tags?" + getStatisticsParameter();
  }

  public static String getVarietySeriesListURL(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    return YOUKU_DOMAIN + "shows/" + paramString + "/reverse/videos?" + getStatisticsParameter() + "&fields=vid|titl|lim|guest|is_new&pg=" + paramInt1 + "&pz=" + paramInt2 + "&order=" + paramInt3;
  }

  public static String getVideoAdv(String paramString, Context paramContext, int paramInt)
  {
    StringBuilder localStringBuilder1 = new StringBuilder().append(YOUKU_AD_DOMAIN).append("adv?vid=").append(paramString).append("&site=1&position=").append(paramInt).append("&is_fullscreen=1&player_type=mdevice&sessionid=").append(SessionUnitil.playEvent_session).append("&device_type=");
    String str1;
    StringBuilder localStringBuilder2;
    if (Youku.isTablet)
    {
      str1 = "pad";
      localStringBuilder2 = localStringBuilder1.append(str1).append("&device_brand=").append(Build.BRAND).append("&ouid=");
      if (!OpenUDID_manager.isInitialized())
        break label147;
    }
    label147: for (String str2 = OpenUDID_manager.getOpenUDID(); ; str2 = "")
    {
      return str2 + "&aw=a&rst=flv" + "&adext=" + Youku.advertStr + "&version=1.0&" + getStatisticsParameter();
      str1 = "phone";
      break;
    }
  }

  public static String getVideoCommentURL(String paramString, int paramInt)
  {
    return YOUKU_DOMAIN + "videos/" + paramString + "/comments?" + getStatisticsParameter() + "&pz=" + 10 + "&pg=" + paramInt;
  }

  public static String getVideoDownloadDetailUrl(String paramString)
  {
    return YOUKU_WIRELESS_DOMAIN + "videos/" + paramString + "/v3?" + getStatisticsParameter();
  }

  public static String getVideoPointURL(String paramString)
  {
    return YOUKU_DOMAIN + "videos/" + paramString + "/points?" + getStatisticsParameter();
  }

  public static String getVideoRelateURL(String paramString)
  {
    return YOUKU_DOMAIN + "android/shows/" + paramString + "/relate?pg=1&pz=16&fields=sid|snam&" + getStatisticsParameter();
  }

  public static String getVideosImgURL(String paramString)
  {
    return YOUKU_WIRELESS_DOMAIN + "videos/batch/hd?fields=vid|imghd&" + getStatisticsParameter() + "&vids=" + paramString;
  }

  public static String getsurveyWebViewURL(Boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (paramBoolean.booleanValue())
      localStringBuilder.append("http://static.youku.com/pub/youku/fragment/panel_pad.html").append("?").append(getStatisticsParameter()).append("&appName=youkuHD");
    while (true)
    {
      localStringBuilder.append("&os=android");
      String str = Youku.getPreference("uid", "");
      if (str.length() != 0)
        localStringBuilder.append("&uid=").append(str);
      return localStringBuilder.toString().toLowerCase();
      localStringBuilder.append("http://static.youku.com/pub/youku/fragment/panel_phone.html").append("?").append(getStatisticsParameter()).append("&appName=youku");
    }
  }

  public static void init()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("pid=").append("6f81431b00e5b30a");
    if (!TextUtils.isEmpty(Youku.GUID))
      localStringBuilder.append("&guid=").append(Youku.GUID);
    localStringBuilder.append("&mac=").append(Device.MAC).append("&imei=").append(Device.IMEI).append("&ver=").append(Youku.versionName);
    initData = localStringBuilder.toString();
  }
}

/* Location:           D:\反编译\dex2jar-0.0.9.15\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.youku.http.URLContainer
 * JD-Core Version:    0.6.0
 */