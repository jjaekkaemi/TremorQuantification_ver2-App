package com.ahnbcilab.tremorquantification.functions;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Calculate how closely the given line is drawn to match the given line.
 * input :  X, Y data of drawn spiral
 * output : total distance/ mean / standard deviation
 * */

public class fitting {
	private static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
	//private static DatabaseReference firebaseCorr = firebaseDatabase.getReference("CorrTest");

	private int srate = 50;
	public static int startX = 0;
	public static int startY = 0;
	public static double[] distance;

	public static  baseline bring(double[] x, double[] y, double[] t) {
		return new baseline(x , y, t);}

	//obj = base, org = subject
	public double[] fitting(double[] orgX, double[] orgY, int m){
		double[] result = new double[2];
		int n = m;
		double[] objX = new double[n] ;      double[] objY = new double[n]  ;       double[] t = new double[n]  ;
		baseline base = bring(objX, objY, t);
		base.setting(n);
		objX = base.getArray1();
		objY = base.getArray2();
		ArrayList<Double> base_x = new ArrayList<Double>();
		ArrayList<Double> base_y = new ArrayList<Double>();
		ArrayList<Double> pos_x = new ArrayList<Double>();
		ArrayList<Double> pos_y = new ArrayList<Double>();
		for(Double d : objX)
			base_x.add(d);
		for(Double d : orgX)
			pos_x.add(d);
		for(Double d : objY)
			base_y.add(d);
		for(Double d : orgY)
			pos_y.add(d);



		//firebaseCorr.child("x_position").setValue(pos_x);
		//firebaseCorr.child("y_position").setValue(pos_y);
		//firebaseCorr.child("base x").setValue(base_x);
		//firebaseCorr.child("base y").setValue(base_y);


		int err;
		for(int i=0;i<orgX.length;i++){

			err = (int) (orgX[i]-objX[i]);

		}


		//initialize xcorrelation
		double[] xcorr_array = xcorr(orgX, objX);

		ArrayList<Double> xcorr_list = new ArrayList<>();
		for(Double d: xcorr_array)
			xcorr_list.add(d);



		int idx = getMaxValue(xcorr_array);
		Log.v("01알림 처리전", ""+idx);
		idx = base_x.size() - idx;

		idx = Math.abs(idx);
		Log.v("01알림 처리후", ""+idx);


		//make delta fucntion
		double[] delta = new double[orgX.length]; // already initialize 0
		delta[idx] = 1;


		//make delayed signal
		double[] shifted_x = conv(orgX, delta);


		ArrayList<Double> shifted_list = new ArrayList<>();
		for(Double d: shifted_x)
			shifted_list.add(d);


		double[] base_new_x = new double[shifted_x.length];

		//make length equally
		for(int i = 0; i < objX.length; i++)
		{
			base_new_x[i] = objX[i];
		}
		for(int i = objX.length; i < shifted_x.length; i++)
		{
			base_new_x[i] = 0;
		}

		ArrayList<Double> base_new_list = new ArrayList<>();
		for(Double d: base_new_x)
			base_new_list.add(d);

		double R =  new SpearmansCorrelation().correlation(shifted_x, base_new_x);
		R = Math.abs(R) * 100;

		Log.v("01알림", "코릴레이션 결과"+ R);

		//firebaseCorr.child("xcorr").setValue(xcorr_list);
		//firebaseCorr.child("shifted_x").setValue(shifted_list);
		//firebaseCorr.child("new_base").setValue(base_new_list);


		return result;


	}

	/* ******************************** xcorr *****************************************/
	public static double[] xcorr(double[] a, double[] b)
	{
		int len = a.length;
		if(b.length > a.length)
			len = b.length;

		return xcorr(a, b, len-1);

		// reverse b in time
       /*double[] brev = new double[10];

       for(int x = 0; x < b.length; x++)
           brev[x] = b[b.length-x-1];

       return conv(a, brev);*/
	}

	/**
	 * Convolves sequences a and b.  The resulting convolution has
	 * length a.length+b.length-1.
	 */
	public static double[] conv(double[] a, double[] b)
	{
		double[] y = new double[a.length+b.length-1];

		// make sure that a is the shorter sequence
		if(a.length > b.length)
		{
			double[] tmp = a;
			a = b;
			b = tmp;
		}

		for(int lag = 0; lag < y.length; lag++)
		{
			y[lag] = 0;

			int start = 0;

			if(lag > a.length-1)
				start = lag-a.length+1;

			int end = lag;

			if(end > b.length-1)
				end = b.length-1;


			for(int n = start; n <= end; n++)
			{

				y[lag] += b[n]*a[lag-n];
			}
		}

		return(y);
	}

	/**
	 * Computes the cross correlation between sequences a and b.
	 * maxlag is the maximum lag to
	 */
	public static double[] xcorr(double[] a, double[] b, int maxlag)
	{
		double[] y = new double[2*maxlag+1];
		Arrays.fill(y, 0);

		for(int lag = b.length-1, idx = maxlag-b.length+1;
			lag > -a.length; lag--, idx++)
		{
			if(idx < 0)
				continue;

			if(idx >= y.length)
				break;

			// where do the two signals overlap?
			int start = 0;
			// we can't start past the left end of b
			if(lag < 0)
			{
				//System.out.println("b");
				start = -lag;
			}

			int end = a.length-1;
			// we can't go past the right end of b
			if(end > b.length-lag-1)
			{
				end = b.length-lag-1;
				//System.out.println("a "+end);
			}

			//System.out.println("lag = " + lag +": "+ start+" to " + end+"   idx = "+idx);
			for(int n = start; n <= end; n++)
			{
				//System.out.println("  bi = " + (lag+n) + ", ai = " + n);
				y[idx] += a[n]*b[lag+n];
			}
			//System.out.println(y[idx]);
		}

		return(y);
	}
	public static int getMaxValue(double[] numbers){
		double maxValue = numbers[0];
		int i;
		int cnt=0;
		for( i=1;i < numbers.length;i++){
			if(numbers[i] > maxValue){
				maxValue = numbers[i];
				cnt = i;
			}
		}
		return cnt;

	}


	static class baseline {
		private double[] baseX;
		private double[] baseY;
		private double[] t;
		private float min = 0;//baseline scope
		private double max = 4 * Math.PI;

		public baseline() {
		}

		public baseline(double[] array1, double[] array2, double[] time) {
			this.baseX = array1;
			this.baseY = array2;
			this.t = time;
		}

		public double[] getArray1() {
			return baseX;
		}

		public double[] getArray2() {
			return baseY;
		}

		public double[] getArray3() {
			return t;
		}

		public baseline setting(int length) {
			this.t = new double[length];
			for (int i = 0; i < length; i++) {
				this.t[i] = min + i * (max - min) / (length - 1);
				this.baseX[i] = t[i] * Math.cos(2.5 * t[i]) * 50 + fitting.startX;
				this.baseY[i] = t[i] * Math.sin(2.5 * t[i]) * 50 + fitting.startY;
			}
			return new baseline(baseX, baseY, t);
		}

	}}