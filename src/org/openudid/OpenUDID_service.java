package org.openudid;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;

public class OpenUDID_service extends Service
{
  public IBinder onBind(Intent paramIntent)
  {
    return new Binder()
    {
      public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      {
        SharedPreferences localSharedPreferences = OpenUDID_service.this.getSharedPreferences("openudid_prefs", 0);
        paramParcel2.writeInt(paramParcel1.readInt());
        paramParcel2.writeString(localSharedPreferences.getString("openudid", null));
        return true;
      }
    };
  }
}

/* Location:           D:\反编译\dex2jar-0.0.9.15\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     org.openudid.OpenUDID_service
 * JD-Core Version:    0.6.0
 */