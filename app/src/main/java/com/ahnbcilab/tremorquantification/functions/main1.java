package com.ahnbcilab.tremorquantification.functions;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ahnbcilab.tremorquantification.data.Complex;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class main1 {

	private static fft ft;
	private static FillNull fn ;
	private static fitting fg;
	private static double[] Result;
	private static final int srate = 50;
	private static int count = 0;
	static String Clinic_ID;

	private static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
	private static DatabaseReference firebaseLine = firebaseDatabase.getReference("Line RowData");
	public static int line_count;
	private static String lline_count;
	static File file;
	static String m;
	static Context ctx;


	public static double[] main1(String args, Context context, String id) throws IOException {
		// TODO Auto-generated method stub
		ctx = context;
		Clinic_ID = id;
		double[] resultx = new double[4];      double[] resulty = new double[4];// result of fft
		double[][] fitting;//result of fitting
		double[][] fildata;// filled null point data



		/* read data - skip*/
		List<Double> orgX = new ArrayList<Double>();
		List<Double> orgY = new ArrayList<Double>();
		List<Double> time = new ArrayList<Double>();
		String csvFile = args;
		BufferedReader br = null;
		String line = "";
		file = new File(ctx.getFilesDir(), Clinic_ID + "LineRow_num.txt");
		//writeToFile("0", ctx);

		try {
			firebaseLine.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
					for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()){
						line_count = (int) dataSnapshot.getChildrenCount();
						if(line_count < 10){
							lline_count = "0" + line_count;
						}
						else{
							lline_count = String.valueOf(line_count);
						}
						writeToFile(String.valueOf(line_count), ctx);
					}
				}

				@Override
				public void onCancelled(@NonNull DatabaseError databaseError) {
					Log.v("알림", "Failed"); }
			});

			if(file.exists()){
				m = readFromFile(ctx);
				Log.v("SpiralRow", m);
			}
			br = new BufferedReader(new FileReader(csvFile));
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",+");
				orgX.add(Double.parseDouble(data[0]));
				if(lline_count == null)
					lline_count = "00";
				firebaseLine.child("Task No "+lline_count).child("x_position").setValue(orgX);
				firebaseLine.child("Task No "+lline_count).child("y_position").setValue(orgY);
				firebaseLine.child("Task No "+lline_count).child("time").setValue(time);
				orgY.add(Double.parseDouble(data[1]));
				time.add(Double.parseDouble(data[2]));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


		/* data setting - must do */
		int n = time.size();
		Complex[] x = new Complex[n];      Complex[] y = new Complex[n];

		//pre-processing

		fn = new FillNull();
		fildata = new double[2][n];
		fildata[0] = fn.FillNull(orgX,n);
		fildata[1] = fn.FillNull(orgY,n);


		for (int i = 0; i < n; i++) {
			x[i] = new Complex(fildata[0][i], 0);
			y[i] = new Complex(fildata[1][i], 0);
		}

		//separate data
		int start = 1;
		List<Integer> slice = new ArrayList<Integer>();
		Dataslice ds = new Dataslice();
		slice = ds.Dataslice(n);
		int m = slice.size();
		int totalL = 0;

		fitting = new double[m][2];
		fg = new fitting();

		//make position listarray to array
		double[] x_position = new double[n];
		double[] y_position = new double[n];
		int size = 0;
		for(double temp: orgX)
		{
			x_position[size++] = temp;
			if(temp == n)
				break;
		}
		size = 0;
		for(double temp: orgY)
		{
			y_position[size++] = temp;
			if(temp == n)
				break;
		}

		/* ******************************** fitting *************************************/
		for(int k = 0 ; k < m; k++) {


			fitting[k] =  fg.fitting(x_position, y_position, n);


		}



		/* ******************************** FFT *****************************************/
		int padlen = ds.calN(n);
		Complex[] xi = new Complex[padlen];         Complex[] yi= new Complex[padlen];
		xi = ds.zeropadding(x, padlen);       yi = ds.zeropadding(y, padlen);
		Complex[] fftx = ft.fft(xi);        Complex[] ffty = ft.fft(yi);

		// change the data type: Complex ---> double
		double[] absfftx = new double[padlen/2];
		double[] absffty = new double[padlen/2];
		Complex inter; Complex nm = new Complex(padlen,0);

		inter = fftx[0].divides(nm);
		absfftx[0] = inter.abs();
		inter = ffty[0].divides(nm);
		absffty[0] = inter.abs();

		for(int i = 1; i < padlen/2; i++) {
			inter = fftx[i].divides(nm);
			absfftx[i] = 2*inter.abs();
			inter = ffty[i].divides(nm);
			absffty[i] = 2*inter.abs();
		}
		float[] index = new float[padlen/2];
		for(int j = 0; j < padlen/2 ; j++)
			index[j] = (float)srate*(float)j/(float)padlen;

		resultx = ft.analysis(absfftx, index);
		resulty = ft.analysis(absffty, index);
		// end FFT calculation

		Result = new double[6];
		for(int j = 0; j < 4 ; j++) {
			Result[j] = (resultx[j] + resulty[j]) * 0.5;
		}

		for(int j = 4; j < 6 ; j++) {
			Result[j] = 0;
			for(int i = 0; i < m ; i++) {
				Result[j] = fitting[i][j-4];
			}
		}
		return Result;
	}

	// write File
	private static void writeToFile(String data, Context context) {
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(Clinic_ID + "LineRow_num.txt", Context.MODE_PRIVATE));
			outputStreamWriter.write(data);
			outputStreamWriter.close();
		}
		catch (IOException e) {
			Log.e("Exception", "File write failed: " + e.toString());
		}
	}



	// read File
	private static String readFromFile(Context context) {
		String ret = "";
		try {
			InputStream inputStream = context.openFileInput(Clinic_ID + "LineRow_num.txt");
			if ( inputStream != null ) {
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ( (receiveString = bufferedReader.readLine()) != null ) {
					stringBuilder.append(receiveString);
				}

				inputStream.close();
				ret = stringBuilder.toString();
			}
		}
		catch (FileNotFoundException e) {
			Log.e("login activity", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("login activity", "Can not read file: " + e.toString());
		}
		return ret;
	}
}