package com.youku.http;

public class HistoryUploadThread extends Thread
{
  public static final int FAIL = 1105;
  public static final int SUCCESS = 1104;
  private static final String TAG = "HistoryUploadThread";
  private int code = -5;
  private int count = 0;
  private int message = 1105;
  private String sid;
  private String url;

  public HistoryUploadThread(String paramString1, int paramInt, String paramString2, String paramString3)
  {
    if (paramString3 == null)
      paramString3 = "";
    this.sid = paramString3;
    this.url = URLContainer.getAddHistoryURL(paramString1, this.sid, paramInt);
  }

  // ERROR //
  private void connectAPI(String paramString)
  {
    // Byte code:
    //   0: new 51	java/net/URL
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 53	java/net/URL:<init>	(Ljava/lang/String;)V
    //   8: astore_2
    //   9: aload_2
    //   10: invokevirtual 57	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   13: astore 6
    //   15: aload 6
    //   17: sipush 30000
    //   20: invokevirtual 63	java/net/URLConnection:setConnectTimeout	(I)V
    //   23: aload 6
    //   25: sipush 30000
    //   28: invokevirtual 66	java/net/URLConnection:setReadTimeout	(I)V
    //   31: aload 6
    //   33: checkcast 68	java/net/HttpURLConnection
    //   36: astore 7
    //   38: aload 7
    //   40: iconst_0
    //   41: invokevirtual 72	java/net/HttpURLConnection:setAllowUserInteraction	(Z)V
    //   44: aload 7
    //   46: iconst_1
    //   47: invokevirtual 75	java/net/HttpURLConnection:setInstanceFollowRedirects	(Z)V
    //   50: aload 7
    //   52: ldc 77
    //   54: invokevirtual 80	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   57: aload 7
    //   59: ldc 82
    //   61: getstatic 87	com/youku/phone/Youku:User_Agent	Ljava/lang/String;
    //   64: invokevirtual 91	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   67: aload 7
    //   69: ldc 93
    //   71: getstatic 96	com/youku/phone/Youku:COOKIE	Ljava/lang/String;
    //   74: invokevirtual 91	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   77: aload 7
    //   79: invokevirtual 99	java/net/HttpURLConnection:connect	()V
    //   82: aload 7
    //   84: invokevirtual 103	java/net/HttpURLConnection:getResponseCode	()I
    //   87: istore 8
    //   89: iload 8
    //   91: sipush 200
    //   94: if_icmpne +56 -> 150
    //   97: new 105	org/json/JSONObject
    //   100: dup
    //   101: aload 7
    //   103: invokevirtual 109	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   106: invokestatic 115	com/youku/util/YoukuUtil:convertStreamToString	(Ljava/io/InputStream;)Ljava/lang/String;
    //   109: invokespecial 116	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   112: astore 9
    //   114: aload 9
    //   116: ldc 118
    //   118: invokevirtual 122	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   121: ldc 124
    //   123: invokevirtual 130	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   126: ifeq +164 -> 290
    //   129: aload 9
    //   131: ldc 131
    //   133: invokevirtual 135	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   136: iconst_1
    //   137: if_icmpne +153 -> 290
    //   140: aload_0
    //   141: sipush 1104
    //   144: putfield 25	com/youku/http/HistoryUploadThread:message	I
    //   147: goto +143 -> 290
    //   150: iload 8
    //   152: sipush 400
    //   155: if_icmpne +75 -> 230
    //   158: new 105	org/json/JSONObject
    //   161: dup
    //   162: aload 7
    //   164: invokevirtual 138	java/net/HttpURLConnection:getErrorStream	()Ljava/io/InputStream;
    //   167: invokestatic 115	com/youku/util/YoukuUtil:convertStreamToString	(Ljava/io/InputStream;)Ljava/lang/String;
    //   170: invokespecial 116	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   173: astore 10
    //   175: aload 10
    //   177: ldc 118
    //   179: invokevirtual 122	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   182: ldc 140
    //   184: invokevirtual 130	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   187: ifeq +103 -> 290
    //   190: aload_0
    //   191: aload 10
    //   193: ldc 131
    //   195: invokevirtual 135	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   198: putfield 27	com/youku/http/HistoryUploadThread:code	I
    //   201: aload_0
    //   202: sipush 1105
    //   205: putfield 25	com/youku/http/HistoryUploadThread:message	I
    //   208: goto +82 -> 290
    //   211: astore 5
    //   213: ldc 13
    //   215: ldc 142
    //   217: aload 5
    //   219: invokestatic 148	com/youku/util/Logger:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   222: aload_0
    //   223: sipush 1105
    //   226: putfield 25	com/youku/http/HistoryUploadThread:message	I
    //   229: return
    //   230: aload_0
    //   231: sipush 1105
    //   234: putfield 25	com/youku/http/HistoryUploadThread:message	I
    //   237: goto +53 -> 290
    //   240: astore 4
    //   242: ldc 13
    //   244: ldc 142
    //   246: aload 4
    //   248: invokestatic 148	com/youku/util/Logger:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   251: aload_0
    //   252: sipush 1105
    //   255: putfield 25	com/youku/http/HistoryUploadThread:message	I
    //   258: return
    //   259: astore_3
    //   260: ldc 13
    //   262: ldc 142
    //   264: aload_3
    //   265: invokestatic 148	com/youku/util/Logger:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   268: aload_0
    //   269: sipush 1105
    //   272: putfield 25	com/youku/http/HistoryUploadThread:message	I
    //   275: return
    //   276: astore_3
    //   277: goto -17 -> 260
    //   280: astore 4
    //   282: goto -40 -> 242
    //   285: astore 5
    //   287: goto -74 -> 213
    //   290: return
    //
    // Exception table:
    //   from	to	target	type
    //   9	89	211	java/net/MalformedURLException
    //   97	147	211	java/net/MalformedURLException
    //   158	208	211	java/net/MalformedURLException
    //   230	237	211	java/net/MalformedURLException
    //   9	89	240	java/io/IOException
    //   97	147	240	java/io/IOException
    //   158	208	240	java/io/IOException
    //   230	237	240	java/io/IOException
    //   0	9	259	java/lang/Exception
    //   9	89	276	java/lang/Exception
    //   97	147	276	java/lang/Exception
    //   158	208	276	java/lang/Exception
    //   230	237	276	java/lang/Exception
    //   0	9	280	java/io/IOException
    //   0	9	285	java/net/MalformedURLException
  }

  public void run()
  {
    while ((this.message == 1105) && (this.count <= 2))
    {
      connectAPI(this.url);
      this.count = (1 + this.count);
    }
  }
}

/* Location:           D:\反编译\dex2jar-0.0.9.15\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.youku.http.HistoryUploadThread
 * JD-Core Version:    0.6.0
 */