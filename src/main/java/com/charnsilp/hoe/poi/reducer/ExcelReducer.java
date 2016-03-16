package com.charnsilp.hoe.poi.reducer;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.charnsilp.hoe.poi.PoiMain;

public class ExcelReducer extends Reducer<Text, Text, Text, Text> {
	
	private static Logger logger = LoggerFactory.getLogger(PoiMain.class);

	@Override
	protected void reduce(Text key, Iterable<Text> values,
			Context context)
			throws IOException, InterruptedException {
	
		Text sum =new Text();
		Iterator<Text> valuesIt = values.iterator();
		if(valuesIt.hasNext()){
			sum = valuesIt.next();
		}

		context.write(null, sum);

	}	


	
}
