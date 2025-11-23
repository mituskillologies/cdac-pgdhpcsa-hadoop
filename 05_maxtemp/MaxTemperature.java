import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class MaxTemperature {
    /**
     * MAPPER
     * * Input: (Key: Line offset, Value: "StationID,Year,MeasurementType,Value")
     * Output: (Key: Year, Value: Temperature)
     */
    public static class MaxTemperatureMapper 
            extends Mapper<Object, Text, Text, FloatWritable> {
        // Re-use objects to save memory
        private Text yearKey = new Text();
        private FloatWritable tempValue = new FloatWritable();
        public void map(Object key, Text value, Context context) 
                throws IOException, InterruptedException {         
            String line = value.toString();
            String[] parts = line.split(",");
            // Basic data validation
            if (parts.length == 4) {
                try {
                    String year = parts[1];
                    String measurementType = parts[2];                    
                    // Filter for "TMAX" records only
                    if (measurementType.equals("TMAX")) {
                        float temperature = Float.parseFloat(parts[3]);
                      
                        yearKey.set(year);
                        tempValue.set(temperature);                       
                        // Emit (Year, Temperature)
                        context.write(yearKey, tempValue);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Ignoring malformed line: " + line);
                }
            }
        }
    }

    /**
     * REDUCER (and COMBINER)
     * * Input: (Key: Year, Value: [temp1, temp2, temp3, ...])
     * Output: (Key: Year, Value: max_temp)
     */
    public static class MaxTemperatureReducer 
            extends Reducer<Text, FloatWritable, Text, FloatWritable> {
        private FloatWritable result = new FloatWritable();
        public void reduce(Text key, Iterable<FloatWritable> values, Context context) 
                throws IOException, InterruptedException { 
            float maxValue = -100.0f;
            // Iterate through all temperatures for this year
            for (FloatWritable val : values) {
                maxValue = Math.max(maxValue, val.get());
            }
            result.set(maxValue);         
            // Emit (Year, Max_Temperature)
            context.write(key, result);
        }
    }
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: MaxTemperature <input path> <output path>");
            System.exit(-1);
        }       
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Max Temperature Finder");    
        // Set the main class for the job
        job.setJarByClass(MaxTemperature.class);    
        // Set the Mapper class
        job.setMapperClass(MaxTemperatureMapper.class); 
        // --- OPTIMIZATION ---
        // Set the Combiner class. For "max", we can reuse the Reducer logic
        // as a Combiner to reduce network traffic.
        job.setCombinerClass(MaxTemperatureReducer.class);        
        // Set the Reducer class
        job.setReducerClass(MaxTemperatureReducer.class);        
        // Set the final output key and value types
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FloatWritable.class);     
        // Set the input and output paths from command-line arguments
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));     
        // Wait for the job to complete, and exit with 0 (success) or 1 (failure)
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

