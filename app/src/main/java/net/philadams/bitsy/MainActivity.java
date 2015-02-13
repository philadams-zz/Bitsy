package net.philadams.bitsy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

  private final String TAG = getClass().getSimpleName();
  private NotificationManager notificationManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (notificationManager == null) {
      notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    // we're only playing with special actions, or specific onClicks in RemoteViews in
    // Bitsy notifications. If someone taps on the notification and prompts the default action,
    // we would normally launch an in-app version of the self-report interface.
    // for now, via getMainActivityPendingIntent, we just display MainActivity with a note
    // explaining this
    if (getIntent() != null && getIntent().getBooleanExtra(Constants.KEY_FROM_CONTENT_INTENT, false)) {
      TextView messageError = (TextView) findViewById(R.id.message_error);
      messageError.setVisibility(View.VISIBLE);
    }

    // just dump Bitsy responses into the MainActivity text for now...
    if (getIntent() != null && getIntent().hasExtra(Constants.KEY_BINARY_RESPONSE)) {
      String binaryResponse = "No";
      if (getIntent().getBooleanExtra(Constants.KEY_BINARY_RESPONSE, false)) {
        binaryResponse = "Yes";
      }
      TextView messageAlert = (TextView) findViewById(R.id.message_alert);
      messageAlert.setText(String.format("When asked, \"%s\", you answered, \"%s\".",
          getString(R.string.notify_binary_basic_text), binaryResponse));
      messageAlert.setVisibility(View.VISIBLE);
    }

    notificationManager.cancelAll();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.notify_binary_basic) {
      notifyBinaryBasic();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public void notifyBinaryBasic() {

    // notification base
    Notification.Builder mBuilder = new Notification.Builder(this)
        .setContentTitle(getString(R.string.notify_title))
        .setContentText(getString(R.string.notify_binary_basic_text))
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentIntent(getMainActivityPendingIntent());

    // notification yes/no actions
    Intent noIntent = new Intent(this, MainActivity.class);
    noIntent.putExtra(Constants.KEY_BINARY_RESPONSE, false);
    PendingIntent noPendingIntent =
        PendingIntent.getActivity(this, Constants.ID_BINARY_FALSE, noIntent,
            PendingIntent.FLAG_ONE_SHOT);
    Intent yesIntent = new Intent(this, MainActivity.class);
    yesIntent.putExtra(Constants.KEY_BINARY_RESPONSE, true);
    PendingIntent yesPendingIntent =
        PendingIntent.getActivity(this, Constants.ID_BINARY_TRUE, yesIntent,
            PendingIntent.FLAG_ONE_SHOT);

    // add actions to notification builder
    mBuilder.addAction(0, getString(R.string.notify_binary_false), noPendingIntent)
        .addAction(0, getString(R.string.notify_binary_true), yesPendingIntent);

    notificationManager.notify(Constants.ID_NOTIFY_BINARY_BASIC, mBuilder.build());
  }

  private PendingIntent getMainActivityPendingIntent() {
    Intent intent = new Intent(this, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    intent.putExtra(Constants.KEY_FROM_CONTENT_INTENT, true);
    PendingIntent pendingIntent =
        PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    return pendingIntent;
  }
}
