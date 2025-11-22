import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class Log {
    public static class LogMapper 
            extends Mapper<Object, Text, Text, FloatWritable> {
        private Text username = new Text();
        private FloatWritable average = new FloatWritable();
        public void map(Object key, Text value, Context context) 
                throws IOException, InterruptedException {         
            String line = value.toString();
            String[] words = line.split("\t");
            username.set(words[0]);
            int add = 0, i = 1;
            while ( i < 8 ) {
            	add += Integer.parseInt(words[i]);
            	i++;
            }
            average.set(add/7.0f);
            if (average.get() >=5 )
            	context.write(username, average);
        }
    }
    public static void main(String[] args) throws Exception {      
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Internet Log Monitor");    
        job.setJarByClass(Log.class);    
        job.setMapperClass(LogMapper.class); 
        job.setNumReduceTasks(0);        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FloatWritable.class);     
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));     
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

