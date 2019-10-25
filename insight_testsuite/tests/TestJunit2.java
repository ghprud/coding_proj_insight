import org.junit.Test;
import static org.junit.Assert.assertEquals;

import insight.DataProcessingUtils;
import insight.Constants;
import insight.BorderCrossingDataKey;
import insight.BorderCrossingComputedData;

import java.util.HashMap;



public class TestJunit2 {
   @Test
   public void testInput2() {

	DataProcessingUtils dataProcessingUtils = new DataProcessingUtils();
	String testFileInputTwo = "./tests/test_2/input/test_data_2.csv";
	String testFileOutputTwo = "./tests/test_2/output/final_output.txt";


	HashMap<BorderCrossingDataKey, BorderCrossingComputedData> finalOutput =
	  dataProcessingUtils.calcTotalCrossings(testFileInputTwo);

	dataProcessingUtils.writeFinalOutpout(finalOutput, testFileInputTwo);
    
   }
}
