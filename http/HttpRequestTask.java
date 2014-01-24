package com.youku.http;

import android.os.Handler;
import android.os.Message;

public class HttpRequestTask extends HttpAsyncTask<Handler, Object, Handler>
{
  public static final int ADDRESS_NOT_FOUNT = 404;
  public static final String REQ_GET = "GET";
  public static final String REQ_POST = "POST";
  public static final int SERVER_ERR = 500;
  public static int connectTimes = 3;
  private int fail;
  private int message;
  private String requestType;
  private String requrl;
  private String resultJson;
  private boolean setCookie;
  private int success;

  public HttpRequestTask(String paramString)
  {
    this(paramString, "GET", false);
  }

  public HttpRequestTask(String paramString1, String paramString2)
  {
    this(paramString1, paramString2, false);
  }

  public HttpRequestTask(String paramString1, String paramString2, boolean paramBoolean)
  {
    this.requrl = paramString1;
    this.requestType = paramString2;
    this.setCookie = paramBoolean;
    this.success = -1;
    this.fail = -1;
  }

  public HttpRequestTask(String paramString, boolean paramBoolean)
  {
    this(paramString, "GET", paramBoolean);
  }

  // ERROR //
  private void Connection()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: new 58	java/net/URL
    //   5: dup
    //   6: aload_0
    //   7: getfield 40	com/youku/http/HttpRequestTask:requrl	Ljava/lang/String;
    //   10: invokespecial 60	java/net/URL:<init>	(Ljava/lang/String;)V
    //   13: astore_2
    //   14: ldc 62
    //   16: aload_0
    //   17: getfield 40	com/youku/http/HttpRequestTask:requrl	Ljava/lang/String;
    //   20: invokestatic 67	com/youku/util/Logger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   23: aload_2
    //   24: invokevirtual 71	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   27: astore 11
    //   29: aload 11
    //   31: sipush 30000
    //   34: invokevirtual 77	java/net/URLConnection:setConnectTimeout	(I)V
    //   37: aload 11
    //   39: sipush 30000
    //   42: invokevirtual 80	java/net/URLConnection:setReadTimeout	(I)V
    //   45: aload 11
    //   47: checkcast 82	java/net/HttpURLConnection
    //   50: astore 12
    //   52: aload 12
    //   54: iconst_0
    //   55: invokevirtual 86	java/net/HttpURLConnection:setAllowUserInteraction	(Z)V
    //   58: aload 12
    //   60: iconst_1
    //   61: invokevirtual 89	java/net/HttpURLConnection:setInstanceFollowRedirects	(Z)V
    //   64: aload 12
    //   66: aload_0
    //   67: getfield 42	com/youku/http/HttpRequestTask:requestType	Ljava/lang/String;
    //   70: invokevirtual 92	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   73: aload_0
    //   74: getfield 44	com/youku/http/HttpRequestTask:setCookie	Z
    //   77: istore 13
    //   79: aconst_null
    //   80: astore_1
    //   81: iload 13
    //   83: ifeq +24 -> 107
    //   86: getstatic 97	com/youku/phone/Youku:COOKIE	Ljava/lang/String;
    //   89: astore 14
    //   91: aload 12
    //   93: ldc 99
    //   95: aload 14
    //   97: invokevirtual 102	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   100: ldc 104
    //   102: aload 14
    //   104: invokestatic 67	com/youku/util/Logger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   107: aload 12
    //   109: ldc 106
    //   111: getstatic 109	com/youku/phone/Youku:User_Agent	Ljava/lang/String;
    //   114: invokevirtual 102	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   117: aload 12
    //   119: invokevirtual 112	java/net/HttpURLConnection:connect	()V
    //   122: aload 12
    //   124: invokevirtual 116	java/net/HttpURLConnection:getResponseCode	()I
    //   127: istore 15
    //   129: aconst_null
    //   130: astore_1
    //   131: iload 15
    //   133: sipush 200
    //   136: if_icmpeq +28 -> 164
    //   139: getstatic 30	com/youku/http/HttpRequestTask:connectTimes	I
    //   142: istore 16
    //   144: aconst_null
    //   145: astore_1
    //   146: iload 16
    //   148: ifle +16 -> 164
    //   151: aload 12
    //   153: invokevirtual 112	java/net/HttpURLConnection:connect	()V
    //   156: iconst_m1
    //   157: getstatic 30	com/youku/http/HttpRequestTask:connectTimes	I
    //   160: iadd
    //   161: putstatic 30	com/youku/http/HttpRequestTask:connectTimes	I
    //   164: aload 12
    //   166: invokevirtual 116	java/net/HttpURLConnection:getResponseCode	()I
    //   169: istore 17
    //   171: aload_0
    //   172: ldc 118
    //   174: putfield 120	com/youku/http/HttpRequestTask:resultJson	Ljava/lang/String;
    //   177: ldc 122
    //   179: new 124	java/lang/StringBuilder
    //   182: dup
    //   183: invokespecial 125	java/lang/StringBuilder:<init>	()V
    //   186: iload 17
    //   188: invokevirtual 129	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   191: ldc 131
    //   193: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   196: aload_0
    //   197: getfield 40	com/youku/http/HttpRequestTask:requrl	Ljava/lang/String;
    //   200: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   203: invokevirtual 138	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   206: invokestatic 67	com/youku/util/Logger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   209: aload_0
    //   210: getfield 46	com/youku/http/HttpRequestTask:success	I
    //   213: istore 18
    //   215: aconst_null
    //   216: astore_1
    //   217: iload 18
    //   219: iconst_m1
    //   220: if_icmpeq +22 -> 242
    //   223: aload_0
    //   224: getfield 48	com/youku/http/HttpRequestTask:fail	I
    //   227: istore 19
    //   229: aload_0
    //   230: getfield 46	com/youku/http/HttpRequestTask:success	I
    //   233: istore 20
    //   235: iload 19
    //   237: iload 20
    //   239: if_icmpne +28 -> 267
    //   242: iconst_0
    //   243: ifeq +7 -> 250
    //   246: aconst_null
    //   247: invokevirtual 143	java/io/InputStream:close	()V
    //   250: aload_0
    //   251: invokevirtual 146	com/youku/http/HttpRequestTask:onCancelled	()V
    //   254: return
    //   255: astore 22
    //   257: ldc 148
    //   259: aload 22
    //   261: invokestatic 152	com/youku/util/Logger:e	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   264: goto -14 -> 250
    //   267: iload 17
    //   269: sipush 200
    //   272: if_icmpne +38 -> 310
    //   275: aload 12
    //   277: invokevirtual 156	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   280: astore_1
    //   281: aload_0
    //   282: aload_1
    //   283: invokestatic 162	com/youku/util/YoukuUtil:convertStreamToString	(Ljava/io/InputStream;)Ljava/lang/String;
    //   286: putfield 120	com/youku/http/HttpRequestTask:resultJson	Ljava/lang/String;
    //   289: aload_0
    //   290: aload_0
    //   291: getfield 46	com/youku/http/HttpRequestTask:success	I
    //   294: putfield 164	com/youku/http/HttpRequestTask:message	I
    //   297: aload_1
    //   298: ifnull +7 -> 305
    //   301: aload_1
    //   302: invokevirtual 143	java/io/InputStream:close	()V
    //   305: aload_0
    //   306: invokevirtual 146	com/youku/http/HttpRequestTask:onCancelled	()V
    //   309: return
    //   310: iload 17
    //   312: sipush 400
    //   315: if_icmpne +58 -> 373
    //   318: aload 12
    //   320: invokevirtual 167	java/net/HttpURLConnection:getErrorStream	()Ljava/io/InputStream;
    //   323: astore_1
    //   324: aload_0
    //   325: aload_1
    //   326: invokestatic 162	com/youku/util/YoukuUtil:convertStreamToString	(Ljava/io/InputStream;)Ljava/lang/String;
    //   329: putfield 120	com/youku/http/HttpRequestTask:resultJson	Ljava/lang/String;
    //   332: aload_0
    //   333: aload_0
    //   334: getfield 48	com/youku/http/HttpRequestTask:fail	I
    //   337: putfield 164	com/youku/http/HttpRequestTask:message	I
    //   340: goto -43 -> 297
    //   343: astore 9
    //   345: ldc 148
    //   347: aload 9
    //   349: invokestatic 152	com/youku/util/Logger:e	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   352: aload_0
    //   353: aload_0
    //   354: getfield 48	com/youku/http/HttpRequestTask:fail	I
    //   357: putfield 164	com/youku/http/HttpRequestTask:message	I
    //   360: aload_1
    //   361: ifnull +7 -> 368
    //   364: aload_1
    //   365: invokevirtual 143	java/io/InputStream:close	()V
    //   368: aload_0
    //   369: invokevirtual 146	com/youku/http/HttpRequestTask:onCancelled	()V
    //   372: return
    //   373: iload 17
    //   375: sipush 404
    //   378: if_icmpne +50 -> 428
    //   381: aload_0
    //   382: sipush 404
    //   385: aload_0
    //   386: getfield 48	com/youku/http/HttpRequestTask:fail	I
    //   389: iadd
    //   390: putfield 164	com/youku/http/HttpRequestTask:message	I
    //   393: aconst_null
    //   394: astore_1
    //   395: goto -98 -> 297
    //   398: astore 7
    //   400: ldc 148
    //   402: aload 7
    //   404: invokestatic 152	com/youku/util/Logger:e	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   407: aload_0
    //   408: aload_0
    //   409: getfield 48	com/youku/http/HttpRequestTask:fail	I
    //   412: putfield 164	com/youku/http/HttpRequestTask:message	I
    //   415: aload_1
    //   416: ifnull +7 -> 423
    //   419: aload_1
    //   420: invokevirtual 143	java/io/InputStream:close	()V
    //   423: aload_0
    //   424: invokevirtual 146	com/youku/http/HttpRequestTask:onCancelled	()V
    //   427: return
    //   428: aconst_null
    //   429: astore_1
    //   430: iload 17
    //   432: sipush 500
    //   435: if_icmpne -138 -> 297
    //   438: aload_0
    //   439: sipush 500
    //   442: aload_0
    //   443: getfield 48	com/youku/http/HttpRequestTask:fail	I
    //   446: iadd
    //   447: putfield 164	com/youku/http/HttpRequestTask:message	I
    //   450: aconst_null
    //   451: astore_1
    //   452: goto -155 -> 297
    //   455: astore 5
    //   457: ldc 148
    //   459: aload 5
    //   461: invokestatic 152	com/youku/util/Logger:e	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   464: aload_0
    //   465: aload_0
    //   466: getfield 48	com/youku/http/HttpRequestTask:fail	I
    //   469: putfield 164	com/youku/http/HttpRequestTask:message	I
    //   472: aload_1
    //   473: ifnull +7 -> 480
    //   476: aload_1
    //   477: invokevirtual 143	java/io/InputStream:close	()V
    //   480: aload_0
    //   481: invokevirtual 146	com/youku/http/HttpRequestTask:onCancelled	()V
    //   484: return
    //   485: astore 21
    //   487: ldc 148
    //   489: aload 21
    //   491: invokestatic 152	com/youku/util/Logger:e	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   494: goto -189 -> 305
    //   497: astore 10
    //   499: ldc 148
    //   501: aload 10
    //   503: invokestatic 152	com/youku/util/Logger:e	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   506: goto -138 -> 368
    //   509: astore 8
    //   511: ldc 148
    //   513: aload 8
    //   515: invokestatic 152	com/youku/util/Logger:e	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   518: goto -95 -> 423
    //   521: astore 6
    //   523: ldc 148
    //   525: aload 6
    //   527: invokestatic 152	com/youku/util/Logger:e	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   530: goto -50 -> 480
    //   533: astore_3
    //   534: aload_1
    //   535: ifnull +7 -> 542
    //   538: aload_1
    //   539: invokevirtual 143	java/io/InputStream:close	()V
    //   542: aload_0
    //   543: invokevirtual 146	com/youku/http/HttpRequestTask:onCancelled	()V
    //   546: aload_3
    //   547: athrow
    //   548: astore 4
    //   550: ldc 148
    //   552: aload 4
    //   554: invokestatic 152	com/youku/util/Logger:e	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   557: goto -15 -> 542
    //   560: astore_3
    //   561: goto -27 -> 534
    //   564: astore 5
    //   566: aconst_null
    //   567: astore_1
    //   568: goto -111 -> 457
    //   571: astore 7
    //   573: aconst_null
    //   574: astore_1
    //   575: goto -175 -> 400
    //   578: astore 9
    //   580: aconst_null
    //   581: astore_1
    //   582: goto -237 -> 345
    //
    // Exception table:
    //   from	to	target	type
    //   246	250	255	java/io/IOException
    //   14	79	343	java/net/MalformedURLException
    //   86	107	343	java/net/MalformedURLException
    //   107	129	343	java/net/MalformedURLException
    //   139	144	343	java/net/MalformedURLException
    //   151	164	343	java/net/MalformedURLException
    //   164	215	343	java/net/MalformedURLException
    //   223	235	343	java/net/MalformedURLException
    //   275	297	343	java/net/MalformedURLException
    //   318	340	343	java/net/MalformedURLException
    //   381	393	343	java/net/MalformedURLException
    //   438	450	343	java/net/MalformedURLException
    //   14	79	398	java/net/ProtocolException
    //   86	107	398	java/net/ProtocolException
    //   107	129	398	java/net/ProtocolException
    //   139	144	398	java/net/ProtocolException
    //   151	164	398	java/net/ProtocolException
    //   164	215	398	java/net/ProtocolException
    //   223	235	398	java/net/ProtocolException
    //   275	297	398	java/net/ProtocolException
    //   318	340	398	java/net/ProtocolException
    //   381	393	398	java/net/ProtocolException
    //   438	450	398	java/net/ProtocolException
    //   14	79	455	java/io/IOException
    //   86	107	455	java/io/IOException
    //   107	129	455	java/io/IOException
    //   139	144	455	java/io/IOException
    //   151	164	455	java/io/IOException
    //   164	215	455	java/io/IOException
    //   223	235	455	java/io/IOException
    //   275	297	455	java/io/IOException
    //   318	340	455	java/io/IOException
    //   381	393	455	java/io/IOException
    //   438	450	455	java/io/IOException
    //   301	305	485	java/io/IOException
    //   364	368	497	java/io/IOException
    //   419	423	509	java/io/IOException
    //   476	480	521	java/io/IOException
    //   2	14	533	finally
    //   345	360	533	finally
    //   400	415	533	finally
    //   457	472	533	finally
    //   538	542	548	java/io/IOException
    //   14	79	560	finally
    //   86	107	560	finally
    //   107	129	560	finally
    //   139	144	560	finally
    //   151	164	560	finally
    //   164	215	560	finally
    //   223	235	560	finally
    //   275	297	560	finally
    //   318	340	560	finally
    //   381	393	560	finally
    //   438	450	560	finally
    //   2	14	564	java/io/IOException
    //   2	14	571	java/net/ProtocolException
    //   2	14	578	java/net/MalformedURLException
  }

  protected Handler doInBackground(Handler[] paramArrayOfHandler)
  {
    Connection();
    if (paramArrayOfHandler.length == 0)
      return null;
    return paramArrayOfHandler[0];
  }

  protected void onCancelled()
  {
    if (getStatus() != HttpAsyncTask.Status.RUNNING)
      cancel(true);
    super.onCancelled();
  }

  protected void onPostExecute(Handler paramHandler)
  {
    if ((paramHandler == null) || (this.message > 200 + this.fail))
      return;
    Message localMessage = Message.obtain();
    localMessage.what = this.message;
    localMessage.obj = this.resultJson;
    paramHandler.sendMessage(localMessage);
    super.onPostExecute(paramHandler);
  }

  public void setCookie(boolean paramBoolean)
  {
    this.setCookie = paramBoolean;
  }

  public void setFail(int paramInt)
  {
    this.fail = paramInt;
  }

  public void setRequestType(String paramString)
  {
    this.requestType = paramString;
  }

  public void setRequestURL(String paramString)
  {
    this.requrl = paramString;
  }

  public void setSuccess(int paramInt)
  {
    this.success = paramInt;
  }
}

/* Location:           D:\反编译\dex2jar-0.0.9.15\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.youku.http.HttpRequestTask
 * JD-Core Version:    0.6.0
 */