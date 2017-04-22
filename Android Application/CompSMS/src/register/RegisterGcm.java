package register;

import java.io.IOException;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.roy.compsms.MainActivity;
import com.roy.compsms.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

//Class which register the device into GCM servers
//Returns RegID string
// Registers the application with GCM servers asynchronously.
public class RegisterGcm extends AsyncTask<Void, Void, Void> {
	static final String SENDER_ID = "";//enter your sendrID
	GoogleCloudMessaging gcm;
	String regID,email,pass;
	ProgressDialog progressDialog;
	private Context context;
	public RegisterGcm(String email, String pass,Context context)
	{
		gcm=GoogleCloudMessaging.getInstance(MainActivity.context);
		this.pass=pass;
		this.email=email;
		this.context=context;
		
	}
@Override
protected void onPreExecute() {
	super.onPreExecute();
    progressDialog = ProgressDialog.show(context, "", context.getText(R.string.registering) , true);
}
			@Override
			protected Void doInBackground(Void... params) {
				String RegID="";
				try {
					RegID=gcm.register(SENDER_ID);
				} catch (IOException e) {
					Log.i("Developer:", "Registerion error "+e.getMessage());
					e.printStackTrace();
					return null;
				}
				Log.i("Developer:", RegID);
				//register at back-end
				new registerBackEnd(this.email, this.pass,RegID).execute(null,null);
				this.regID=RegID;
				return null;
			}
@Override
protected void onPostExecute(Void result) {
	super.onPostExecute(result);
		progressDialog.dismiss();
	
}
						
		}
		


