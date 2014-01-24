package com.youku.http;

import android.text.TextUtils;
import com.youku.data.SQLiteManager;
import com.youku.phone.Youku;
import com.youku.phone.channel.data.ChannelBoxInfo;
import com.youku.phone.channel.data.ChannelCellInfo;
import com.youku.phone.channel.data.ChannelVideoInfo;
import com.youku.ui.activity.ChannelActivity;
import com.youku.ui.search.SearchConstant;
import com.youku.ui.search.data.SearchOtherGridviewResults;
import com.youku.util.Logger;
import com.youku.util.YoukuUtil;
import com.youku.vo.Channel;
import com.youku.vo.ChannelSortBean;
import com.youku.vo.Channels;
import com.youku.vo.EditorRecommand;
import com.youku.vo.Filter;
import com.youku.vo.HomeBean;
import com.youku.vo.HomeLike;
import com.youku.vo.HomeRecommend;
import com.youku.vo.HomeRecommend.Cell;
import com.youku.vo.HomeRecommend.HomeGroup;
import com.youku.vo.MyTag;
import com.youku.vo.Navigations;
import com.youku.vo.Navigations.Navi;
import com.youku.vo.Order;
import com.youku.vo.OtherSiteData;
import com.youku.vo.ScrollerInfoVo;
import com.youku.vo.ScrollerInfoVo.ScrollerInfo;
import com.youku.vo.ScrollerInfoVo.ScrollerInfo.GameCenterVideoInfo;
import com.youku.vo.SearchDirectAllOtherSiteEpisode;
import com.youku.vo.SearchDirectAllOtherSiteSeries;
import com.youku.vo.SearchDirectAllResult;
import com.youku.vo.SearchDirectAllSerises;
import com.youku.vo.Tags;
import com.youku.vo.UseHistoryInfo;
import com.youku.vo.UserProfile;
import com.youku.vo.UserProfile.UserProfileItems;
import com.youku.vo.UserProfile.UserProfileResult;
import com.youku.vo.VideoInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJson
{
  private Channel channel;
  protected JSONObject jsonObject;
  private String jsonString;

  public ParseJson(String paramString)
  {
    this.jsonString = paramString;
  }

  public ParseJson(String paramString, Channel paramChannel)
  {
    this.channel = paramChannel;
    this.jsonString = paramString;
  }

  private JSONArray getResultArray()
    throws JSONException
  {
    if (!TextUtils.isEmpty(this.jsonString))
    {
      this.jsonObject = new JSONObject(this.jsonString);
      if (this.jsonObject.has("results"))
        return this.jsonObject.getJSONArray("results");
    }
    return null;
  }

  private JSONObject getResultObject()
    throws JSONException
  {
    if (!TextUtils.isEmpty(this.jsonString))
    {
      this.jsonObject = new JSONObject(this.jsonString);
      if (this.jsonObject.has("results"))
        return this.jsonObject.getJSONObject("results");
    }
    return null;
  }

  private String getStringJsonObject(JSONObject paramJSONObject, String paramString)
  {
    if (paramJSONObject.has(paramString))
      return paramJSONObject.optString(paramString);
    return "";
  }

  private HomeBean[] paseHomePageData(Object paramObject)
    throws JSONException
  {
    JSONArray localJSONArray = (JSONArray)paramObject;
    HomeBean[] arrayOfHomeBean;
    if ((localJSONArray == null) || (localJSONArray.length() < 1))
      arrayOfHomeBean = null;
    while (true)
    {
      return arrayOfHomeBean;
      arrayOfHomeBean = new HomeBean[localJSONArray.length()];
      for (int i = 0; i < localJSONArray.length(); i++)
        arrayOfHomeBean[i] = paseHomepageDataOne(localJSONArray.get(i));
    }
  }

  private HomeBean paseHomepageDataOne(Object paramObject)
    throws JSONException
  {
    this.jsonObject = new JSONObject(paramObject.toString());
    HomeBean localHomeBean = new HomeBean();
    JSONArray localJSONArray1 = this.jsonObject.getJSONArray("img");
    localHomeBean.img = new String[localJSONArray1.length()];
    for (int i = 0; i < localJSONArray1.length(); i++)
      localHomeBean.img[i] = localJSONArray1.getString(i);
    JSONArray localJSONArray2 = this.jsonObject.getJSONArray("title");
    localHomeBean.title = new String[localJSONArray2.length()];
    for (int j = 0; j < localJSONArray2.length(); j++)
      localHomeBean.title[j] = localJSONArray2.getString(j);
    JSONArray localJSONArray3 = this.jsonObject.getJSONArray("tid");
    localHomeBean.tid = new String[localJSONArray3.length()];
    for (int k = 0; k < localJSONArray3.length(); k++)
      localHomeBean.tid[k] = localJSONArray3.getString(k);
    JSONArray localJSONArray4 = this.jsonObject.getJSONArray("type");
    localHomeBean.type = new int[localJSONArray4.length()];
    for (int m = 0; m < localJSONArray4.length(); m++)
      localHomeBean.type[m] = localJSONArray4.optInt(m);
    JSONArray localJSONArray5 = this.jsonObject.getJSONArray("remark");
    localHomeBean.remark = new String[localJSONArray5.length()];
    for (int n = 0; n < localJSONArray5.length(); n++)
      localHomeBean.remark[n] = localJSONArray5.getString(n);
    if (this.jsonObject.has("openurl"))
    {
      JSONArray localJSONArray6 = this.jsonObject.getJSONArray("openurl");
      localHomeBean.openurl = new String[localJSONArray6.length()];
      for (int i1 = 0; i1 < localJSONArray6.length(); i1++)
        localHomeBean.openurl[i1] = localJSONArray6.getString(i1);
    }
    if (this.jsonObject.has("playlist_id"))
      localHomeBean.playlist_id = this.jsonObject.getString("playlist_id");
    localHomeBean.img_size = this.jsonObject.optInt("img_size");
    localHomeBean.middle_stripe = this.jsonObject.optString("middle_stripe");
    localHomeBean.paid = this.jsonObject.optInt("paid", 0);
    return localHomeBean;
  }

  public boolean isAlreadyAdd()
  {
    try
    {
      this.jsonObject = new JSONObject(this.jsonString);
      boolean bool = this.jsonObject.getString("description").equals("already add");
      if (bool)
        return true;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return false;
  }

  public boolean isTooManyTags()
  {
    try
    {
      this.jsonObject = new JSONObject(this.jsonString);
      boolean bool = this.jsonObject.getString("description").equals("too many tags");
      if (bool)
        return true;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return false;
  }

  public void parseChannel(boolean paramBoolean)
  {
    while (true)
    {
      try
      {
        this.jsonObject = new JSONObject(this.jsonString);
        JSONArray localJSONArray = getResultArray();
        if (!this.jsonObject.has("total"))
          continue;
        this.channel.totalVideo = Integer.valueOf(this.jsonObject.getString("total")).intValue();
        if (!paramBoolean)
          break label243;
        this.channel.videoList.clear();
        break label243;
        if (i < localJSONArray.length())
        {
          JSONObject localJSONObject = localJSONArray.getJSONObject(i);
          VideoInfo localVideoInfo = new VideoInfo();
          localVideoInfo.stripe_bottom = localJSONObject.optString("stripe_bottom");
          localVideoInfo.stripe_middle = localJSONObject.optString("middle_stripe");
          localVideoInfo.vid = localJSONObject.optString("tid");
          localVideoInfo.starNum = (float)(localJSONObject.optDouble("reputation", 0.0D) / 2.0D);
          localVideoInfo.imageURL = localJSONObject.optString("show_thumburl_hd");
          localVideoInfo.imageVUrl = localJSONObject.optString("show_vthumburl_hd");
          localVideoInfo.title = localJSONObject.optString("showname");
          localVideoInfo.duration = localJSONObject.optString("stripe_bottom");
          localVideoInfo.rating = localJSONObject.optDouble("reputation", 0.0D);
          this.channel.videoList.add(localVideoInfo);
          i++;
          continue;
        }
      }
      catch (JSONException localJSONException)
      {
        Logger.e("ParseJson.parseChannel()", localJSONException);
      }
      return;
      label243: int i = 0;
    }
  }

  public ArrayList<ChannelCellInfo> parseChannelCellInfo()
  {
    ArrayList localArrayList1 = new ArrayList();
    while (true)
    {
      int k;
      try
      {
        this.jsonObject = new JSONObject(this.jsonString);
        if ((this.jsonObject != null) && (this.jsonObject.has("boxes")))
        {
          JSONArray localJSONArray1 = this.jsonObject.optJSONArray("boxes");
          if ((localJSONArray1 != null) && (localJSONArray1.length() > 0))
          {
            int i = 0;
            if (i < localJSONArray1.length())
            {
              JSONObject localJSONObject1 = localJSONArray1.optJSONObject(i);
              if (localJSONObject1 == null)
                continue;
              ChannelBoxInfo localChannelBoxInfo = new ChannelBoxInfo();
              localChannelBoxInfo.setTitle(localJSONObject1.optString("title"));
              localChannelBoxInfo.setFilter(localJSONObject1.optString("filter", ""));
              localChannelBoxInfo.setOb(localJSONObject1.optString("ob", ""));
              localChannelBoxInfo.setSub_channel_id_for_link(localJSONObject1.optInt("sub_channel_id_for_link"));
              if (!localJSONObject1.has("cells"))
                continue;
              JSONArray localJSONArray2 = localJSONObject1.optJSONArray("cells");
              if ((localJSONArray2 == null) || (localJSONArray2.length() <= 0))
                continue;
              int j = 0;
              if (j >= localJSONArray2.length())
                continue;
              JSONObject localJSONObject2 = localJSONArray2.optJSONObject(j);
              if (localJSONObject2 == null)
                continue;
              ChannelCellInfo localChannelCellInfo = new ChannelCellInfo();
              localChannelCellInfo.setLayout(localJSONObject2.optInt("layout"));
              localChannelCellInfo.setChannelBoxInfo(localChannelBoxInfo);
              localChannelCellInfo.setCellIndex(j);
              if (TextUtils.isEmpty(localChannelBoxInfo.getTitle()))
                break label519;
              bool = true;
              localChannelCellInfo.setShowBoxTitle(bool);
              if (!localJSONObject2.has("contents"))
                continue;
              JSONArray localJSONArray3 = localJSONObject2.optJSONArray("contents");
              if ((localJSONArray3 == null) || (localJSONArray3.length() <= 0))
                continue;
              ArrayList localArrayList2 = new ArrayList();
              k = 0;
              if (k >= localJSONArray3.length())
                continue;
              JSONObject localJSONObject3 = localJSONArray3.optJSONObject(k);
              if (localJSONObject3 == null)
                break label513;
              ChannelVideoInfo localChannelVideoInfo = new ChannelVideoInfo();
              localChannelVideoInfo.setStripe(localJSONObject3.optString("stripe"));
              localChannelVideoInfo.setPaid(localJSONObject3.optInt("paid"));
              localChannelVideoInfo.setImg(localJSONObject3.optString("img"));
              localChannelVideoInfo.setTitle(localJSONObject3.optString("title"));
              localChannelVideoInfo.setSubtitle(localJSONObject3.optString("subtitle"));
              localChannelVideoInfo.setTid(localJSONObject3.optString("tid"));
              localChannelVideoInfo.setType(localJSONObject3.optInt("type"));
              localChannelVideoInfo.setUrl(localJSONObject3.optString("url"));
              localChannelVideoInfo.setPlaylistid(localJSONObject3.optString("playlistid"));
              localArrayList2.add(localChannelVideoInfo);
              break label513;
              localChannelCellInfo.setVideos(localArrayList2);
              localArrayList1.add(localChannelCellInfo);
              j++;
              continue;
              i++;
              continue;
            }
          }
        }
      }
      catch (Exception localException)
      {
        Logger.e("Youku", "ParseJson#parseChannelCellInfo()", localException);
      }
      return localArrayList1;
      label513: k++;
      continue;
      label519: boolean bool = false;
    }
  }

  public Channels parseChannelData()
  {
    Channels localChannels = new Channels();
    try
    {
      this.jsonObject = new JSONObject(this.jsonString);
      JSONObject localJSONObject1 = this.jsonObject.getJSONObject("channels");
      localChannels.title = localJSONObject1.getString("title");
      JSONArray localJSONArray = localJSONObject1.getJSONArray("tags");
      int i = 0;
      if (i < localJSONArray.length())
      {
        JSONObject localJSONObject2 = localJSONArray.getJSONObject(i);
        String str1 = localJSONObject2.optString("title");
        String str2 = localJSONObject2.optString("tid");
        boolean bool = localJSONObject2.optBoolean("is_favorite", false);
        int j = Integer.parseInt(localJSONObject2.optString("color"));
        String str3 = localJSONObject2.optString("icon");
        String str4 = localJSONObject2.optString("favorite_icon");
        int k = localJSONObject2.optInt("type");
        Channel localChannel = new Channel(str1);
        localChannel.channelCid = str2;
        localChannel.color = j;
        localChannel.icon = str3;
        localChannel.type = k;
        localChannel.isFav = bool;
        if (bool);
        for (int m = 1; ; m = 0)
        {
          localChannel.isFavInt = m;
          localChannel.favIcon = str4;
          localChannels.channels.add(localChannel);
          i++;
          break;
        }
      }
    }
    catch (JSONException localJSONException)
    {
      Logger.d("parseMyTagData", localJSONException);
    }
    return localChannels;
  }

  public void parseChannelFilterOrderData(ArrayList<Filter> paramArrayList, ArrayList<Order> paramArrayList1)
  {
    while (true)
    {
      int j;
      int k;
      try
      {
        JSONObject localJSONObject1 = getResultObject();
        JSONArray localJSONArray1 = localJSONObject1.getJSONArray("filter");
        if (localJSONArray1 == null)
          continue;
        j = 0;
        if (j >= localJSONArray1.length())
          continue;
        JSONObject localJSONObject3 = localJSONArray1.optJSONObject(j);
        if (localJSONObject3 == null)
          break label286;
        Filter localFilter = new Filter();
        localFilter.cat = localJSONObject3.optString("cat");
        localFilter.title = localJSONObject3.optString("title");
        JSONArray localJSONArray3 = localJSONObject3.optJSONArray("items");
        if (localJSONArray3 == null)
          continue;
        k = 0;
        if (k >= localJSONArray3.length())
          continue;
        JSONObject localJSONObject4 = localJSONArray3.optJSONObject(k);
        if (localJSONObject4 == null)
          break label280;
        Order localOrder2 = new Order();
        localOrder2.title = localJSONObject4.optString("title");
        localOrder2.value = localJSONObject4.optString("value");
        localFilter.orders.add(localOrder2);
        break label280;
        paramArrayList.add(localFilter);
        break label286;
        JSONArray localJSONArray2 = localJSONObject1.getJSONArray("sort");
        if (localJSONArray2 != null)
        {
          int i = 0;
          if (i < localJSONArray2.length())
          {
            JSONObject localJSONObject2 = localJSONArray2.optJSONObject(i);
            if (localJSONObject2 == null)
              continue;
            Order localOrder1 = new Order();
            localOrder1.title = localJSONObject2.optString("title");
            localOrder1.value = localJSONObject2.optString("value");
            paramArrayList1.add(localOrder1);
            i++;
            continue;
          }
        }
      }
      catch (JSONException localJSONException)
      {
        Logger.e("ParseJson.parseChannelFilterOrderData()", localJSONException);
      }
      return;
      label280: k++;
      continue;
      label286: j++;
    }
  }

  public ChannelSortBean parseChannelSortData()
  {
    ChannelSortBean localChannelSortBean = new ChannelSortBean();
    try
    {
      localChannelSortBean.channelBean = parseChannelData();
      if (this.jsonObject == null)
        return localChannelSortBean;
      JSONObject localJSONObject1 = this.jsonObject.getJSONObject("recommend");
      EditorRecommand localEditorRecommand = new EditorRecommand();
      localEditorRecommand.title = localJSONObject1.getString("title");
      JSONArray localJSONArray = localJSONObject1.getJSONArray("tags");
      for (int i = 0; i < localJSONArray.length(); i++)
      {
        JSONObject localJSONObject2 = localJSONArray.getJSONObject(i);
        String str1 = localJSONObject2.optString("title");
        String str2 = localJSONObject2.optString("tid");
        String str3 = localJSONObject2.optString("icon");
        int j = localJSONObject2.optInt("type");
        int k = localJSONObject2.optInt("channel_id");
        String str4 = localJSONObject2.optString("playlist_first_video_id");
        Tags localTags = new Tags();
        localTags.icon = str3;
        localTags.tid = str2;
        localTags.title = str1;
        localTags.type = j;
        localTags.channelId = k;
        localTags.playlistFirstVid = str4;
        localEditorRecommand.tags.add(localTags);
      }
      localChannelSortBean.recommandBean = localEditorRecommand;
      localChannelSortBean.tagBean = parseMyTagData();
      return localChannelSortBean;
    }
    catch (Exception localException)
    {
      Logger.d("parseChannelSortData", localException);
    }
    return localChannelSortBean;
  }

  // ERROR //
  public ArrayList<com.youku.phone.channel.data.ChannelTabInfo> parseChannelTabInfo()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: new 32	org/json/JSONObject
    //   6: dup
    //   7: aload_0
    //   8: getfield 17	com/youku/http/ParseJson:jsonString	Ljava/lang/String;
    //   11: invokespecial 34	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   14: putfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   17: aload_0
    //   18: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   21: astore_3
    //   22: aconst_null
    //   23: astore_1
    //   24: aload_3
    //   25: ifnull +411 -> 436
    //   28: aload_0
    //   29: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   32: ldc 38
    //   34: invokevirtual 42	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   37: istore 4
    //   39: aconst_null
    //   40: astore_1
    //   41: iload 4
    //   43: ifeq +393 -> 436
    //   46: aload_0
    //   47: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   50: ldc 38
    //   52: invokevirtual 261	org/json/JSONObject:optJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   55: astore 5
    //   57: aconst_null
    //   58: astore_1
    //   59: aload 5
    //   61: ifnull +375 -> 436
    //   64: aload 5
    //   66: invokevirtual 68	org/json/JSONArray:length	()I
    //   69: istore 6
    //   71: aconst_null
    //   72: astore_1
    //   73: iload 6
    //   75: ifle +361 -> 436
    //   78: new 255	java/util/ArrayList
    //   81: dup
    //   82: invokespecial 256	java/util/ArrayList:<init>	()V
    //   85: astore 7
    //   87: iconst_0
    //   88: istore 8
    //   90: iload 8
    //   92: aload 5
    //   94: invokevirtual 68	org/json/JSONArray:length	()I
    //   97: if_icmpge +348 -> 445
    //   100: aload 5
    //   102: iload 8
    //   104: invokevirtual 264	org/json/JSONArray:optJSONObject	(I)Lorg/json/JSONObject;
    //   107: astore 9
    //   109: aload 9
    //   111: ifnull +308 -> 419
    //   114: new 496	com/youku/phone/channel/data/ChannelTabInfo
    //   117: dup
    //   118: invokespecial 497	com/youku/phone/channel/data/ChannelTabInfo:<init>	()V
    //   121: astore 10
    //   123: aload 10
    //   125: aload 9
    //   127: ldc_w 499
    //   130: invokevirtual 130	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   133: invokevirtual 502	com/youku/phone/channel/data/ChannelTabInfo:setSub_channel_id	(I)V
    //   136: aload 10
    //   138: aload 9
    //   140: ldc_w 504
    //   143: invokevirtual 130	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   146: invokevirtual 507	com/youku/phone/channel/data/ChannelTabInfo:setSub_channel_type	(I)V
    //   149: aload 10
    //   151: aload 9
    //   153: ldc_w 509
    //   156: invokevirtual 130	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   159: invokevirtual 512	com/youku/phone/channel/data/ChannelTabInfo:setImage_state	(I)V
    //   162: aload 10
    //   164: aload 9
    //   166: ldc_w 514
    //   169: invokevirtual 130	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   172: invokevirtual 517	com/youku/phone/channel/data/ChannelTabInfo:setDisplay_type	(I)V
    //   175: aload 10
    //   177: aload 9
    //   179: ldc 96
    //   181: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   184: invokevirtual 518	com/youku/phone/channel/data/ChannelTabInfo:setTitle	(Ljava/lang/String;)V
    //   187: aload 10
    //   189: aload 9
    //   191: ldc_w 520
    //   194: invokevirtual 130	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   197: invokevirtual 523	com/youku/phone/channel/data/ChannelTabInfo:setHighlight	(I)V
    //   200: aload 10
    //   202: aload 9
    //   204: ldc_w 272
    //   207: ldc 60
    //   209: invokevirtual 275	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   212: invokevirtual 524	com/youku/phone/channel/data/ChannelTabInfo:setFilter	(Ljava/lang/String;)V
    //   215: aload 10
    //   217: aload 9
    //   219: ldc_w 280
    //   222: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   225: invokevirtual 525	com/youku/phone/channel/data/ChannelTabInfo:setOb	(Ljava/lang/String;)V
    //   228: iconst_1
    //   229: aload 10
    //   231: invokevirtual 528	com/youku/phone/channel/data/ChannelTabInfo:getDisplay_type	()I
    //   234: if_icmpne +177 -> 411
    //   237: aload 9
    //   239: ldc_w 530
    //   242: invokevirtual 42	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   245: ifeq +166 -> 411
    //   248: aload 9
    //   250: ldc_w 530
    //   253: invokevirtual 261	org/json/JSONObject:optJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   256: astore 12
    //   258: aload 12
    //   260: ifnull +151 -> 411
    //   263: aload 12
    //   265: invokevirtual 68	org/json/JSONArray:length	()I
    //   268: ifle +143 -> 411
    //   271: new 255	java/util/ArrayList
    //   274: dup
    //   275: invokespecial 256	java/util/ArrayList:<init>	()V
    //   278: astore 13
    //   280: iconst_0
    //   281: istore 14
    //   283: iload 14
    //   285: aload 12
    //   287: invokevirtual 68	org/json/JSONArray:length	()I
    //   290: if_icmpge +114 -> 404
    //   293: aload 12
    //   295: iload 14
    //   297: invokevirtual 264	org/json/JSONArray:optJSONObject	(I)Lorg/json/JSONObject;
    //   300: astore 15
    //   302: aload 15
    //   304: ifnull +144 -> 448
    //   307: new 496	com/youku/phone/channel/data/ChannelTabInfo
    //   310: dup
    //   311: invokespecial 497	com/youku/phone/channel/data/ChannelTabInfo:<init>	()V
    //   314: astore 16
    //   316: aload 16
    //   318: aload 15
    //   320: ldc_w 499
    //   323: invokevirtual 130	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   326: invokevirtual 502	com/youku/phone/channel/data/ChannelTabInfo:setSub_channel_id	(I)V
    //   329: aload 16
    //   331: aload 15
    //   333: ldc_w 504
    //   336: invokevirtual 130	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   339: invokevirtual 507	com/youku/phone/channel/data/ChannelTabInfo:setSub_channel_type	(I)V
    //   342: aload 16
    //   344: aload 15
    //   346: ldc_w 509
    //   349: invokevirtual 130	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   352: invokevirtual 512	com/youku/phone/channel/data/ChannelTabInfo:setImage_state	(I)V
    //   355: aload 16
    //   357: aload 15
    //   359: ldc_w 514
    //   362: invokevirtual 130	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   365: invokevirtual 517	com/youku/phone/channel/data/ChannelTabInfo:setDisplay_type	(I)V
    //   368: aload 16
    //   370: aload 15
    //   372: ldc 96
    //   374: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   377: invokevirtual 518	com/youku/phone/channel/data/ChannelTabInfo:setTitle	(Ljava/lang/String;)V
    //   380: aload 16
    //   382: aload 15
    //   384: ldc_w 532
    //   387: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   390: invokevirtual 535	com/youku/phone/channel/data/ChannelTabInfo:setSudoku_image	(Ljava/lang/String;)V
    //   393: aload 13
    //   395: aload 16
    //   397: invokevirtual 351	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   400: pop
    //   401: goto +47 -> 448
    //   404: aload 10
    //   406: aload 13
    //   408: invokevirtual 538	com/youku/phone/channel/data/ChannelTabInfo:setSub_tabs	(Ljava/util/ArrayList;)V
    //   411: aload 7
    //   413: aload 10
    //   415: invokevirtual 351	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   418: pop
    //   419: iinc 8 1
    //   422: goto -332 -> 90
    //   425: astore_2
    //   426: ldc_w 357
    //   429: ldc_w 540
    //   432: aload_2
    //   433: invokestatic 362	com/youku/util/Logger:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   436: aload_1
    //   437: areturn
    //   438: astore_2
    //   439: aload 7
    //   441: astore_1
    //   442: goto -16 -> 426
    //   445: aload 7
    //   447: areturn
    //   448: iinc 14 1
    //   451: goto -168 -> 283
    //
    // Exception table:
    //   from	to	target	type
    //   2	22	425	java/lang/Exception
    //   28	39	425	java/lang/Exception
    //   46	57	425	java/lang/Exception
    //   64	71	425	java/lang/Exception
    //   78	87	425	java/lang/Exception
    //   90	109	438	java/lang/Exception
    //   114	258	438	java/lang/Exception
    //   263	280	438	java/lang/Exception
    //   283	302	438	java/lang/Exception
    //   307	401	438	java/lang/Exception
    //   404	411	438	java/lang/Exception
    //   411	419	438	java/lang/Exception
  }

  // ERROR //
  public com.youku.phone.channel.data.ChannelVideoListInfo parseChannelVideoListInfo()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: new 32	org/json/JSONObject
    //   6: dup
    //   7: aload_0
    //   8: getfield 17	com/youku/http/ParseJson:jsonString	Ljava/lang/String;
    //   11: invokespecial 34	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   14: putfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   17: aload_0
    //   18: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   21: astore_3
    //   22: aconst_null
    //   23: astore_1
    //   24: aload_3
    //   25: ifnull +240 -> 265
    //   28: new 544	com/youku/phone/channel/data/ChannelVideoListInfo
    //   31: dup
    //   32: invokespecial 545	com/youku/phone/channel/data/ChannelVideoListInfo:<init>	()V
    //   35: astore 4
    //   37: aload 4
    //   39: aload_0
    //   40: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   43: ldc 166
    //   45: invokevirtual 130	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   48: invokevirtual 548	com/youku/phone/channel/data/ChannelVideoListInfo:setTotal	(I)V
    //   51: aload_0
    //   52: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   55: ldc 38
    //   57: invokevirtual 42	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   60: ifeq +202 -> 262
    //   63: aload_0
    //   64: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   67: ldc 38
    //   69: invokevirtual 261	org/json/JSONObject:optJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   72: astore 5
    //   74: aload 5
    //   76: ifnull +186 -> 262
    //   79: aload 5
    //   81: invokevirtual 68	org/json/JSONArray:length	()I
    //   84: ifle +178 -> 262
    //   87: new 255	java/util/ArrayList
    //   90: dup
    //   91: invokespecial 256	java/util/ArrayList:<init>	()V
    //   94: astore 6
    //   96: iconst_0
    //   97: istore 7
    //   99: iload 7
    //   101: aload 5
    //   103: invokevirtual 68	org/json/JSONArray:length	()I
    //   106: if_icmpge +149 -> 255
    //   109: aload 5
    //   111: iload 7
    //   113: invokevirtual 264	org/json/JSONArray:optJSONObject	(I)Lorg/json/JSONObject;
    //   116: astore 8
    //   118: aload 8
    //   120: ifnull +167 -> 287
    //   123: new 316	com/youku/phone/channel/data/ChannelVideoInfo
    //   126: dup
    //   127: invokespecial 317	com/youku/phone/channel/data/ChannelVideoInfo:<init>	()V
    //   130: astore 9
    //   132: aload 9
    //   134: aload 8
    //   136: ldc_w 319
    //   139: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   142: invokevirtual 322	com/youku/phone/channel/data/ChannelVideoInfo:setStripe	(Ljava/lang/String;)V
    //   145: aload 9
    //   147: aload 8
    //   149: ldc 139
    //   151: invokevirtual 130	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   154: invokevirtual 325	com/youku/phone/channel/data/ChannelVideoInfo:setPaid	(I)V
    //   157: aload 9
    //   159: aload 8
    //   161: ldc 85
    //   163: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   166: invokevirtual 328	com/youku/phone/channel/data/ChannelVideoInfo:setImg	(Ljava/lang/String;)V
    //   169: aload 9
    //   171: aload 8
    //   173: ldc 96
    //   175: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   178: invokevirtual 329	com/youku/phone/channel/data/ChannelVideoInfo:setTitle	(Ljava/lang/String;)V
    //   181: aload 9
    //   183: aload 8
    //   185: ldc_w 331
    //   188: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   191: invokevirtual 334	com/youku/phone/channel/data/ChannelVideoInfo:setSubtitle	(Ljava/lang/String;)V
    //   194: aload 9
    //   196: aload 8
    //   198: ldc 100
    //   200: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   203: invokevirtual 337	com/youku/phone/channel/data/ChannelVideoInfo:setTid	(Ljava/lang/String;)V
    //   206: aload 9
    //   208: aload 8
    //   210: ldc 104
    //   212: invokevirtual 130	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   215: invokevirtual 340	com/youku/phone/channel/data/ChannelVideoInfo:setType	(I)V
    //   218: aload 9
    //   220: aload 8
    //   222: ldc_w 342
    //   225: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   228: invokevirtual 345	com/youku/phone/channel/data/ChannelVideoInfo:setUrl	(Ljava/lang/String;)V
    //   231: aload 9
    //   233: aload 8
    //   235: ldc_w 347
    //   238: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   241: invokevirtual 350	com/youku/phone/channel/data/ChannelVideoInfo:setPlaylistid	(Ljava/lang/String;)V
    //   244: aload 6
    //   246: aload 9
    //   248: invokevirtual 351	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   251: pop
    //   252: goto +35 -> 287
    //   255: aload 4
    //   257: aload 6
    //   259: invokevirtual 551	com/youku/phone/channel/data/ChannelVideoListInfo:setChannelVideoInfos	(Ljava/util/ArrayList;)V
    //   262: aload 4
    //   264: astore_1
    //   265: aload_1
    //   266: areturn
    //   267: astore_2
    //   268: ldc_w 357
    //   271: ldc_w 553
    //   274: aload_2
    //   275: invokestatic 362	com/youku/util/Logger:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   278: aload_1
    //   279: areturn
    //   280: astore_2
    //   281: aload 4
    //   283: astore_1
    //   284: goto -16 -> 268
    //   287: iinc 7 1
    //   290: goto -191 -> 99
    //
    // Exception table:
    //   from	to	target	type
    //   2	22	267	java/lang/Exception
    //   28	37	267	java/lang/Exception
    //   37	74	280	java/lang/Exception
    //   79	96	280	java/lang/Exception
    //   99	118	280	java/lang/Exception
    //   123	252	280	java/lang/Exception
    //   255	262	280	java/lang/Exception
  }

  // ERROR //
  public com.youku.alipay.entity.DoPayData parseDoPayData()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: new 32	org/json/JSONObject
    //   6: dup
    //   7: aload_0
    //   8: getfield 17	com/youku/http/ParseJson:jsonString	Ljava/lang/String;
    //   11: invokespecial 34	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   14: putfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   17: aload_0
    //   18: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   21: astore_3
    //   22: aconst_null
    //   23: astore_1
    //   24: aload_3
    //   25: ifnull +70 -> 95
    //   28: aload_0
    //   29: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   32: ldc 38
    //   34: invokevirtual 42	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   37: istore 4
    //   39: aconst_null
    //   40: astore_1
    //   41: iload 4
    //   43: ifeq +52 -> 95
    //   46: aload_0
    //   47: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   50: ldc 38
    //   52: invokevirtual 52	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   55: astore 5
    //   57: new 557	com/youku/alipay/entity/DoPayData
    //   60: dup
    //   61: invokespecial 558	com/youku/alipay/entity/DoPayData:<init>	()V
    //   64: astore 6
    //   66: aload 6
    //   68: aload 5
    //   70: ldc_w 560
    //   73: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   76: invokevirtual 563	com/youku/alipay/entity/DoPayData:setChannel_params	(Ljava/lang/String;)V
    //   79: aload 6
    //   81: aload 5
    //   83: ldc_w 565
    //   86: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   89: invokevirtual 568	com/youku/alipay/entity/DoPayData:setTrade_id	(Ljava/lang/String;)V
    //   92: aload 6
    //   94: astore_1
    //   95: aload_1
    //   96: areturn
    //   97: astore_2
    //   98: ldc_w 357
    //   101: ldc_w 570
    //   104: aload_2
    //   105: invokestatic 362	com/youku/util/Logger:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   108: aload_1
    //   109: areturn
    //   110: astore_2
    //   111: aload 6
    //   113: astore_1
    //   114: goto -16 -> 98
    //
    // Exception table:
    //   from	to	target	type
    //   2	22	97	java/lang/Exception
    //   28	39	97	java/lang/Exception
    //   46	66	97	java/lang/Exception
    //   66	92	110	java/lang/Exception
  }

  public void parseFav(boolean paramBoolean)
  {
    while (true)
    {
      try
      {
        JSONArray localJSONArray = getResultArray();
        if (((this.channel.totalVideo != -1) && (!paramBoolean)) || (!this.jsonObject.has("total")))
          break label208;
        this.channel.totalVideo = Integer.valueOf(this.jsonObject.getString("total")).intValue();
        break label208;
        if (i < localJSONArray.length())
        {
          JSONObject localJSONObject = localJSONArray.getJSONObject(i);
          VideoInfo localVideoInfo = new VideoInfo();
          localVideoInfo.starNum = (float)(localJSONObject.optDouble("reputation", 0.0D) / 2.0D);
          localVideoInfo.imageURL = localJSONObject.optString("img_hd");
          localVideoInfo.title = localJSONObject.optString("title");
          localVideoInfo.duration = localJSONObject.optString("duration");
          localVideoInfo.vid = localJSONObject.optString("videoid");
          localVideoInfo.state = localJSONObject.optInt("state");
          localVideoInfo.paid = localJSONObject.optInt("paid", 0);
          this.channel.videoList.add(localVideoInfo);
          i++;
          continue;
        }
      }
      catch (JSONException localJSONException)
      {
        Logger.e("ParseJson.parseFav()", localJSONException);
      }
      return;
      label208: int i = 0;
    }
  }

  public void parseFilterAndOrder()
  {
    while (true)
    {
      int i;
      int k;
      try
      {
        ChannelActivity.filters.clear();
        ChannelActivity.orders.clear();
        JSONObject localJSONObject1 = getResultObject();
        JSONArray localJSONArray1 = localJSONObject1.getJSONArray("filter");
        i = 0;
        if (i >= localJSONArray1.length())
          continue;
        JSONObject localJSONObject3 = localJSONArray1.optJSONObject(i);
        if (localJSONObject3 == null)
          break label289;
        Filter localFilter = new Filter();
        localFilter.cat = localJSONObject3.optString("cat");
        localFilter.title = localJSONObject3.optString("title");
        JSONArray localJSONArray3 = localJSONObject3.optJSONArray("items");
        if (localJSONArray3 == null)
          continue;
        k = 0;
        if (k >= localJSONArray3.length())
          continue;
        JSONObject localJSONObject4 = localJSONArray3.optJSONObject(k);
        if (localJSONObject4 == null)
          break label283;
        Order localOrder2 = new Order();
        localOrder2.title = localJSONObject4.optString("title");
        localOrder2.value = localJSONObject4.optString("value");
        localFilter.orders.add(localOrder2);
        break label283;
        ChannelActivity.filters.add(localFilter);
        break label289;
        JSONArray localJSONArray2 = localJSONObject1.getJSONArray("sort");
        int j = 0;
        if (j < localJSONArray2.length())
        {
          JSONObject localJSONObject2 = localJSONArray2.optJSONObject(j);
          if (localJSONObject2 == null)
            continue;
          Order localOrder1 = new Order();
          localOrder1.title = localJSONObject2.optString("title");
          localOrder1.value = String.valueOf(localJSONObject2.optInt("value"));
          ChannelActivity.orders.add(localOrder1);
          j++;
          continue;
        }
      }
      catch (JSONException localJSONException)
      {
        Logger.e("ParseJson.parseFilterAndOrder()", localJSONException);
      }
      return;
      label283: k++;
      continue;
      label289: i++;
    }
  }

  public HomeLike parseHomePage()
  {
    HomeLike localHomeLike = new HomeLike();
    try
    {
      this.jsonObject = new JSONObject(this.jsonString);
      JSONArray localJSONArray = this.jsonObject.getJSONArray("results");
      localHomeLike.results = new ArrayList();
      for (int i = 0; i < localJSONArray.length(); i++)
      {
        HomeBean[] arrayOfHomeBean = paseHomePageData(localJSONArray.get(i));
        localHomeLike.results.add(arrayOfHomeBean);
      }
    }
    catch (Exception localException)
    {
      Logger.e("Youku", "ParseJson#parseHomePage()", localException);
    }
    return localHomeLike;
  }

  public HomeRecommend parseHomePageRemmend()
  {
    HomeRecommend localHomeRecommend = new HomeRecommend();
    while (true)
    {
      int i;
      HomeRecommend.HomeGroup localHomeGroup;
      int k;
      try
      {
        this.jsonObject = new JSONObject(this.jsonString);
        if (!this.jsonObject.has("nav"))
          continue;
        JSONArray localJSONArray9 = this.jsonObject.getJSONArray("nav");
        localHomeRecommend.nav = new Navigations();
        localHomeRecommend.nav.results = new ArrayList();
        int i14 = 0;
        int i15 = localJSONArray9.length();
        if (i14 >= i15)
          continue;
        JSONObject localJSONObject4 = localJSONArray9.getJSONObject(i14);
        Navigations localNavigations = localHomeRecommend.nav;
        localNavigations.getClass();
        Navigations.Navi localNavi = new Navigations.Navi(localNavigations);
        localNavi.icon = localJSONObject4.optString("icon");
        localNavi.title = localJSONObject4.optString("title");
        localHomeRecommend.nav.results.add(localNavi);
        i14++;
        continue;
        if (!this.jsonObject.has("scroller"))
          continue;
        localHomeRecommend.hasScollerInfo = true;
        JSONArray localJSONArray3 = this.jsonObject.optJSONObject("scroller").optJSONArray("contents");
        localHomeRecommend.scrollerInfoVo = new ScrollerInfoVo();
        localHomeRecommend.scrollerInfoVo.scrollerList = new ArrayList();
        int n = 0;
        int i1 = localJSONArray3.length();
        if (n >= i1)
          continue;
        JSONObject localJSONObject2 = localJSONArray3.getJSONObject(n);
        ScrollerInfoVo localScrollerInfoVo = localHomeRecommend.scrollerInfoVo;
        localScrollerInfoVo.getClass();
        ScrollerInfoVo.ScrollerInfo localScrollerInfo = new ScrollerInfoVo.ScrollerInfo(localScrollerInfoVo);
        JSONArray localJSONArray4 = localJSONObject2.getJSONArray("img");
        localScrollerInfo.videoImage = new String[localJSONArray4.length()];
        int i2 = 0;
        int i3 = localJSONArray4.length();
        if (i2 >= i3)
          continue;
        localScrollerInfo.videoImage[i2] = localJSONArray4.getString(i2);
        i2++;
        continue;
        JSONArray localJSONArray5 = localJSONObject2.getJSONArray("title");
        localScrollerInfo.videoTitle = new String[localJSONArray5.length()];
        int i4 = 0;
        int i5 = localJSONArray5.length();
        if (i4 >= i5)
          continue;
        localScrollerInfo.videoTitle[i4] = localJSONArray5.getString(i4);
        i4++;
        continue;
        JSONArray localJSONArray6 = localJSONObject2.getJSONArray("tid");
        localScrollerInfo.tidVideo = new String[localJSONArray6.length()];
        int i6 = 0;
        int i7 = localJSONArray6.length();
        if (i6 >= i7)
          continue;
        localScrollerInfo.tidVideo[i6] = localJSONArray6.getString(i6);
        i6++;
        continue;
        JSONArray localJSONArray7 = localJSONObject2.getJSONArray("remark");
        localScrollerInfo.videoRemark = new String[localJSONArray7.length()];
        int i8 = 0;
        int i9 = localJSONArray7.length();
        if (i8 >= i9)
          continue;
        localScrollerInfo.videoRemark[i8] = localJSONArray7.getString(i8);
        i8++;
        continue;
        if (!localJSONObject2.has("openurl"))
          continue;
        JSONArray localJSONArray8 = localJSONObject2.getJSONArray("openurl");
        localScrollerInfo.openUrl = new String[localJSONArray8.length()];
        int i12 = 0;
        int i13 = localJSONArray8.length();
        if (i12 >= i13)
          continue;
        localScrollerInfo.openUrl[i12] = localJSONArray8.getString(i12);
        i12++;
        continue;
        if (!localJSONObject2.has("game_information"))
          continue;
        localScrollerInfo.getClass();
        ScrollerInfoVo.ScrollerInfo.GameCenterVideoInfo localGameCenterVideoInfo = new ScrollerInfoVo.ScrollerInfo.GameCenterVideoInfo(localScrollerInfo);
        localScrollerInfo.gameCenterVideoInfo = localGameCenterVideoInfo;
        JSONObject localJSONObject3 = localJSONObject2.getJSONObject("game_information");
        localScrollerInfo.gameCenterVideoInfo.game_version_name = getStringJsonObject(localJSONObject3, "game_version_name");
        localScrollerInfo.gameCenterVideoInfo.game_version_code = getStringJsonObject(localJSONObject3, "game_version_code");
        localScrollerInfo.gameCenterVideoInfo.game_description = getStringJsonObject(localJSONObject3, "game_description");
        localScrollerInfo.gameCenterVideoInfo.game_logo = getStringJsonObject(localJSONObject3, "game_logo");
        localScrollerInfo.gameCenterVideoInfo.game_name = getStringJsonObject(localJSONObject3, "game_name");
        localScrollerInfo.gameCenterVideoInfo.game_package_name = getStringJsonObject(localJSONObject3, "game_package_name");
        localScrollerInfo.gameCenterVideoInfo.game_url = getStringJsonObject(localJSONObject3, "game_url");
        localScrollerInfo.gameCenterVideoInfo.game_id = getStringJsonObject(localJSONObject3, "game_id");
        localScrollerInfo.gameCenterVideoInfo.game_type_name = getStringJsonObject(localJSONObject3, "game_type_name");
        if (!localJSONObject2.has("playlist_id"))
          continue;
        localScrollerInfo.playlist_id = localJSONObject2.getString("playlist_id");
        if (!localJSONObject2.has("url"))
          continue;
        localScrollerInfo.app_url = new String[localJSONObject2.getJSONArray("url").length()];
        int i10 = 0;
        int i11 = localJSONArray4.length();
        if (i10 >= i11)
          continue;
        localScrollerInfo.app_url[i10] = localJSONArray4.getString(i10);
        i10++;
        continue;
        localScrollerInfo.paidVideo = localJSONObject2.optInt("paid", 0);
        localScrollerInfo.typeVideo = localJSONObject2.optInt("type", 0);
        localHomeRecommend.scrollerInfoVo.scrollerList.add(localScrollerInfo);
        n++;
        continue;
        localHomeRecommend.hasScollerInfo = false;
        JSONArray localJSONArray1 = this.jsonObject.getJSONArray("results");
        localHomeRecommend.results = new ArrayList();
        i = 0;
        int j = localJSONArray1.length();
        if (i < j)
        {
          localHomeRecommend.getClass();
          localHomeGroup = new HomeRecommend.HomeGroup(localHomeRecommend);
          JSONObject localJSONObject1 = (JSONObject)localJSONArray1.get(i);
          JSONArray localJSONArray2 = localJSONObject1.getJSONArray("cells");
          localHomeGroup.group = localJSONObject1.optString("group");
          localHomeGroup.cid = localJSONObject1.optString("cid");
          localHomeGroup.cells = new ArrayList();
          k = 0;
          int m = localJSONArray2.length();
          if (k >= m)
            break label1121;
          localHomeRecommend.getClass();
          HomeRecommend.Cell localCell = new HomeRecommend.Cell(localHomeRecommend);
          localCell.cell = paseHomePageData(localJSONArray2.get(k));
          if (localCell.cell == null)
            break label1148;
          localHomeGroup.cells.add(localCell);
        }
      }
      catch (Exception localException)
      {
        Logger.e("Youku", "ParseJson#parseHomePageRemmend()", localException);
      }
      return localHomeRecommend;
      label1121: if (localHomeGroup.cells.size() > 0)
        localHomeRecommend.results.add(localHomeGroup);
      i++;
      continue;
      label1148: k++;
    }
  }

  public MyTag parseMyTagData()
  {
    MyTag localMyTag = new MyTag();
    try
    {
      this.jsonObject = new JSONObject(this.jsonString);
      JSONObject localJSONObject1 = this.jsonObject.getJSONObject("mytags");
      localMyTag.title = localJSONObject1.getString("title");
      JSONArray localJSONArray = localJSONObject1.getJSONArray("tags");
      for (int i = 0; i < localJSONArray.length(); i++)
      {
        JSONObject localJSONObject2 = localJSONArray.getJSONObject(i);
        String str1 = localJSONObject2.optString("title");
        String str2 = localJSONObject2.optString("tid");
        String str3 = localJSONObject2.optString("icon");
        int j = localJSONObject2.optInt("type");
        int k = localJSONObject2.optInt("color");
        Tags localTags = new Tags();
        localTags.icon = str3;
        localTags.tid = str2;
        localTags.title = str1;
        localTags.type = j;
        localTags.color = k;
        localMyTag.tags.add(localTags);
      }
      localMyTag.tags.add(0, new Tags());
      return localMyTag;
    }
    catch (JSONException localJSONException)
    {
      Logger.d("parseMyTagData", localJSONException);
    }
    return localMyTag;
  }

  // ERROR //
  public com.youku.vo.MyYoukuDataInfo parseMyYoukuData()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: new 32	org/json/JSONObject
    //   6: dup
    //   7: aload_0
    //   8: getfield 17	com/youku/http/ParseJson:jsonString	Ljava/lang/String;
    //   11: invokespecial 34	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   14: putfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   17: aload_0
    //   18: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   21: astore_3
    //   22: aconst_null
    //   23: astore_1
    //   24: aload_3
    //   25: ifnull +230 -> 255
    //   28: new 770	com/youku/vo/MyYoukuDataInfo
    //   31: dup
    //   32: invokespecial 771	com/youku/vo/MyYoukuDataInfo:<init>	()V
    //   35: astore 4
    //   37: aload 4
    //   39: aload_0
    //   40: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   43: ldc_w 773
    //   46: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   49: invokevirtual 776	com/youku/vo/MyYoukuDataInfo:setFavorites_count	(Ljava/lang/String;)V
    //   52: aload 4
    //   54: aload_0
    //   55: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   58: ldc_w 778
    //   61: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   64: invokevirtual 781	com/youku/vo/MyYoukuDataInfo:setVideos_count	(Ljava/lang/String;)V
    //   67: aload 4
    //   69: aload_0
    //   70: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   73: ldc_w 783
    //   76: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   79: invokevirtual 786	com/youku/vo/MyYoukuDataInfo:setAvatar	(Ljava/lang/String;)V
    //   82: aload 4
    //   84: aload_0
    //   85: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   88: ldc_w 788
    //   91: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   94: invokevirtual 791	com/youku/vo/MyYoukuDataInfo:setHistory_count	(Ljava/lang/String;)V
    //   97: aload 4
    //   99: aload_0
    //   100: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   103: ldc_w 793
    //   106: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   109: invokevirtual 796	com/youku/vo/MyYoukuDataInfo:setPay_count	(Ljava/lang/String;)V
    //   112: aload 4
    //   114: aload_0
    //   115: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   118: ldc_w 798
    //   121: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   124: invokevirtual 801	com/youku/vo/MyYoukuDataInfo:setNickname	(Ljava/lang/String;)V
    //   127: aload_0
    //   128: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   131: ldc_w 803
    //   134: invokevirtual 42	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   137: ifeq +83 -> 220
    //   140: aload_0
    //   141: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   144: ldc_w 803
    //   147: invokevirtual 52	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   150: astore 5
    //   152: new 805	com/youku/vo/UserBackgroundInfo
    //   155: dup
    //   156: invokespecial 806	com/youku/vo/UserBackgroundInfo:<init>	()V
    //   159: astore 6
    //   161: aload 6
    //   163: aload 5
    //   165: ldc_w 808
    //   168: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   171: invokevirtual 811	com/youku/vo/UserBackgroundInfo:setDefaultBg	(Ljava/lang/String;)V
    //   174: aload 6
    //   176: aload 5
    //   178: ldc_w 813
    //   181: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   184: invokevirtual 816	com/youku/vo/UserBackgroundInfo:setBanner	(Ljava/lang/String;)V
    //   187: aload 6
    //   189: aload 5
    //   191: ldc_w 818
    //   194: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   197: invokevirtual 821	com/youku/vo/UserBackgroundInfo:setUid	(Ljava/lang/String;)V
    //   200: aload 6
    //   202: aload 5
    //   204: ldc_w 803
    //   207: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   210: invokevirtual 824	com/youku/vo/UserBackgroundInfo:setBackground	(Ljava/lang/String;)V
    //   213: aload 4
    //   215: aload 6
    //   217: invokevirtual 828	com/youku/vo/MyYoukuDataInfo:setUserBackgroundInfo	(Lcom/youku/vo/UserBackgroundInfo;)V
    //   220: aload_0
    //   221: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   224: ldc_w 830
    //   227: invokevirtual 42	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   230: ifeq +22 -> 252
    //   233: aload 4
    //   235: aload_0
    //   236: aload_0
    //   237: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   240: ldc_w 830
    //   243: invokevirtual 261	org/json/JSONObject:optJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   246: invokevirtual 834	com/youku/http/ParseJson:parseUseHistoryInfos	(Lorg/json/JSONArray;)Ljava/util/ArrayList;
    //   249: invokevirtual 837	com/youku/vo/MyYoukuDataInfo:setUseHistoryInfos	(Ljava/util/ArrayList;)V
    //   252: aload 4
    //   254: astore_1
    //   255: aload_1
    //   256: areturn
    //   257: astore_2
    //   258: ldc_w 357
    //   261: ldc_w 839
    //   264: aload_2
    //   265: invokestatic 362	com/youku/util/Logger:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   268: aload_1
    //   269: areturn
    //   270: astore_2
    //   271: aload 4
    //   273: astore_1
    //   274: goto -16 -> 258
    //
    // Exception table:
    //   from	to	target	type
    //   2	22	257	java/lang/Exception
    //   28	37	257	java/lang/Exception
    //   37	220	270	java/lang/Exception
    //   220	252	270	java/lang/Exception
  }

  // ERROR //
  public com.youku.vo.PayDataListInfo parsePayDataListInfo()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: new 32	org/json/JSONObject
    //   6: dup
    //   7: aload_0
    //   8: getfield 17	com/youku/http/ParseJson:jsonString	Ljava/lang/String;
    //   11: invokespecial 34	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   14: putfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   17: aload_0
    //   18: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   21: astore_3
    //   22: aconst_null
    //   23: astore_1
    //   24: aload_3
    //   25: ifnull +193 -> 218
    //   28: new 843	com/youku/vo/PayDataListInfo
    //   31: dup
    //   32: invokespecial 844	com/youku/vo/PayDataListInfo:<init>	()V
    //   35: astore 4
    //   37: aload 4
    //   39: aload_0
    //   40: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   43: ldc 166
    //   45: iconst_0
    //   46: invokevirtual 142	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   49: invokevirtual 845	com/youku/vo/PayDataListInfo:setTotal	(I)V
    //   52: aload_0
    //   53: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   56: ldc 38
    //   58: invokevirtual 42	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   61: ifeq +154 -> 215
    //   64: aload_0
    //   65: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   68: ldc 38
    //   70: invokevirtual 261	org/json/JSONObject:optJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   73: astore 5
    //   75: aload 5
    //   77: ifnull +138 -> 215
    //   80: aload 5
    //   82: invokevirtual 68	org/json/JSONArray:length	()I
    //   85: ifle +130 -> 215
    //   88: new 255	java/util/ArrayList
    //   91: dup
    //   92: invokespecial 256	java/util/ArrayList:<init>	()V
    //   95: astore 6
    //   97: iconst_0
    //   98: istore 7
    //   100: iload 7
    //   102: aload 5
    //   104: invokevirtual 68	org/json/JSONArray:length	()I
    //   107: if_icmpge +101 -> 208
    //   110: aload 5
    //   112: iload 7
    //   114: invokevirtual 264	org/json/JSONArray:optJSONObject	(I)Lorg/json/JSONObject;
    //   117: astore 8
    //   119: aload 8
    //   121: ifnull +119 -> 240
    //   124: new 847	com/youku/vo/PayDataInfo
    //   127: dup
    //   128: invokespecial 848	com/youku/vo/PayDataInfo:<init>	()V
    //   131: astore 9
    //   133: aload 9
    //   135: aload 8
    //   137: ldc_w 850
    //   140: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   143: invokevirtual 853	com/youku/vo/PayDataInfo:setShowid	(Ljava/lang/String;)V
    //   146: aload 9
    //   148: aload 8
    //   150: ldc 229
    //   152: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   155: invokevirtual 856	com/youku/vo/PayDataInfo:setShowname	(Ljava/lang/String;)V
    //   158: aload 9
    //   160: aload 8
    //   162: ldc_w 858
    //   165: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   168: invokevirtual 861	com/youku/vo/PayDataInfo:setExpire_time	(Ljava/lang/String;)V
    //   171: aload 9
    //   173: aload 8
    //   175: ldc_w 863
    //   178: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   181: invokevirtual 866	com/youku/vo/PayDataInfo:setShow_vthumburl	(Ljava/lang/String;)V
    //   184: aload 9
    //   186: aload 8
    //   188: ldc_w 868
    //   191: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   194: invokevirtual 871	com/youku/vo/PayDataInfo:setShow_thumburl	(Ljava/lang/String;)V
    //   197: aload 6
    //   199: aload 9
    //   201: invokevirtual 351	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   204: pop
    //   205: goto +35 -> 240
    //   208: aload 4
    //   210: aload 6
    //   212: invokevirtual 874	com/youku/vo/PayDataListInfo:setPayDataInfos	(Ljava/util/ArrayList;)V
    //   215: aload 4
    //   217: astore_1
    //   218: aload_1
    //   219: areturn
    //   220: astore_2
    //   221: ldc_w 357
    //   224: ldc_w 876
    //   227: aload_2
    //   228: invokestatic 362	com/youku/util/Logger:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   231: aload_1
    //   232: areturn
    //   233: astore_2
    //   234: aload 4
    //   236: astore_1
    //   237: goto -16 -> 221
    //   240: iinc 7 1
    //   243: goto -143 -> 100
    //
    // Exception table:
    //   from	to	target	type
    //   2	22	220	java/lang/Exception
    //   28	37	220	java/lang/Exception
    //   37	75	233	java/lang/Exception
    //   80	97	233	java/lang/Exception
    //   100	119	233	java/lang/Exception
    //   124	205	233	java/lang/Exception
    //   208	215	233	java/lang/Exception
  }

  public ArrayList<VideoInfo> parseRecommandList()
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      this.jsonObject = new JSONObject(this.jsonString);
      JSONArray localJSONArray = this.jsonObject.getJSONArray("result");
      for (int i = 0; i < localJSONArray.length(); i++)
      {
        JSONObject localJSONObject = localJSONArray.getJSONObject(i);
        VideoInfo localVideoInfo = new VideoInfo();
        localVideoInfo.vid = localJSONObject.optString("content_id");
        localVideoInfo.type = localJSONObject.optString("content_type");
        localVideoInfo.title = localJSONObject.optString("title");
        localVideoInfo.imageURL = localJSONObject.optString("image");
        localVideoInfo.stripe_bottom = localJSONObject.optString("stripe_bottom");
        localVideoInfo.paid = localJSONObject.optInt("paid", 0);
        localArrayList.add(localVideoInfo);
      }
    }
    catch (JSONException localJSONException)
    {
      Logger.e("ParseJson.parseRecommandList()", localJSONException);
    }
    return localArrayList;
  }

  // ERROR //
  public ArrayList<com.youku.vo.Keyword> parseRecommendKeywordsData()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: new 32	org/json/JSONObject
    //   6: dup
    //   7: aload_0
    //   8: getfield 17	com/youku/http/ParseJson:jsonString	Ljava/lang/String;
    //   11: invokespecial 34	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   14: putfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   17: aload_0
    //   18: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   21: astore_3
    //   22: aconst_null
    //   23: astore_1
    //   24: aload_3
    //   25: ifnull +148 -> 173
    //   28: aload_0
    //   29: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   32: ldc 38
    //   34: invokevirtual 42	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   37: istore 4
    //   39: aconst_null
    //   40: astore_1
    //   41: iload 4
    //   43: ifeq +130 -> 173
    //   46: aload_0
    //   47: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   50: ldc 38
    //   52: invokevirtual 261	org/json/JSONObject:optJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   55: astore 5
    //   57: aconst_null
    //   58: astore_1
    //   59: aload 5
    //   61: ifnull +112 -> 173
    //   64: aload 5
    //   66: invokevirtual 68	org/json/JSONArray:length	()I
    //   69: istore 6
    //   71: aconst_null
    //   72: astore_1
    //   73: iload 6
    //   75: ifle +98 -> 173
    //   78: new 255	java/util/ArrayList
    //   81: dup
    //   82: invokespecial 256	java/util/ArrayList:<init>	()V
    //   85: astore 7
    //   87: iconst_0
    //   88: istore 8
    //   90: iload 8
    //   92: aload 5
    //   94: invokevirtual 68	org/json/JSONArray:length	()I
    //   97: if_icmpge +85 -> 182
    //   100: aload 5
    //   102: iload 8
    //   104: invokevirtual 264	org/json/JSONArray:optJSONObject	(I)Lorg/json/JSONObject;
    //   107: astore 9
    //   109: aload 9
    //   111: ifnull +45 -> 156
    //   114: new 892	com/youku/vo/Keyword
    //   117: dup
    //   118: invokespecial 893	com/youku/vo/Keyword:<init>	()V
    //   121: astore 10
    //   123: aload 10
    //   125: aload 9
    //   127: ldc 96
    //   129: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   132: invokevirtual 894	com/youku/vo/Keyword:setTitle	(Ljava/lang/String;)V
    //   135: aload 10
    //   137: aload 9
    //   139: ldc_w 896
    //   142: invokevirtual 130	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   145: invokevirtual 899	com/youku/vo/Keyword:setWeight	(I)V
    //   148: aload 7
    //   150: aload 10
    //   152: invokevirtual 351	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   155: pop
    //   156: iinc 8 1
    //   159: goto -69 -> 90
    //   162: astore_2
    //   163: ldc_w 357
    //   166: ldc_w 901
    //   169: aload_2
    //   170: invokestatic 362	com/youku/util/Logger:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   173: aload_1
    //   174: areturn
    //   175: astore_2
    //   176: aload 7
    //   178: astore_1
    //   179: goto -16 -> 163
    //   182: aload 7
    //   184: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   2	22	162	java/lang/Exception
    //   28	39	162	java/lang/Exception
    //   46	57	162	java/lang/Exception
    //   64	71	162	java/lang/Exception
    //   78	87	162	java/lang/Exception
    //   90	109	175	java/lang/Exception
    //   114	156	175	java/lang/Exception
  }

  public int parseResponseFailCode()
  {
    int i = -5;
    if (this.jsonString == null)
      return -6;
    try
    {
      this.jsonObject = new JSONObject(this.jsonString);
      int j = this.jsonObject.getInt("code");
      i = j;
      return i;
    }
    catch (JSONException localJSONException)
    {
      while (true)
        Logger.e("ParseJson.parseResponseFailCode()", localJSONException);
    }
    catch (Exception localException)
    {
      while (true)
        Logger.e("Youku", "ParseJson.parseResponseFailCode()", localException);
    }
  }

  public void parseSearchDirectAllData(ArrayList<SearchDirectAllResult> paramArrayList)
  {
    if (paramArrayList != null)
      paramArrayList.clear();
    while (true)
    {
      int n;
      int j;
      int k;
      try
      {
        this.jsonObject = new JSONObject(this.jsonString);
        int i1;
        if (this.jsonObject != null)
        {
          HashMap localHashMap = new HashMap();
          if (!this.jsonObject.has("play_ctr"))
            continue;
          JSONArray localJSONArray5 = this.jsonObject.optJSONArray("play_ctr");
          if ((localJSONArray5 == null) || (localJSONArray5.length() <= 0))
            continue;
          i1 = 0;
          if (i1 >= localJSONArray5.length())
            continue;
          JSONObject localJSONObject5 = localJSONArray5.optJSONObject(i1);
          if (localJSONObject5 == null)
            break label1033;
          OtherSiteData localOtherSiteData2 = new OtherSiteData();
          localOtherSiteData2.setSource_id(localJSONObject5.optString("source_id"));
          localOtherSiteData2.setPlay_mode(localJSONObject5.optString("play_mode", "1"));
          localHashMap.put(localOtherSiteData2.getSource_id(), localOtherSiteData2);
          break label1033;
          if (this.jsonObject.has("results"))
          {
            JSONArray localJSONArray1 = this.jsonObject.optJSONArray("results");
            if ((localJSONArray1 != null) && (localJSONArray1.length() > 0))
            {
              int i = 0;
              if (i < localJSONArray1.length())
              {
                JSONObject localJSONObject1 = localJSONArray1.optJSONObject(i);
                if (localJSONObject1 == null)
                  continue;
                SearchDirectAllResult localSearchDirectAllResult = new SearchDirectAllResult();
                localSearchDirectAllResult.setFormat_flag(localJSONObject1.optInt("format_flag"));
                localSearchDirectAllResult.setIs_youku(localJSONObject1.optInt("is_youku"));
                localSearchDirectAllResult.setNeed_query(localJSONObject1.optInt("need_query"));
                localSearchDirectAllResult.setSequence(localJSONObject1.optString("sequence", "1"));
                localSearchDirectAllResult.setShowid(localJSONObject1.optString("showid"));
                localSearchDirectAllResult.setNotice(localJSONObject1.optString("notice"));
                localSearchDirectAllResult.setEpisode_total(localJSONObject1.optInt("episode_total"));
                localSearchDirectAllResult.setCompleted(localJSONObject1.optInt("completed"));
                localSearchDirectAllResult.setPaid(localJSONObject1.optInt("paid"));
                localSearchDirectAllResult.setEpisode_last(localJSONObject1.optInt("episode_last"));
                localSearchDirectAllResult.setPlay_title(localJSONObject1.optString("play_title"));
                localSearchDirectAllResult.setPlay_videoid(localJSONObject1.optString("play_videoid"));
                localSearchDirectAllResult.setShow_thumburl(localJSONObject1.optString("show_thumburl"));
                localSearchDirectAllResult.setShow_vthumburl(localJSONObject1.optString("show_vthumburl"));
                localSearchDirectAllResult.setCats(localJSONObject1.optString("cats"));
                localSearchDirectAllResult.setShowname(localJSONObject1.optString("showname"));
                localSearchDirectAllResult.setStripe_bottom(localJSONObject1.optString("stripe_bottom"));
                localSearchDirectAllResult.setSummary(localJSONObject1.optString("summary"));
                localSearchDirectAllResult.setReputation(localJSONObject1.optDouble("reputation", 0.0D));
                if (!localJSONObject1.has("serises"))
                  continue;
                JSONArray localJSONArray4 = localJSONObject1.optJSONArray("serises");
                if ((localJSONArray4 == null) || (localJSONArray4.length() <= 0))
                  continue;
                ArrayList localArrayList3 = new ArrayList();
                n = 0;
                if (n >= localJSONArray4.length())
                  continue;
                JSONObject localJSONObject4 = localJSONArray4.optJSONObject(n);
                if (localJSONObject4 == null)
                  break label1039;
                SearchDirectAllSerises localSearchDirectAllSerises = new SearchDirectAllSerises();
                localSearchDirectAllSerises.setShow_videostage(localJSONObject4.optString("show_videostage"));
                localSearchDirectAllSerises.setTitle(localJSONObject4.optString("title"));
                localSearchDirectAllSerises.setShow_videoseq(localJSONObject4.optString("show_videoseq"));
                localSearchDirectAllSerises.setVideoid(localJSONObject4.optString("videoid"));
                localSearchDirectAllSerises.setLimit(localJSONObject4.optInt("limit"));
                localArrayList3.add(localSearchDirectAllSerises);
                break label1039;
                localSearchDirectAllResult.setSearchDirectAllSerises(localArrayList3);
                if (!localJSONObject1.has("episodes"))
                  continue;
                JSONArray localJSONArray2 = localJSONObject1.optJSONArray("episodes");
                if ((localJSONArray2 == null) || (localJSONArray2.length() <= 0))
                  continue;
                ArrayList localArrayList1 = new ArrayList();
                j = 0;
                if (j >= localJSONArray2.length())
                  continue;
                JSONObject localJSONObject2 = localJSONArray2.optJSONObject(j);
                if (localJSONObject2 == null)
                  break label1051;
                SearchDirectAllOtherSiteEpisode localSearchDirectAllOtherSiteEpisode = new SearchDirectAllOtherSiteEpisode();
                localSearchDirectAllOtherSiteEpisode.setSourceSite(localJSONObject2.optInt("sourceSite"));
                localSearchDirectAllOtherSiteEpisode.setTotal(localJSONObject2.optInt("total"));
                localSearchDirectAllOtherSiteEpisode.setComplete(localJSONObject2.optBoolean("complete"));
                localSearchDirectAllOtherSiteEpisode.setNewestOrder(localJSONObject2.optInt("newestOrder"));
                localSearchDirectAllOtherSiteEpisode.setTrailerCount(localJSONObject2.optInt("trailerCount"));
                localSearchDirectAllOtherSiteEpisode.setExtraCount(localJSONObject2.optInt("extraCount"));
                OtherSiteData localOtherSiteData1 = (OtherSiteData)localHashMap.get(String.valueOf(localSearchDirectAllOtherSiteEpisode.getSourceSite()));
                if (localOtherSiteData1 == null)
                  continue;
                localSearchDirectAllOtherSiteEpisode.setPlay_mode(localOtherSiteData1.getPlay_mode());
                if (!localJSONObject2.has("series"))
                  continue;
                JSONArray localJSONArray3 = localJSONObject2.optJSONArray("series");
                if ((localJSONArray3 == null) || (localJSONArray3.length() <= 0))
                  continue;
                ArrayList localArrayList2 = new ArrayList();
                k = 0;
                int m = localJSONArray3.length();
                if (k >= m)
                  continue;
                JSONObject localJSONObject3 = localJSONArray3.optJSONObject(k);
                if (localJSONObject3 == null)
                  break label1045;
                SearchDirectAllOtherSiteSeries localSearchDirectAllOtherSiteSeries = new SearchDirectAllOtherSiteSeries();
                localSearchDirectAllOtherSiteSeries.setUrl(localJSONObject3.optString("url"));
                localSearchDirectAllOtherSiteSeries.setRurl(localJSONObject3.optString("rurl"));
                localSearchDirectAllOtherSiteSeries.setName(localJSONObject3.optString("name"));
                localSearchDirectAllOtherSiteSeries.setVideo_type(localJSONObject3.optInt("video_type"));
                localSearchDirectAllOtherSiteSeries.setOrder(localJSONObject3.optString("order"));
                localArrayList2.add(localSearchDirectAllOtherSiteSeries);
                break label1045;
                localSearchDirectAllOtherSiteEpisode.setSearchDirectAllOtherSiteSeries(localArrayList2);
                localArrayList1.add(localSearchDirectAllOtherSiteEpisode);
                break label1051;
                localSearchDirectAllResult.setSearchDirectAllOtherSiteEpisodes(localArrayList1);
                paramArrayList.add(localSearchDirectAllResult);
                i++;
                continue;
              }
            }
          }
        }
      }
      catch (Exception localException)
      {
        Logger.e("Youku", "ParseJson#parseSearchDirectAllData()", localException);
      }
      return;
      label1033: i1++;
      continue;
      label1039: n++;
      continue;
      label1045: k++;
      continue;
      label1051: j++;
    }
  }

  // ERROR //
  public ArrayList<com.youku.ui.search.data.SearchKey> parseSearchKeywordsData(com.youku.ui.search.provider.SqliteManager paramSqliteManager)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_0
    //   3: new 32	org/json/JSONObject
    //   6: dup
    //   7: aload_0
    //   8: getfield 17	com/youku/http/ParseJson:jsonString	Ljava/lang/String;
    //   11: invokespecial 34	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   14: putfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   17: aload_0
    //   18: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   21: astore 4
    //   23: aconst_null
    //   24: astore_2
    //   25: aload 4
    //   27: ifnull +164 -> 191
    //   30: aload_0
    //   31: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   34: ldc 38
    //   36: invokevirtual 42	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   39: istore 5
    //   41: aconst_null
    //   42: astore_2
    //   43: iload 5
    //   45: ifeq +146 -> 191
    //   48: aload_0
    //   49: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   52: ldc 38
    //   54: invokevirtual 261	org/json/JSONObject:optJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   57: astore 6
    //   59: aconst_null
    //   60: astore_2
    //   61: aload 6
    //   63: ifnull +128 -> 191
    //   66: aload 6
    //   68: invokevirtual 68	org/json/JSONArray:length	()I
    //   71: istore 7
    //   73: aconst_null
    //   74: astore_2
    //   75: iload 7
    //   77: ifle +114 -> 191
    //   80: new 255	java/util/ArrayList
    //   83: dup
    //   84: invokespecial 256	java/util/ArrayList:<init>	()V
    //   87: astore 8
    //   89: iconst_0
    //   90: istore 9
    //   92: iload 9
    //   94: aload 6
    //   96: invokevirtual 68	org/json/JSONArray:length	()I
    //   99: if_icmpge +101 -> 200
    //   102: aload 6
    //   104: iload 9
    //   106: invokevirtual 264	org/json/JSONArray:optJSONObject	(I)Lorg/json/JSONObject;
    //   109: astore 10
    //   111: aload 10
    //   113: ifnull +61 -> 174
    //   116: new 1120	com/youku/ui/search/data/SearchKey
    //   119: dup
    //   120: invokespecial 1121	com/youku/ui/search/data/SearchKey:<init>	()V
    //   123: astore 11
    //   125: aload 11
    //   127: aload 10
    //   129: ldc_w 1123
    //   132: invokevirtual 130	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   135: invokevirtual 1126	com/youku/ui/search/data/SearchKey:setCount	(I)V
    //   138: aload 11
    //   140: aload 10
    //   142: ldc_w 1128
    //   145: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   148: invokevirtual 1131	com/youku/ui/search/data/SearchKey:setKeyword	(Ljava/lang/String;)V
    //   151: aload 8
    //   153: aload 11
    //   155: invokevirtual 351	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   158: pop
    //   159: aload_1
    //   160: ifnull +14 -> 174
    //   163: aload_1
    //   164: aload 11
    //   166: invokevirtual 1134	com/youku/ui/search/data/SearchKey:getKeyword	()Ljava/lang/String;
    //   169: ldc 60
    //   171: invokevirtual 1139	com/youku/ui/search/provider/SqliteManager:add	(Ljava/lang/String;Ljava/lang/String;)V
    //   174: iinc 9 1
    //   177: goto -85 -> 92
    //   180: astore_3
    //   181: ldc_w 357
    //   184: ldc_w 1141
    //   187: aload_3
    //   188: invokestatic 362	com/youku/util/Logger:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   191: aload_2
    //   192: areturn
    //   193: astore_3
    //   194: aload 8
    //   196: astore_2
    //   197: goto -16 -> 181
    //   200: aload 8
    //   202: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   2	23	180	java/lang/Exception
    //   30	41	180	java/lang/Exception
    //   48	59	180	java/lang/Exception
    //   66	73	180	java/lang/Exception
    //   80	89	180	java/lang/Exception
    //   92	111	193	java/lang/Exception
    //   116	159	193	java/lang/Exception
    //   163	174	193	java/lang/Exception
  }

  public void parseSearchOtherSiteVideoData(SearchDirectAllResult paramSearchDirectAllResult)
  {
    if ((paramSearchDirectAllResult.getSearchDirectAllOtherSiteEpisodes() != null) && (paramSearchDirectAllResult.getSearchDirectAllOtherSiteEpisodes().size() > paramSearchDirectAllResult.getCurrentSelectSite()) && (((SearchDirectAllOtherSiteEpisode)paramSearchDirectAllResult.getSearchDirectAllOtherSiteEpisodes().get(paramSearchDirectAllResult.getCurrentSelectSite())).getSearchDirectAllOtherSiteSeries() == null));
    while (true)
    {
      int i;
      try
      {
        this.jsonObject = new JSONObject(this.jsonString);
        if ((this.jsonObject == null) || (!this.jsonObject.has("results")))
          continue;
        JSONArray localJSONArray = this.jsonObject.optJSONArray("results");
        if ((localJSONArray == null) || (localJSONArray.length() <= 0))
          continue;
        ArrayList localArrayList = new ArrayList();
        i = 0;
        if (i >= localJSONArray.length())
          continue;
        JSONObject localJSONObject = localJSONArray.optJSONObject(i);
        if (localJSONObject != null)
        {
          SearchDirectAllOtherSiteSeries localSearchDirectAllOtherSiteSeries = new SearchDirectAllOtherSiteSeries();
          localSearchDirectAllOtherSiteSeries.setUrl(localJSONObject.optString("url"));
          localSearchDirectAllOtherSiteSeries.setRurl(localJSONObject.optString("rurl"));
          localSearchDirectAllOtherSiteSeries.setName(localJSONObject.optString("name"));
          localSearchDirectAllOtherSiteSeries.setVideo_type(localJSONObject.optInt("video_type"));
          localSearchDirectAllOtherSiteSeries.setOrder(localJSONObject.optString("order"));
          localArrayList.add(localSearchDirectAllOtherSiteSeries);
          break label247;
          ((SearchDirectAllOtherSiteEpisode)paramSearchDirectAllResult.getSearchDirectAllOtherSiteEpisodes().get(paramSearchDirectAllResult.getCurrentSelectSite())).setSearchDirectAllOtherSiteSeries(localArrayList);
          return;
        }
      }
      catch (Exception localException)
      {
        Logger.e("Youku", "ParseJson#parseSearchOtherSiteVideoData()", localException);
        return;
      }
      label247: i++;
    }
  }

  public void parseSearchRecommendResult()
  {
    while (true)
    {
      try
      {
        JSONArray localJSONArray = getResultArray();
        if (!this.jsonObject.has("total"))
          break label137;
        this.channel.totalVideo = Integer.valueOf(this.jsonObject.getString("total")).intValue();
        break label137;
        if (i < localJSONArray.length())
        {
          JSONObject localJSONObject = localJSONArray.getJSONObject(i);
          VideoInfo localVideoInfo = new VideoInfo();
          localVideoInfo.vid = localJSONObject.optString("tid");
          localVideoInfo.imageURL = localJSONObject.optString("img");
          localVideoInfo.title = localJSONObject.optString("title");
          localVideoInfo.type = localJSONObject.optString("type");
          this.channel.videoList.add(localVideoInfo);
          i++;
          continue;
        }
      }
      catch (Exception localException)
      {
      }
      return;
      label137: int i = 0;
    }
  }

  public void parseSearchRecommendVideoResult()
  {
    try
    {
      JSONArray localJSONArray = getResultArray();
      int i = localJSONArray.length();
      if (i > 0)
      {
        SearchConstant.recommendVideos = new ArrayList();
        for (int j = 0; j < i; j++)
        {
          JSONObject localJSONObject = localJSONArray.getJSONObject(j);
          SearchOtherGridviewResults localSearchOtherGridviewResults = new SearchOtherGridviewResults();
          localSearchOtherGridviewResults.title = localJSONObject.optString("title");
          localSearchOtherGridviewResults.img_hd = localJSONObject.optString("img");
          localSearchOtherGridviewResults.videoid = localJSONObject.optString("tid");
          SearchConstant.recommendVideos.add(localSearchOtherGridviewResults);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  // ERROR //
  public com.youku.alipay.entity.TradeInfo parseTradeInfo()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: new 32	org/json/JSONObject
    //   6: dup
    //   7: aload_0
    //   8: getfield 17	com/youku/http/ParseJson:jsonString	Ljava/lang/String;
    //   11: invokespecial 34	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   14: putfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   17: aload_0
    //   18: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   21: astore_3
    //   22: aconst_null
    //   23: astore_1
    //   24: aload_3
    //   25: ifnull +59 -> 84
    //   28: aload_0
    //   29: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   32: ldc_w 879
    //   35: invokevirtual 42	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   38: istore 4
    //   40: aconst_null
    //   41: astore_1
    //   42: iload 4
    //   44: ifeq +40 -> 84
    //   47: aload_0
    //   48: getfield 36	com/youku/http/ParseJson:jsonObject	Lorg/json/JSONObject;
    //   51: ldc_w 879
    //   54: invokevirtual 52	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   57: astore 5
    //   59: new 1175	com/youku/alipay/entity/TradeInfo
    //   62: dup
    //   63: invokespecial 1176	com/youku/alipay/entity/TradeInfo:<init>	()V
    //   66: astore 6
    //   68: aload 6
    //   70: aload 5
    //   72: ldc_w 578
    //   75: invokevirtual 58	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   78: invokevirtual 1179	com/youku/alipay/entity/TradeInfo:setState	(Ljava/lang/String;)V
    //   81: aload 6
    //   83: astore_1
    //   84: aload_1
    //   85: areturn
    //   86: astore_2
    //   87: ldc_w 357
    //   90: ldc_w 1181
    //   93: aload_2
    //   94: invokestatic 362	com/youku/util/Logger:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   97: aload_1
    //   98: areturn
    //   99: astore_2
    //   100: aload 6
    //   102: astore_1
    //   103: goto -16 -> 87
    //
    // Exception table:
    //   from	to	target	type
    //   2	22	86	java/lang/Exception
    //   28	40	86	java/lang/Exception
    //   47	68	86	java/lang/Exception
    //   68	81	99	java/lang/Exception
  }

  public ArrayList<UseHistoryInfo> parseUseHistoryInfos(JSONArray paramJSONArray)
    throws JSONException
  {
    ArrayList localArrayList = new ArrayList();
    if (paramJSONArray != null)
    {
      int i = paramJSONArray.length();
      for (int j = 0; j < i; j++)
      {
        JSONObject localJSONObject = paramJSONArray.getJSONObject(j);
        UseHistoryInfo localUseHistoryInfo = new UseHistoryInfo();
        localUseHistoryInfo.setShowid(localJSONObject.optString("showid"));
        localUseHistoryInfo.setImg(localJSONObject.optString("img"));
        localUseHistoryInfo.setPoint(localJSONObject.optInt("point", 0));
        localUseHistoryInfo.setImg_hd(localJSONObject.optString("img_hd"));
        localUseHistoryInfo.setTitle(localJSONObject.optString("title"));
        localUseHistoryInfo.setIsshow(localJSONObject.optInt("isshow", 0));
        localUseHistoryInfo.setPaid(localJSONObject.optInt("paid", 0));
        localUseHistoryInfo.setVideoid(localJSONObject.optString("videoid"));
        localUseHistoryInfo.setDuration(localJSONObject.optDouble("duration", 0.0D));
        localUseHistoryInfo.setCats(localJSONObject.optString("cats"));
        localUseHistoryInfo.setShowname(localJSONObject.optString("showname"));
        localUseHistoryInfo.setDuration_format(localJSONObject.optString("duration_format"));
        localUseHistoryInfo.setUpdate_title(localJSONObject.optString("update_title"));
        localUseHistoryInfo.setSee_title(localJSONObject.optString("see_title", "看到:"));
        localUseHistoryInfo.setFormat_flag(localJSONObject.optInt("format_flag", 0));
        localUseHistoryInfo.setPlaypercent(localJSONObject.optString("playpercent"));
        if ((!Youku.isLogined) && (!TextUtils.isEmpty(localUseHistoryInfo.getVideoid())) && (localUseHistoryInfo.getDuration() > 0.0D))
        {
          VideoInfo localVideoInfo = SQLiteManager.getVideoInfoUseID(localUseHistoryInfo.getVideoid());
          if (localVideoInfo != null)
            localUseHistoryInfo.setPlaypercent(YoukuUtil.formatPlayPercent(Math.ceil(100.0D * (1.0D * localVideoInfo.playTime / localUseHistoryInfo.getDuration()))));
        }
        localArrayList.add(localUseHistoryInfo);
      }
    }
    return localArrayList;
  }

  public void parseUserInfo()
  {
    try
    {
      if (!TextUtils.isEmpty(this.jsonString))
      {
        this.jsonObject = new JSONObject(this.jsonString);
        if (this.jsonObject.has("results"))
        {
          JSONObject localJSONObject1 = this.jsonObject.getJSONObject("results");
          Youku.userprofile = new UserProfile();
          if (localJSONObject1 != null)
          {
            Youku.userprofile.results.username = localJSONObject1.optString("username");
            Youku.userprofile.results.userdesc = localJSONObject1.optString("userdesc");
            Youku.userprofile.results.userid = localJSONObject1.optString("userid");
            Youku.userprofile.results.avatar = localJSONObject1.optString("avatar");
            JSONArray localJSONArray = localJSONObject1.getJSONArray("items");
            for (int i = 0; i < localJSONArray.length(); i++)
            {
              UserProfile localUserProfile = Youku.userprofile;
              localUserProfile.getClass();
              UserProfile.UserProfileItems localUserProfileItems = new UserProfile.UserProfileItems(localUserProfile);
              JSONObject localJSONObject2 = localJSONArray.getJSONObject(i);
              localUserProfileItems.top_count = localJSONObject2.optInt("top_count");
              localUserProfileItems.bottom_count = localJSONObject2.optString("bottom_count");
              localUserProfileItems.title = localJSONObject2.optString("title");
              localUserProfileItems.color = localJSONObject2.optInt("color");
              localUserProfileItems.id = localJSONObject2.optInt("id");
              localUserProfileItems.icon = localJSONObject2.optString("icon");
              localUserProfileItems.link_url = localJSONObject2.optString("link_url");
              localUserProfileItems.link_type = localJSONObject2.optInt("link_type");
              Youku.userprofile.results.items.add(localUserProfileItems);
            }
          }
        }
      }
    }
    catch (JSONException localJSONException)
    {
      Logger.e("ParseJson.parseUserInfo()", localJSONException);
    }
  }
}

/* Location:           D:\反编译\dex2jar-0.0.9.15\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.youku.http.ParseJson
 * JD-Core Version:    0.6.0
 */