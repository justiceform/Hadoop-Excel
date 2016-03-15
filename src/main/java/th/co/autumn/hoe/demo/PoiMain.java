package th.co.autumn.hoe.demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PoiMain  {
	
	private static Logger logger = LoggerFactory.getLogger(PoiMain.class);

	public static void main(String[] args) throws Exception {
		
		logger.info("Driver started");

		Job job = new Job();
		job.setJarByClass(PoiMain.class);
		job.setJobName("Excel Record Reader"); 
		job.setMapperClass(ExcelMapper.class);
//		job.setReducerClass(ExcelReducer.class);
		job.setNumReduceTasks(0);

		removeExistingOutputDir(args[1]);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		System.out.println("--->Tor  set setInputFormatClass for ExcelInputFormat ");
		job.setInputFormatClass(ExcelInputFormat.class);

		job.waitForCompletion(true);
	}

	private static void removeExistingOutputDir(String path) throws IOException {
		// configuration should contain reference to your namenode
		FileSystem fs = FileSystem.get(new Configuration());
// true stands for recursively deleting the folder you gave
		fs.delete(new Path(path), true);
	}

	
}
