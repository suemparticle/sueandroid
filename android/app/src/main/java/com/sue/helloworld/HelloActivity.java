package com.sue.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.location.LocationManager;
import android.os.Debug;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import android.widget.TextView;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.util.Log;

import com.mparticle.MParticle;
import com.mparticle.MParticleOptions;
import com.mparticle.commerce.Impression;
import com.mparticle.consent.ConsentState;
import com.mparticle.consent.GDPRConsent;
import com.mparticle.identity.IdentityApiRequest;
import com.mparticle.identity.MParticleUser;
import com.mparticle.internal.MPUtility;
import com.mparticle.MPEvent;
import com.mparticle.MParticle.EventType;
import com.mparticle.commerce.Product;
import com.mparticle.commerce.CommerceApi;
import com.mparticle.commerce.CommerceEvent;
import com.mparticle.commerce.TransactionAttributes;

import com.appboy.Appboy;
import androidx.fragment.app.FragmentActivity;
import com.google.firebase.iid.FirebaseInstanceId;

import android.content.Context;
import android.widget.Toast;

public class HelloActivity extends AppCompatActivity {

    private Context mApplicationContext;
    private WebView myWebView;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Logged Out");

                Intent activity1Intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity1Intent);

                MParticle.getInstance().Identity().logout(IdentityApiRequest.withEmptyUser().build());
            }
        });

        //Tracking events
        Button trackEvent = findViewById(R.id.trackEvent);
        trackEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Event tracked");

//                // Note: may return null if the SDK has yet to acquire a user via IDSync!
                MParticleUser user = MParticle.getInstance().Identity().getCurrentUser();
                //Log.d("userbeforemodify",user.getUserIdentities().toString());

                IdentityApiRequest modifyRequest = IdentityApiRequest.withEmptyUser()
                        .email("fair6@example.com")
                        .build();

                MParticle.getInstance().Identity().modify(modifyRequest);


//
//                Map<String, String> customAttributes = new HashMap<String, String>();
//                customAttributes.put("Publisher", "Organic");
//                customAttributes.put("Campaign","Organic");
//
//                MPEvent event = new MPEvent.Builder("EventA", EventType.Other)
//                        .customAttributes(customAttributes)
//                        .build();
//
//                MParticle.getInstance().logEvent(event);
//
//                boolean network_enabled;
//                try {
//                    System.out.println(LocationManager.NETWORK_PROVIDER);
//                    //System.io.LocationManager.NETWORK_PROVIDER;
//                } catch(Exception ex) {
//                    ex.printStackTrace();
//                }



                //

                TextView tv = (TextView)findViewById(R.id.status);
                tv.setText("Tracked Event");

            }
        });

        //Track purchases
        Button purchase = findViewById(R.id.purchaseButton);
        purchase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Purchased item");

                // Note: may return null if the SDK has yet to acquire a user via IDSync!
                MParticleUser currentUser = MParticle.getInstance().Identity().getCurrentUser();

                Map<String, String> customAttributes = new HashMap<String, String>();
                customAttributes.put("category", "Destination Intro");
                customAttributes.put("title", "Paris");

                // 1. Create the products
                Product product = new Product.Builder("ProductX", "sku-888", 100.00)
                        .quantity(4.0)
                        .build();

                // 2. Summarize the transaction
                TransactionAttributes attributes = new TransactionAttributes("transaction-5678")
                        .setRevenue(2000.00)
                        .setTax(30.00);

                // 3. Log the purchase event
                CommerceEvent event = new CommerceEvent.Builder(Product.PURCHASE, product)
                        .transactionAttributes(attributes)
                        .customAttributes(customAttributes)
                        .build();
                MParticle.getInstance().logEvent(event);
                //Appboy.getInstance(mApplicationContebraze3@xt).logPurchase("test111","USD",new BigDecimal("1000"));

                //Impression impression = new Impression("Suggested Products List", product);brae


//                MParticle.getInstance().logEvent(
//                        new CommerceEvent.Builder(impression).build()
//                );


                TextView tv = (TextView)findViewById(R.id.status);
                tv.setText("Purchased Item");
            }
        });

        //Set user attribute
        Button setAttribute = findViewById(R.id.setAttribute);
        setAttribute.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                System.out.println("set attribute");
