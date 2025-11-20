import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job; 
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.StringTokenizer;
import java.io.IOException;
public class WordCount {
	public static class WordMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
        
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        StringTokenizer tokenizer = new StringTokenizer(line);
        while (tokenizer.hasMoreTokens()) {
            word.set(tokenizer.nextToken());
            context.write(word, one);
        }
    }
 } 
 public static class WordReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    public void reduce(Text key, Iterable<IntWritable> values, Context context) 
      throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable val : values) {
            sum += val.get();
        }
        context.write(key, new IntWritable(sum));
    }
 }
		public static void main(String args[]) throws Exception {
		// create the object of Configuration class
		Configuration conf = new Configuration();
		// create the object of Job class
		Job job = new Job(conf, "WordCount");
		// Set the data type of output key
		job.setOutputKeyClass(Text.class);
		// Set the data type of output value
		job.setOutputValueClass(IntWritable.class);	
		// Set the data format of output
		job.setOutputFormatClass(TextOutputFormat.class);	
		// Set the data format of input
		job.setInputFormatClass(TextInputFormat.class);	
		// Set the name of Mapper class
		job.setMapperClass(WordMapper.class);	
		// Set the name of Reducer class
		job.setReducerClass(WordReducer.class);	
		// Set the input files path from 0th argument
		FileInputFormat.addInputPath(job, new Path(args[0]));
		// Set the output files path from 1st argument
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		// Execute the job and wait for completion
		job.waitForCompletion(true);
	}
}	
