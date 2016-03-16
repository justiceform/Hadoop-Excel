package com.charnsilp.hoe.poi.mapper;
import java.io.IOException;

import com.charnsilp.hoe.poi.dao.ReceiverDAO;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ExcelMapper extends Mapper<LongWritable,ReceiverDAO, Text, Text> {

	 /**
     * Excel Spreadsheet is supplied in string form to the mapper.
     * We are simply emitting them for viewing on HDFS.
     */
	public void map(LongWritable key, ReceiverDAO value, Context context)
			throws InterruptedException, IOException {
		if(value == null)
			return;

		context.write(
				new Text(value.getDate()+value.getBank()+value.getBus()),
				new Text(value.toString())
		);
	}
}

