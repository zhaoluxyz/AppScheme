package main.byhook.scheme;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String SCHEME_DOMAIN = "scheme_activity";

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mTvDomain;

    private TextView mTvParam;

    public static Intent getStartIntent(Context ctx) {
        return new Intent(ctx, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mTvDomain = (TextView) findViewById(R.id.tv_domain);
        mTvDomain.setText(Html.fromHtml("<a href='andy://domain/path?params'>点我试试</a>"));
        mTvDomain.setMovementMethod(LinkMovementMethod.getInstance());

        mTvParam = (TextView) findViewById(R.id.tv_param);
        mTvParam.setText(Html.fromHtml("<a href='andy://scheme_activity?type=0&buffer=这是个字符串'>点我一下</a>"));
        mTvParam.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri uri = intent.getData();
        if (uri != null) {
            dispatchUri(uri);
        } else {
            Log.e(TAG, "Uri is null");
        }
    }

    private void dispatchUri(Uri uri) {
        try {
            final String domain = uri.getAuthority();
            if (TextUtils.equals(SCHEME_DOMAIN, domain)) {
                final String buffer = uri.getQueryParameter("buffer");
                final int type = Integer.valueOf(uri.getQueryParameter("type"));
                Toast.makeText(this, type + "  " + buffer, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Uri Parse Error");
        }
    }

}

