package register;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;

public class registerBackEnd extends AsyncTask<Void, Void, Void> {
	public static final String ServerURL = ""; //Enter your sever Url here
	private static final String SALT = "r9pNDTbajMjIw"; //Change your salt
	static final String TAG = "Developer";

	String regId;
	String email;
	String pincode;

	// Context context;
	public registerBackEnd(String email, String pincode, String regId) {
		super();
		this.regId = regId;
		this.email = email;
		this.pincode = pincode;
	}

	protected Void doInBackground(Void... params) {
		Log.i(TAG, "Got HERe");
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(ServerURL);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("regId", this.regId));
		nameValuePairs.add(new BasicNameValuePair("email", this.email));
		nameValuePairs.add(new BasicNameValuePair("password",
				md5Java(this.pincode + registerBackEnd.SALT)));
		nameValuePairs.add(new BasicNameValuePair("salt", SALT));
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Execute HTTP Post Request
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			String responseBody = httpclient.execute(httppost, responseHandler);
			Log.i(TAG, responseBody);
		} catch (ClientProtocolException e) {
			Log.i(TAG, "ERORR POST1");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i(TAG, "ERORR POST2");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Calculate MD5 String of given String
	 * 
	 * @param message
	 *            given string
	 * @return MD5 String
	 */
	public static String md5Java(String message) {
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(message.getBytes("UTF-8"));

			// converting byte array to Hexadecimal String
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}

			digest = sb.toString();

		} catch (UnsupportedEncodingException ex) {
			Log.i(TAG, "Encryption error1");
		} catch (NoSuchAlgorithmException ex) {
			Log.i(TAG, "Encryption error2");
		}
		return digest;
	}
}
