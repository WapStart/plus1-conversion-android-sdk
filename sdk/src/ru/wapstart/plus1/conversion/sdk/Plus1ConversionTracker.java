/**
 * Copyright (c) 2013, Alexander Klestov <a.klestov@co.wapstart.ru>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the "Wapstart" nor the names
 *     of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package ru.wapstart.plus1.conversion.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Base64;

public final class Plus1ConversionTracker {
	public final class TrackIdNotDefinedException extends Exception {
		private static final long serialVersionUID = -3015095209982953526L;
	}

	public final class CallbackUrlNotDefinedException extends Exception {
		private static final long serialVersionUID = -2570282021275294792L;
	}

	private static final String PREFERENCES_NAME = "Plus1ConversionTracker";
	private static final String PREFERENCES_OPTION_NAME = "firstRun";
	
	private String mConversionUrl = "http://cnv.plus1.wapstart.ru/";
	private Context mContext = null;
	private String mTrackId = null;
	private String mCallbackUrl = null;
	
	public Plus1ConversionTracker(Context context) {
		mContext = context;
	}
	
	public boolean isFirstRun()
	{
		return getPreferences().getBoolean(PREFERENCES_OPTION_NAME, true);
	}
	
	public void run()
		throws TrackIdNotDefinedException, CallbackUrlNotDefinedException
	{
		if (isFirstRun() && isOnline()) {
			String url = getConversionUrl();

			if (url != null) {
				SharedPreferences.Editor editor = getPreferences().edit();
				editor.putBoolean(PREFERENCES_OPTION_NAME, false);
				editor.commit();

				mContext.startActivity(
					new Intent(
						Intent.ACTION_VIEW,
						Uri.parse(url)
					)
				);
			}
		}
	}

	public Plus1ConversionTracker setTrackId(String trackId)
	{
		mTrackId = trackId;

		return this;
	}

	public Plus1ConversionTracker setConversionUrl(String conversionUrl)
	{
		mConversionUrl = conversionUrl;
		
		return this;
	}

	public Plus1ConversionTracker setCallbackUrl(String callbackUrl)
	{
		mCallbackUrl = callbackUrl;

		return this;
	}

	public String getConversionUrl()
		throws TrackIdNotDefinedException, CallbackUrlNotDefinedException
	{
		if (mTrackId == null)
			throw new TrackIdNotDefinedException();

		if (mCallbackUrl == null)
			throw new CallbackUrlNotDefinedException();

		return
			mConversionUrl
			+ mTrackId
			+ "/?callback="
			+ Base64.encodeToString(
				mCallbackUrl.getBytes(),
				Base64.NO_WRAP
			);
	}

	private SharedPreferences getPreferences()
	{
		return
			mContext
				.getApplicationContext()
				.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
	}

	private boolean isOnline()
	{
		ConnectivityManager connectivityManager =
			(ConnectivityManager) mContext.getApplicationContext()
				.getSystemService(
					Context.CONNECTIVITY_SERVICE
				);
		NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();

		return (netInfo != null) && netInfo.isConnected();
	}
}
