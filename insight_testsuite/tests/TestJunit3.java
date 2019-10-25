import org.junit.Test;
import static org.junit.Assert.assertEquals;

import insight.DataProcessingUtils;
import insight.Constants;
import insight.BorderCrossingDataKey;
import insight.BorderCrossingComputedData;

import java.util.HashMap;


public class TestJunit3 {
   @Test
   public void testInput3() {

	DataProcessingUtils dataProcessingUtils = new DataProcessingUtils();

	String testFileInputThree = "./tests/test_3/input/test_data_3.csv";
	String testFileOutputThree = "./tests/test_3/output/final_output.txt";

	HashMap<BorderCrossingDataKey, BorderCrossingComputedData> finalOutput =
	  dataProcessingUtils.calcTotalCrossings(testFileInputThree);

	dataProcessingUtils.writeFinalOutpout(finalOutput, testFileOutputThree);
      
   }
}
