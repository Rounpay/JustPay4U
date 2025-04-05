package com.solution.app.justpay4u.ApiHits;

import static android.util.Log.VERBOSE;
import static com.roundpay.emoneylib.Network.AC.getUnsafeOkHttpClient;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.RootedCheck.RootBeer;
import com.therockakash.shaketrace.logger.Level;
import com.therockakash.shaketrace.logger.PrettyLoggingInterceptor;

import java.io.File;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    private static final int REQUEST_PERMISSIONS = 324;
    private static Retrofit retrofit = null, retrofitTest = null;
    private static OkHttpClient.Builder okHttpClient;
    private static HttpLoggingInterceptor interceptor;

    public static Retrofit getClient() {
        if (retrofit == null) {
            Dispatcher dispatcher = new Dispatcher();
            dispatcher.setMaxRequests(1);
            dispatcher.setMaxRequestsPerHost(1);
            interceptor = new HttpLoggingInterceptor();
            okHttpClient = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                PrettyLoggingInterceptor.Builder prettyInterceptor = new PrettyLoggingInterceptor.Builder()
                        .setLevel(Level.BASIC)
                        .log(VERBOSE)
                        .setCashDir(new File("/data/user/0/com.solution.app.justpay4u/cache"));
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpClient.addNetworkInterceptor(prettyInterceptor.build());
                // okHttpClient.addInterceptor(interceptor);
                retrofit = new Retrofit.Builder()
                        .baseUrl(ApplicationConstant.INSTANCE.baseUrl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient.build())
                        .build();
                return retrofit;
            } else {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                        .readTimeout(10, TimeUnit.MINUTES)
                        .connectTimeout(10, TimeUnit.MINUTES)
                        .writeTimeout(10, TimeUnit.MINUTES)
                        .dispatcher(dispatcher)
                        .retryOnConnectionFailure(false)
                        .build();
                ////////////////////////////////////////////////////
                retrofit = new Retrofit.Builder()
                        .baseUrl(ApplicationConstant.INSTANCE.baseUrl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build();
                return retrofit;
            }

        }
        return retrofit;

    }

    public static Retrofit getClientTest() {
        if (retrofitTest == null) {
            ConnectionSpec requireTls12 = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2)
                    .build();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            OkHttpClient okHttpClient;
            Dispatcher dispatcher = new Dispatcher();
            dispatcher.setMaxRequests(1);
            dispatcher.setMaxRequestsPerHost(1);

            if (Build.VERSION.SDK_INT < 23) {

                okHttpClient = getUnsafeOkHttpClient()
                        .connectionSpecs(Arrays.asList(requireTls12, ConnectionSpec.CLEARTEXT))
                        .addInterceptor(interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                        .readTimeout(10, TimeUnit.MINUTES)
                        .connectTimeout(10, TimeUnit.MINUTES)
                        .writeTimeout(10, TimeUnit.MINUTES)
                        .dispatcher(dispatcher)
                        .retryOnConnectionFailure(false)
                        .build();
            } else {
                okHttpClient = new OkHttpClient.Builder()
                        .connectionSpecs(Arrays.asList(requireTls12, ConnectionSpec.CLEARTEXT))
                        .addInterceptor(interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                        .readTimeout(10, TimeUnit.MINUTES)
                        .connectTimeout(10, TimeUnit.MINUTES)
                        .writeTimeout(10, TimeUnit.MINUTES)
                        .dispatcher(dispatcher)
                        .retryOnConnectionFailure(false)
                        .build();
            }
            ////////////////////////////////////////////////////
            retrofitTest = new Retrofit.Builder()
                    .baseUrl("http://85.10.235.153/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofitTest;

    }

    public static Retrofit getClient(Activity mContext) {
        if (!isSimAvailable(mContext)) {
            CustomAlertDialog mCustomAlertDialog = new CustomAlertDialog(mContext);
            mCustomAlertDialog.showSimErrorDialog(mContext.getResources().getString(R.string.sim_error_msg));
            return null;
        } else if (isVpnConnected(mContext)) {
            CustomAlertDialog mCustomAlertDialog = new CustomAlertDialog(mContext);
            mCustomAlertDialog.showVpnEnableDialog();
            return null;
        } else if (isRootedDevice(mContext)) {
            CustomAlertDialog mCustomAlertDialog = new CustomAlertDialog(mContext);
            mCustomAlertDialog.showSimErrorDialog(mContext.getResources().getString(R.string.rooted_error_msg));
            return null;
        } else {
            if (retrofit == null) {
                Dispatcher dispatcher = new Dispatcher();
                dispatcher.setMaxRequests(1);
                dispatcher.setMaxRequestsPerHost(1);
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                        .readTimeout(10, TimeUnit.MINUTES)
                        .connectTimeout(10, TimeUnit.MINUTES)
                        .writeTimeout(10, TimeUnit.MINUTES)
                        .dispatcher(dispatcher)
                        .retryOnConnectionFailure(false)
                        .build();
                ////////////////////////////////////////////////////
                retrofit = new Retrofit.Builder()
                        .baseUrl(ApplicationConstant.INSTANCE.baseUrl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build();
            }
            return retrofit;
        }
    }

    public static boolean isVpnConnected(Context mContext) {
        ConnectivityManager m_ConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        List<NetworkInfo> connectedNetworks = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= 21) {
            Network[] networks = m_ConnectivityManager.getAllNetworks();

            for (Network n : networks) {
                NetworkInfo ni = m_ConnectivityManager.getNetworkInfo(n);

                if (ni.isConnectedOrConnecting()) {
                    connectedNetworks.add(ni);
                }
            }
            boolean bHasVPN = false;
            for (NetworkInfo ni : connectedNetworks) {
                bHasVPN |= (ni.getType() == ConnectivityManager.TYPE_VPN);
            }
            return bHasVPN;
        } else {
            List<String> networkList = new ArrayList<>();
            try {
                for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                    if (networkInterface.isUp())
                        networkList.add(networkInterface.getName());
                }
            } catch (Exception ex) {

            }

            return networkList.contains("tun0") || networkList.contains("ppp0");

        }


    }


    public static boolean isSimAvailable(Context context) {
        String[] nonActualDeviceArray = context.getResources().getStringArray(R.array.nonActualDeviceSimArray);
        ArrayList<String> nonActualDeviceStringArray = new ArrayList(Arrays.asList(nonActualDeviceArray));

        boolean isSimAvailable = true;
        boolean isActualDevice = true;
        TelephonyManager telMgr = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);

        String networkProviderName = telMgr.getNetworkOperatorName();
        String networkSimOperatorName = telMgr.getSimOperatorName();

        if (networkProviderName != null && !networkProviderName.isEmpty() && nonActualDeviceStringArray.contains(networkProviderName) ||
                networkSimOperatorName != null && !networkSimOperatorName.isEmpty() && nonActualDeviceStringArray.contains(networkSimOperatorName)) {
            isSimAvailable = false;
            isActualDevice = false;
        } else {
            isActualDevice = true;
            int simState = telMgr.getSimState();
            switch (simState) {
                case TelephonyManager.SIM_STATE_ABSENT:
                    isSimAvailable = false;
                    break;
                case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                    isSimAvailable = false;
                    break;
                case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                    isSimAvailable = false;
                    break;
                case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                    isSimAvailable = false;
                    break;
                case TelephonyManager.SIM_STATE_READY:
                    isSimAvailable = true;
                    break;
                case TelephonyManager.SIM_STATE_UNKNOWN:
                    isSimAvailable = false;
                    break;
            }
        }
        if (!isSimAvailable && isActualDevice) {
            ArrayList<String> networkProvider = getNetworkProvider(context);
            if (networkProvider != null && networkProvider.size() > 0) {
                isSimAvailable = true;
            }
        }
        return BuildConfig.DEBUG ? true : isSimAvailable;
    }

    public static ArrayList<String> getNetworkProvider(Context context) {
        ArrayList<String> carrierNameArray = new ArrayList<>();
        int permissionCheck = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_PHONE_STATE);

        Activity activity = (Activity) context;
        String networkProvider = "";

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PERMISSIONS);
        } else {
            //TODO
            TelephonyManager telephonyManager = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);

            try {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                    final SubscriptionManager subscriptionManager;
                    subscriptionManager = SubscriptionManager.from(context);
                    final List<SubscriptionInfo> activeSubscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
                    for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
                        final CharSequence carrierName = subscriptionInfo.getCarrierName();
                        carrierNameArray.add(carrierName.toString());
                    }
                } else {
                    networkProvider = telephonyManager.getNetworkOperatorName();
                    if (networkProvider != null && !networkProvider.isEmpty()) {
                        carrierNameArray.add(networkProvider);
                    }
                }
            } catch (Exception e) {

            }
        }


        return carrierNameArray;
    }

    public static boolean isRootedDevice(Context context) {

        boolean rootedDevice = false;

        RootBeer mRootBeer = new RootBeer(context);
        if (mRootBeer.isRooted()) {
            rootedDevice = true;
        }

        return rootedDevice;
    }

}
