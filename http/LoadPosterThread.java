package com.youku.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import com.youku.phone.Youku;
import com.youku.util.Logger;
import com.youku.util.YoukuUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class LoadPosterThread extends Thread
{
  private static final int BUFFER_IO_SIZE = 8000;
  public static final String POSTER_DIR = Youku.context.getCacheDir() + File.separator + "poster";
  private ImageCallBack callBack;
  private Bitmap mBitmap;
  private String mImageUrl;

  public LoadPosterThread(String paramString)
  {
    this.mImageUrl = paramString;
  }

  private void copy(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException
  {
    byte[] arrayOfByte = new byte[256];
    while (true)
    {
      int i = paramInputStream.read(arrayOfByte);
      if (i < 0)
        break;
      paramOutputStream.write(arrayOfByte, 0, i);
    }
  }

  private Bitmap loadImageFromUrl()
  {
    try
    {
      BufferedInputStream localBufferedInputStream = new BufferedInputStream(new URL(this.mImageUrl).openStream(), 8000);
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(localByteArrayOutputStream, 8000);
      copy(localBufferedInputStream, localBufferedOutputStream);
      localBufferedOutputStream.flush();
      this.mBitmap = BitmapFactory.decodeByteArray(localByteArrayOutputStream.toByteArray(), 0, localByteArrayOutputStream.size());
      saveBmpToSD(this.mBitmap, YoukuUtil.getPosterImgUrlTrait(this.mImageUrl));
      Bitmap localBitmap = this.mBitmap;
      return localBitmap;
    }
    catch (IOException localIOException)
    {
      Logger.e("LoadPosterThread.loadImageFromUrl()", localIOException);
    }
    return null;
  }

  private void saveBmpToSD(Bitmap paramBitmap, String paramString)
  {
    if (paramBitmap == null)
      return;
    try
    {
      localFile1 = new File(POSTER_DIR);
      if (!localFile1.exists())
      {
        localFile1.mkdir();
        File localFile2 = new File(POSTER_DIR, paramString);
        localFile2.createNewFile();
        FileOutputStream localFileOutputStream = new FileOutputStream(localFile2);
        paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, localFileOutputStream);
        localFileOutputStream.flush();
        localFileOutputStream.close();
        return;
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      File localFile1;
      do
      {
        Logger.e("LoadPosterThread.saveBmpToSD()", localFileNotFoundException);
        return;
      }
      while (localFile1.list().length <= 0);
      for (int i = -1 + localFile1.list().length; i >= 0; i--)
        localFile1.listFiles()[i].delete();
    }
    catch (IOException localIOException)
    {
      Logger.e("LoadPosterThread.saveBmpToSD()", localIOException);
      return;
    }
    catch (Exception localException)
    {
      Logger.e("LoadPosterThread.saveBmpToSD()", localException);
    }
  }

  public void run()
  {
    setName("LoadPosterThread");
    loadImageFromUrl();
    if (this.callBack != null)
      this.callBack.imageLoaded(this.mBitmap);
  }

  public void setImageCallBack(ImageCallBack paramImageCallBack)
  {
    this.callBack = paramImageCallBack;
  }

  public static abstract interface ImageCallBack
  {
    public abstract void imageLoaded(Bitmap paramBitmap);
  }
}

/* Location:           D:\反编译\dex2jar-0.0.9.15\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.youku.http.LoadPosterThread
 * JD-Core Version:    0.6.0
 */