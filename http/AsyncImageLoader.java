package com.youku.http;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import com.youku.network.YoukuAsyncTask;
import com.youku.util.Logger;
import java.io.File;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class AsyncImageLoader
{
  private static final int FAILED = 0;
  private static final int SUCCESS = 1;
  private HashMap<String, SoftReference<Drawable>> imageCache = new HashMap();
  public boolean isLocalMode;
  private int message;

  public void clearImage()
  {
    Iterator localIterator = this.imageCache.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Drawable localDrawable = (Drawable)((SoftReference)this.imageCache.get(str)).get();
      if (localDrawable != null)
        localDrawable.setCallback(null);
      ((SoftReference)this.imageCache.get(str)).clear();
    }
    this.imageCache.clear();
  }

  public void clearImage(String paramString)
  {
    if ((this.imageCache != null) && (this.imageCache.get(paramString) != null))
    {
      Drawable localDrawable = (Drawable)((SoftReference)this.imageCache.get(paramString)).get();
      if (localDrawable != null)
        localDrawable.setCallback(null);
      ((SoftReference)this.imageCache.get(paramString)).clear();
      this.imageCache.remove(paramString);
    }
  }

  public Drawable loadDrawable(String paramString, ImageCallback paramImageCallback)
  {
    1 local1 = new Handler(paramImageCallback, paramString)
    {
      public void handleMessage(Message paramMessage)
      {
        switch (paramMessage.what)
        {
        default:
          return;
        case 1:
          this.val$imageCallback.imageLoaded((Drawable)paramMessage.obj, this.val$imageUrl);
          return;
        case 0:
        }
        this.val$imageCallback.imageLoaded(null, this.val$imageUrl);
        Logger.d("Load Image Failed");
      }
    };
    if (this.imageCache.containsKey(paramString))
    {
      Drawable localDrawable = (Drawable)((SoftReference)this.imageCache.get(paramString)).get();
      if (localDrawable != null)
        return localDrawable;
    }
    new YoukuAsyncTask(paramString, local1)
    {
      protected Drawable doInBackground(Void[] paramArrayOfVoid)
      {
        if (!AsyncImageLoader.this.isLocalMode)
          return AsyncImageLoader.this.loadImageFromUrl(this.val$imageUrl);
        return AsyncImageLoader.this.loadImageFromPath(this.val$imageUrl);
      }

      protected void onPostExecute(Drawable paramDrawable)
      {
        AsyncImageLoader.this.imageCache.put(this.val$imageUrl, new SoftReference(paramDrawable));
        Message localMessage = this.val$handler.obtainMessage(AsyncImageLoader.this.message, paramDrawable);
        this.val$handler.sendMessage(localMessage);
      }
    }
    .execute(new Void[0]);
    return null;
  }

  public Drawable loadImageFromPath(String paramString)
  {
    File localFile = new File(paramString);
    if (localFile.exists())
    {
      BitmapDrawable localBitmapDrawable = new BitmapDrawable(localFile.getPath());
      int i = 0;
      if (localBitmapDrawable != null)
        i = 1;
      this.message = i;
      return localBitmapDrawable;
    }
    this.message = 0;
    return null;
  }

  public Drawable loadImageFromUrl(String paramString)
  {
    try
    {
      localInputStream = (InputStream)new URL(paramString).getContent();
    }
    catch (Exception localException)
    {
      try
      {
        InputStream localInputStream;
        Drawable localDrawable = Drawable.createFromStream(localInputStream, "src");
        if (localDrawable != null);
        for (int i = 1; ; i = 0)
        {
          this.message = i;
          return localDrawable;
          localException = localException;
          this.message = 0;
          Logger.e("Youku", "AsyncImageLoader.loadImageFromUrl()", localException);
          localInputStream = null;
          break;
        }
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        Logger.e("Youku", "AsyncImageLoader.loadImageFromUrl()", localOutOfMemoryError);
        clearImage();
        this.message = 0;
      }
    }
    return null;
  }

  public static abstract interface ImageCallback
  {
    public abstract void imageLoaded(Drawable paramDrawable, String paramString);
  }
}

/* Location:           D:\反编译\dex2jar-0.0.9.15\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.youku.http.AsyncImageLoader
 * JD-Core Version:    0.6.0
 */