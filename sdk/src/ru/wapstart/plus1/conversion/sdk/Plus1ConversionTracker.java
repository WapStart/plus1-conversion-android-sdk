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
import android.net.Uri;

public final class Plus1ConversionTracker {
	private static final String PREFERENCES_NAME = "Plus1ConversionTracker";
	private static final String PREFERENCES_OPTION_NAME = "firstRun";
	private static final int APP_TYPE_ID = 3;
	
	private String mConversionUrl = "http://cnv.plus1.wapstart.ru/";
	private Context mContext = null;
	private int mApplicationId;
	
	public Plus1ConversionTracker(Context context, int applicationId) {
		mContext = context;
		mApplicationId = applicationId;
	}
	
	public boolean isFirstRun()
	{
		return getPreferences().getBoolean(PREFERENCES_OPTION_NAME, true);
	}
	
	public void run()
	{
		if (isFirstRun()) {
			SharedPreferences.Editor editor = getPreferences().edit();
			editor.putBoolean(PREFERENCES_OPTION_NAME, false);
			editor.commit();

			mContext.startActivity(
				new Intent(
					Intent.ACTION_VIEW,
					Uri.parse(
						getConversionUrl()
					)
				)
			);
		}
	}

	public Plus1ConversionTracker setConversionUrl(String conversionUrl)
	{
		mConversionUrl = conversionUrl;
		
		return this;
	}

	public String getConversionUrl()
	{
		return
			mConversionUrl
			+ "app/"
			+ String.valueOf(APP_TYPE_ID)
			+ "/"
			+ String.valueOf(mApplicationId);
	}

	private SharedPreferences getPreferences()
	{
		return
			mContext
				.getApplicationContext()
				.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
	}
}
