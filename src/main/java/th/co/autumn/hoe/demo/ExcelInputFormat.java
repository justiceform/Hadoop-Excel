package th.co.autumn.hoe.demo;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

/**
 * <p>
 * An {@link org.apache.hadoop.mapreduce.InputFormat} for excel spread sheet files.
 * Multiple sheets are supported
 * <p/>
 * Keys are the position in the file, and values are the row containing all columns for the
 * particular row.
 */
public class ExcelInputFormat extends FileInputFormat<LongWritable,Text>{

	@Override
	public RecordReader<LongWritable, Text> createRecordReader(InputSplit split,
			TaskAttemptContext context) throws IOException, InterruptedException {
		
		System.out.println("--> in ExcelInputFormat --> start ExcelRecordReader class");
		return new ExcelRecordReader();
	}

}
