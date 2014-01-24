package com.youku.http;

import android.os.Handler;
import android.os.Message;
import android.os.Process;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class HttpAsyncTask<Params, Progress, Result>
{
  private static final int CORE_POOL_SIZE = 5;
  private static final int KEEP_ALIVE = 10;
  private static final String LOG_TAG = "HttpAsyncTask";
  private static final int MAXIMUM_POOL_SIZE = 10;
  private static final int MESSAGE_POST_CANCEL = 3;
  private static final int MESSAGE_POST_PROGRESS = 2;
  private static final int MESSAGE_POST_RESULT = 1;
  private static RejectedExecutionHandler handle;
  private static final ThreadPoolExecutor sExecutor;
  private static final InternalHandler sHandler;
  private static final ThreadFactory sThreadFactory;
  private static final LinkedBlockingQueue<Runnable> sWorkQueue = new LinkedBlockingQueue(10);
  private final FutureTask<Result> mFuture = new FutureTask(this.mWorker)
  {
    protected void done()
    {
      try
      {
        Object localObject2 = get();
        localObject1 = localObject2;
        HttpAsyncTask.sHandler.obtainMessage(1, new HttpAsyncTask.HttpAsyncTaskResult(HttpAsyncTask.this, new Object[] { localObject1 })).sendToTarget();
        return;
      }
      catch (ExecutionException localExecutionException)
      {
        throw new RuntimeException("An error occured while executing doInBackground()", localExecutionException.getCause());
      }
      catch (CancellationException localCancellationException)
      {
        HttpAsyncTask.sHandler.obtainMessage(3, new HttpAsyncTask.HttpAsyncTaskResult(HttpAsyncTask.this, (Object[])null)).sendToTarget();
        return;
      }
      catch (Throwable localThrowable)
      {
        throw new RuntimeException("An error occured while executing doInBackground()", localThrowable);
      }
      catch (InterruptedException localInterruptedException)
      {
        while (true)
          Object localObject1 = null;
      }
    }
  };
  private volatile Status mStatus = Status.PENDING;
  private final WorkerRunnable<Params, Result> mWorker = new WorkerRunnable()
  {
    public Result call()
      throws Exception
    {
      Process.setThreadPriority(10);
      return HttpAsyncTask.this.doInBackground(this.mParams);
    }
  };

  static
  {
    handle = new ThreadPoolExecutor.DiscardOldestPolicy();
    sThreadFactory = new ThreadFactory()
    {
      private final AtomicInteger mCount = new AtomicInteger(1);

      public Thread newThread(Runnable paramRunnable)
      {
        return new Thread(paramRunnable, "HttpAsyncTask#" + this.mCount.getAndIncrement());
      }
    };
    sExecutor = new ThreadPoolExecutor(5, 10, 10L, TimeUnit.SECONDS, sWorkQueue, sThreadFactory, handle);
    sHandler = new InternalHandler(null);
  }

  public static void clearQueue()
  {
    sWorkQueue.clear();
  }

  private void finish(Result paramResult)
  {
    onPostExecute(paramResult);
    this.mStatus = Status.FINISHED;
  }

  public final boolean cancel(boolean paramBoolean)
  {
    return this.mFuture.cancel(paramBoolean);
  }

  protected abstract Result doInBackground(Params[] paramArrayOfParams);

  public final HttpAsyncTask<Params, Progress, Result> execute(Params[] paramArrayOfParams)
  {
    if (this.mStatus != Status.PENDING);
    switch (4.$SwitchMap$com$youku$http$HttpAsyncTask$Status[this.mStatus.ordinal()])
    {
    default:
      this.mStatus = Status.RUNNING;
      onPreExecute();
      this.mWorker.mParams = paramArrayOfParams;
      sExecutor.execute(this.mFuture);
      return this;
    case 1:
      throw new IllegalStateException("Cannot execute task: the task is already running.");
    case 2:
    }
    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
  }

  public final Result get()
    throws InterruptedException, ExecutionException
  {
    return this.mFuture.get();
  }

  public final Result get(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException, ExecutionException, TimeoutException
  {
    return this.mFuture.get(paramLong, paramTimeUnit);
  }

  public final Status getStatus()
  {
    return this.mStatus;
  }

  public final boolean isCancelled()
  {
    return this.mFuture.isCancelled();
  }

  protected void onCancelled()
  {
  }

  protected void onPostExecute(Result paramResult)
  {
  }

  protected void onPreExecute()
  {
  }

  protected void onProgressUpdate(Progress[] paramArrayOfProgress)
  {
  }

  protected final void publishProgress(Progress[] paramArrayOfProgress)
  {
    sHandler.obtainMessage(2, new HttpAsyncTaskResult(this, paramArrayOfProgress)).sendToTarget();
  }

  private static class HttpAsyncTaskResult<Data>
  {
    final Data[] mData;
    final HttpAsyncTask mTask;

    HttpAsyncTaskResult(HttpAsyncTask paramHttpAsyncTask, Data[] paramArrayOfData)
    {
      this.mTask = paramHttpAsyncTask;
      this.mData = paramArrayOfData;
    }
  }

  private static class InternalHandler extends Handler
  {
    public void handleMessage(Message paramMessage)
    {
      HttpAsyncTask.HttpAsyncTaskResult localHttpAsyncTaskResult = (HttpAsyncTask.HttpAsyncTaskResult)paramMessage.obj;
      switch (paramMessage.what)
      {
      default:
        return;
      case 1:
        localHttpAsyncTaskResult.mTask.finish(localHttpAsyncTaskResult.mData[0]);
        return;
      case 2:
        localHttpAsyncTaskResult.mTask.onProgressUpdate(localHttpAsyncTaskResult.mData);
        return;
      case 3:
      }
      localHttpAsyncTaskResult.mTask.onCancelled();
    }
  }

  public static enum Status
  {
    static
    {
      FINISHED = new Status("FINISHED", 2);
      Status[] arrayOfStatus = new Status[3];
      arrayOfStatus[0] = PENDING;
      arrayOfStatus[1] = RUNNING;
      arrayOfStatus[2] = FINISHED;
      $VALUES = arrayOfStatus;
    }
  }

  private static abstract class WorkerRunnable<Params, Result>
    implements Callable<Result>
  {
    Params[] mParams;
  }
}

/* Location:           D:\反编译\dex2jar-0.0.9.15\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.youku.http.HttpAsyncTask
 * JD-Core Version:    0.6.0
 */