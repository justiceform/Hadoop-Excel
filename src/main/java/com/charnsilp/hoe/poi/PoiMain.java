package com.charnsilp.hoe.poi;

import com.charnsilp.hoe.poi.reducer.ExcelReducer;
import com.charnsilp.hoe.poi.service.ExcelCreator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.charnsilp.hoe.poi.inputformat.ExcelInputFormat;
import com.charnsilp.hoe.poi.mapper.ExcelMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PoiMain  {
	
	private static Logger logger = LoggerFactory.getLogger(PoiMain.class);

	public static void main(String[] args) throws Exception {
		
		logger.info("Driver started");

		Job job = new Job();
		job.setJarByClass(PoiMain.class);
		job.setJobName("Excel Record Reader"); 
		job.setMapperClass(ExcelMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setReducerClass(ExcelReducer.class);

		removeExistingOutputDir(args[1]);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		System.out.println("--->Tor  set setInputFormatClass for ExcelInputFormat ");
		job.setInputFormatClass(ExcelInputFormat.class);

		job.waitForCompletion(true);

		saveResultToExcel(new Path(args[1]+"/part-r-00000"),new Path(args[1]+"/result.xlsx"));

	}

	private static void saveResultToExcel(Path path, Path excelFile) throws IOException{
		// Get Hadoop Configuration
		Configuration conf = new Configuration();
		// Call HDFS base on configuration
		FileSystem hdfs = FileSystem.get(conf);

		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

		// Check if mapreduce result exists
		if (hdfs.exists(path)){
			FSDataInputStream fis = hdfs.open(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

			while(reader.ready()){
				String outLine = reader.readLine();
				// Split String from ReceiverDAO toString() method
				String[] row = outLine.split(",");

				result.add( new ArrayList<String>(Arrays.asList(row)));
			}
			fis.close();
		}

		if(!result.isEmpty()){
//			System.out.println(result.toString());

			if ( hdfs.exists( excelFile )) { hdfs.delete( excelFile, true ); }

			// Create a new file in HDFS
			OutputStream os = hdfs.create( excelFile);
			// Send OutputStrean to ExcelCreator for save excel data
			new ExcelCreator().create(result,os);
			hdfs.close();

		}
	}

	private static void removeExistingOutputDir(String path) throws IOException {
		// configuration should contain reference to your namenode
		FileSystem fs = FileSystem.get(new Configuration());
		// true stands for recursively deleting the folder you gave
		fs.delete(new Path(path), true);
	}

	
}
