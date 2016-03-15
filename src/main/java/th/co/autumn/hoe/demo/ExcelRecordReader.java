package th.co.autumn.hoe.demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;


/**
 * Reads excel spread sheet , where keys are offset in file and value is the row
 * containing all column as a string.
 */
public class ExcelRecordReader extends RecordReader<ObjectWritable, Text> {

	private LongWritable key;
	private Text value;
	private InputStream is;
	private String[] strArrayofLines;
	private ArrayList<ReceiverDAO> dataList;
	private int currentData;


	@Override
	public void initialize(InputSplit genericSplit, TaskAttemptContext context)
			throws IOException, InterruptedException {

		FileSplit split = (FileSplit) genericSplit;
		Configuration job = context.getConfiguration();
		final Path file = split.getPath();

		FileSystem fs = file.getFileSystem(job);
		FSDataInputStream fileIn = fs.open(split.getPath());

		is = fileIn;
		System.out.println("start ExcelParser class");
		dataList= new ExcelParser().parseExcelData(is);
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		if(currentData < dataList.size()-1) {
			currentData++;
			return true;
		}
		return false;

//
//		if (key == null) {
//			key = new LongWritable(0);
//			value = new Text(strArrayofLines[0]);
//
//		} else {
//
//			if (key.get() < (this.strArrayofLines.length - 1)) {
//				long pos = (int) key.get();
//
//				key.set(pos + 1);
//				value.set(this.strArrayofLines[(int) (pos + 1)]);
//
//				pos++;
//			} else {
//				return false;
//			}
//
//		}
//
//		if (key == null || value == null) {
//			return false;
//		} else {
//			return true;
//		}

	}

	@Override
	public ObjectWritable getCurrentKey() throws IOException,
			InterruptedException {
		
		return dataList.get(currentData);
	}

	@Override
	public Text getCurrentValue() throws IOException, InterruptedException {
		
		return value;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {

		return 0;
		

	}

	@Override
	public void close() throws IOException {
		if (is != null) {
			is.close();
		}

	}

}

