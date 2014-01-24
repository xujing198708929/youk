package org.openudid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.Settings.Secure;
import android.util.Log;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class OpenUDID_manager
  implements ServiceConnection
{
  private static final boolean LOG = true;
  private static String OpenUDID = null;
  public static final String PREFS_NAME = "openudid_prefs";
  public static final String PREF_KEY = "openudid";
  public static final String TAG = "OpenUDID";
  private static boolean mInitialized = false;
  private final Context mContext;
  private List<ResolveInfo> mMatchingIntents;
  private final SharedPreferences mPreferences;
  private final Random mRandom;
  private Map<String, Integer> mReceivedOpenUDIDs;

  private OpenUDID_manager(Context paramContext)
  {
    this.mPreferences = paramContext.getSharedPreferences("openudid_prefs", 0);
    this.mContext = paramContext;
    this.mRandom = new Random();
    this.mReceivedOpenUDIDs = new HashMap();
  }

  private void generateOpenUDID()
  {
    Log.d("OpenUDID", "Generating openUDID");
    OpenUDID = Settings.Secure.getString(this.mContext.getContentResolver(), "android_id");
    if ((OpenUDID == null) || (OpenUDID.equals("9774d56d682e549c")) || (OpenUDID.length() < 15))
      OpenUDID = new BigInteger(64, new SecureRandom()).toString(16);
  }

  private void getMostFrequentOpenUDID()
  {
    if (!this.mReceivedOpenUDIDs.isEmpty())
    {
      TreeMap localTreeMap = new TreeMap(new ValueComparator(null));
      localTreeMap.putAll(this.mReceivedOpenUDIDs);
      OpenUDID = (String)localTreeMap.firstKey();
    }
  }

  public static String getOpenUDID()
  {
    if (!mInitialized)
      Log.e("OpenUDID", "Initialisation isn't done");
    return OpenUDID;
  }

  public static boolean isInitialized()
  {
    return mInitialized;
  }

  private void startService()
  {
    if (this.mMatchingIntents.size() > 0)
    {
      Log.d("OpenUDID", "Trying service " + ((ResolveInfo)this.mMatchingIntents.get(0)).loadLabel(this.mContext.getPackageManager()));
      ServiceInfo localServiceInfo = ((ResolveInfo)this.mMatchingIntents.get(0)).serviceInfo;
      Intent localIntent = new Intent();
      localIntent.setComponent(new ComponentName(localServiceInfo.applicationInfo.packageName, localServiceInfo.name));
      this.mContext.bindService(localIntent, this, 1);
      this.mMatchingIntents.remove(0);
      return;
    }
    getMostFrequentOpenUDID();
    if (OpenUDID == null)
      generateOpenUDID();
    Log.d("OpenUDID", "OpenUDID: " + OpenUDID);
    storeOpenUDID();
    mInitialized = true;
  }

  private void storeOpenUDID()
  {
    SharedPreferences.Editor localEditor = this.mPreferences.edit();
    localEditor.putString("openudid", OpenUDID);
    localEditor.commit();
  }

  public static void sync(Context paramContext)
  {
    OpenUDID_manager localOpenUDID_manager = new OpenUDID_manager(paramContext);
    OpenUDID = localOpenUDID_manager.mPreferences.getString("openudid", null);
    if (OpenUDID == null)
    {
      localOpenUDID_manager.mMatchingIntents = paramContext.getPackageManager().queryIntentServices(new Intent("org.OpenUDID.GETUDID"), 0);
      Log.d("OpenUDID", localOpenUDID_manager.mMatchingIntents.size() + " services matches OpenUDID");
      if (localOpenUDID_manager.mMatchingIntents != null)
        localOpenUDID_manager.startService();
      return;
    }
    Log.d("OpenUDID", "OpenUDID: " + OpenUDID);
    mInitialized = true;
  }

  public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
  {
    try
    {
      Parcel localParcel1 = Parcel.obtain();
      localParcel1.writeInt(this.mRandom.nextInt());
      Parcel localParcel2 = Parcel.obtain();
      paramIBinder.transact(1, Parcel.obtain(), localParcel2, 0);
      String str;
      if (localParcel1.readInt() == localParcel2.readInt())
      {
        str = localParcel2.readString();
        if (str != null)
        {
          Log.d("OpenUDID", "Received " + str);
          if (!this.mReceivedOpenUDIDs.containsKey(str))
            break label149;
          this.mReceivedOpenUDIDs.put(str, Integer.valueOf(1 + ((Integer)this.mReceivedOpenUDIDs.get(str)).intValue()));
        }
      }
      while (true)
      {
        this.mContext.unbindService(this);
        startService();
        return;
        label149: this.mReceivedOpenUDIDs.put(str, Integer.valueOf(1));
      }
    }
    catch (RemoteException localRemoteException)
    {
      while (true)
        Log.e("OpenUDID", "RemoteException: " + localRemoteException.getMessage());
    }
  }

  public void onServiceDisconnected(ComponentName paramComponentName)
  {
  }

  private class ValueComparator
    implements Comparator
  {
    private ValueComparator()
    {
    }

    public int compare(Object paramObject1, Object paramObject2)
    {
      if (((Integer)OpenUDID_manager.this.mReceivedOpenUDIDs.get(paramObject1)).intValue() < ((Integer)OpenUDID_manager.this.mReceivedOpenUDIDs.get(paramObject2)).intValue())
        return 1;
      if (OpenUDID_manager.this.mReceivedOpenUDIDs.get(paramObject1) == OpenUDID_manager.this.mReceivedOpenUDIDs.get(paramObject2))
        return 0;
      return -1;
    }
  }
}

/* Location:           D:\反编译\dex2jar-0.0.9.15\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     org.openudid.OpenUDID_manager
 * JD-Core Version:    0.6.0
 */