package net.philadams.bitsy;

/**
 * Created by phil on 2/12/15.
 */
public class Constants {

  // notification IDs
  public static final int ID_NOTIFY_BINARY_BASIC = 1;
  public static final int ID_NOTIFY_NRS = 2;

  // PendingIntent IDs
  public static final int ID_BINARY_FALSE = 100;
  public static final int ID_BINARY_TRUE = 101;
  public static final int[] ID_NRS = { 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210 };

  // various keys, commonly for Intent extras
  public static final String KEY_FROM_CONTENT_INTENT =
      "net.philadams.bitsy.launched_via_notification_default";
  public static final String KEY_BINARY_RESPONSE = "net.philadams.bitsy.binary_response";
  public static final String KEY_NRS_RESPONSE = "net.philadams.bitsy.nrs_response";
}
