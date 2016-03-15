package th.co.autumn.hoe.demo;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelMapper extends Mapper<LongWritable, Text, String, IntWritable> {

	 /**
     * Excel Spreadsheet is supplied in string form to the mapper.
     * We are simply emitting them for viewing on HDFS.
     */
	public void map(LongWritable key, Text value, Context context)
			throws InterruptedException, IOException {
		String line = value.toString();
		String result1 = "";
		int result2 = 0;
		double tmp1 = 0.0;
		
		StringTokenizer stringTokenizer = new StringTokenizer(line,"\t");
		
		stringTokenizer.nextToken();
		result1 = stringTokenizer.nextToken();
		
		tmp1 = Double.parseDouble(stringTokenizer.nextToken());
		result2 = new Double(tmp1).intValue();
		
		result1 = result1+","+result2;
		
		context.write(result1, null);
	
	}
}