//
//                // Note: may return null if the SDK has yet to acquire a user via IDSync!
                MParticleUser currentUser = MParticle.getInstance().Identity().getCurrentUser();

                // Set user attributes associated with the user
                currentUser.setUserAttribute("phone_number","2015559509");


                TextView tv = (TextView)findViewById(R.id.status);
                tv.setText("Set User Attribute");

                // LOCATION TRACKING
                //MParticle.getInstance().enableLocationTracking(LocationManager.NETWORK_PROVIDER, 30*1000, 1000);
//                final Location location = new Location("network");
//                location.setLatitude(1.2345d);
//                location.setLongitude(1.2345d);
//                MParticle.getInstance().setLocation(location);


                MParticleUser user = MParticle.getInstance().Identity().getCurrentUser();
                Log.d("useraftermodify",user.getUserIdentities().toString());
            }
        });

        MParticle.getInstance().Messaging().enablePushNotifications("709599249964");


        //Send push with Braze
        Button sendPush = findViewById(R.id.sendPush);
        sendPush.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("sent push");

                // Note: may return null if the SDK has yet to acquire a user via IDSync!
//                MParticleUser currentUser = MParticle.getInstance().Identity().getCurrentUser();
//
//                TextView tv = (TextView)findViewById(R.id.status);
//                tv.setText("Sent Push");
//
//                //MParticle.getInstance().Messaging().displayPushNotificationByDefault(true);
//
//                MPEvent event = new MPEvent.Builder("pushTrigger", EventType.Navigation)
//                        .build();
//
//                MParticle.getInstance().logEvent(event);
                MParticle.getInstance().setOptOut(false);

            }
        });

        //Open webview
        Button openWeb = findViewById(R.id.openWeb);
        openWeb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("opened webview");

                MParticleUser user = MParticle.getInstance().Identity().getCurrentUser();

                // WEBVIEW
                final WebView myWebView = (WebView) findViewById(R.id.webview);
                myWebView.addJavascriptInterface(new WebAppInterface(mApplicationContext), "Android");
                myWebView.addJavascriptInterface(new message(), "Show");

                WebSettings webSettings = myWebView.getSettings();
                myWebView.setWebChromeClient(new MyWebChromeClient());
                myWebView.setWebViewClient(new WebViewClient());
                webSettings.setJavaScriptEnabled(true);

                MParticle.getInstance().registerWebView(myWebView);
                myWebView.loadUrl("https://www.sueyoungchung.com");
//                myWebView.setWebViewClient(new WebViewClient(){
//                    public void onPageFinished(WebView view, String weburl){
//                        myWebView.loadUrl("javascript:testconsole()");
//                    }
//                });
                myWebView.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                        Log.d("MYWEBSITE", consoleMessage.message() + " -- From line "
                                + consoleMessage.lineNumber() + " of "
                                + consoleMessage.sourceId());
                        return super.onConsoleMessage(consoleMessage);
                    }
                });




// Create GDPRConsent
//                GDPRConsent locationCollectionConsent = GDPRConsent.builder(true)
//                        .document("location_collection_agreement_v4")
//                        .location("17 Cherry Tree Lane")
//                        .hardwareId("IDFA:a5d934n0-232f-4afc-2e9a-3832d95zc702")
//                        .build();
//
//                GDPRConsent parentalConsent = GDPRConsent.builder(true)
//                        .document("parental_consent_agreement_v2")
//                        .location("17 Cherry Tree Lane")
//                        .hardwareId("IDFA:a5d934n0-232f-4afc-2e9a-3832d95zc702")
//                        .build();
//
//// Add to your consent state
//                ConsentState state = ConsentState.builder()
//                        .addGDPRConsentState("cross device", locationCollectionConsent)
//                        .addGDPRConsentState("Performance", parentalConsent)
//                        .build();
//
//                user.setConsentState(state);


            }

            class message {
                @JavascriptInterface
                public String msg() {
                    return "Hello World!!";
                }
            }


        });

        // Firebase push tokens cannot be obtained on the main thread.
//        final Context applicationContext = this;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    String token = FirebaseInstanceId.getInstance().getToken("709599249964", "FCM");
//                    Appboy.getInstance(applicationContext).registerAppboyPushMessages(token);
//                } catch (Exception e) {
//                    Log.e(TAG, "Exception while registering Firebase token with Braze.", e);
//                }
//            }
//        }).start();


    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d("LogTag", message);
            result.confirm();
            return true;
        }
    }


}