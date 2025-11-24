import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.text.DecimalFormat;
public class AverageRating {

    public static class RatingMapper 
            extends Mapper<Object, Text, Text, Text> {
        private Text outMovieID = new Text();
        private DoubleWritable outRating = new DoubleWritable();
        public void map(Object key, Text value, Context context) 
                throws IOException, InterruptedException {
            
            String line = value.toString();
            String[] parts = line.split("\t"); // Data is tab-separated
            if (parts.length == 4) {
                try {
                    String movieID = parts[1];
                    double rating = Double.parseDouble(parts[2]);
                    
                    outMovieID.set(movieID);
                    outRating.set(rating);
                    
                    // Emit (MovieID, Rating)
                    context.write(outMovieID, new Text(outRating));
                    
                } catch (NumberFormatException e) {
                    // Ignore lines with bad data
                    System.err.println("Ignoring malformed line: " + line);
                }
            }
        }
    }
    public static class AverageRatingReducer 
            extends Reducer<Text, DoubleWritable, Text, Text> {

        private DoubleWritable result = new DoubleWritable();

        static final DecimalFormat df = new DecimalFormat("0.00");
        public void reduce(Text key, Iterable<DoubleWritable> values, Context context) 
                throws IOException, InterruptedException {
            
            double sum = 0.0;
            int count = 0;
            
            for (DoubleWritable val : values) {
                sum += val.get();
                count++;
            }

            if (count > 0) {
                double average = sum / count;
                
                String formattedResult = df.format(average);
                result.set(formattedResult);
                context.write(key, result);
            }
        }
    }

    /**
     * DRIVER
     * Configures and runs the MapReduce job.
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: AverageRating <input path> <output path>");
            System.exit(-1);
        }
        
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Average Movie Rating");
        
        // Set the main class for the job
        job.setJarByClass(AverageRating.class);
        
        // Set the Mapper and Reducer classes
        job.setMapperClass(RatingMapper.class);
        job.setReducerClass(AverageRatingReducer.class);
        
        // Set the final output key and value classes
        // (This should match the Reducer's output)
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);
        
        // Set the Mapper's output key and value classes
        // (This is what the Reducer will receive)
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(DoubleWritable.class);
        
        // Set the input and output paths from command-line arguments
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        // Wait for the job to complete
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

