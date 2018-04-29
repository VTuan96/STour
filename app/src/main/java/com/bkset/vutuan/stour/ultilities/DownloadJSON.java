package com.bkset.vutuan.stour.ultilities;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by vutuan on 13/04/2018.
 */

public class DownloadJSON {
    private static DownloadJSON mInstance;
    private static Context mContext;
    private RequestQueue mRequest;

    public static synchronized DownloadJSON getInstance(Context context){
        if (mInstance==null){
            mInstance=new DownloadJSON(context);
        }
        return mInstance;
    }

    public DownloadJSON(Context context){
        mContext=context;
        mRequest=getRequestQue();
    }

    public RequestQueue getRequestQue(){
        if (mRequest==null){
            mRequest= Volley.newRequestQueue(mContext);
        }
        return mRequest;
    }

    public <T> void addToRequestQue(Request<T> rq){
        getRequestQue().add(rq);
    }

}
