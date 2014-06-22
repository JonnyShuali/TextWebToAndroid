package com.roy.compsms;

import com.google.android.gms.auth.GoogleAuthUtil;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;

public class getEmailGoogle {
	  public static String getEmail(Context context) {
		    AccountManager accountManager = AccountManager.get(context); 
		    Account account = getAccount(accountManager);

		    if (account == null) {
		    	Log.i("account", "null");
		      return null;
		    } else {
		    	Log.i("account", account.name);
		      return account.name;
		    }
		  }
	  private static Account getAccount(AccountManager accountManager) {
		    Account[] accounts = accountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
		    Account account;
		    if (accounts.length > 0) {
		      account = accounts[0];      
		    } else {
		      account = null;
		    }
		    return account;
		  }

}
