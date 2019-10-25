import org.junit.Test;
import static org.junit.Assert.assertEquals;

import insight.DataProcessingUtils;
import insight.Constants;
import insight.BorderCrossingDataKey;
import insight.BorderCrossingComputedData;

import java.util.HashMap;


public class TestJunit {
   @Test
   public void testInput1() {
	DataProcessingUtils dataProcessingUtils = new DataProcessingUtils();

 	String testFileInputOne = "./tests/test_1/input/test_data_1.csv";
	String testFileOutputOne = "./tests/test_1/output/final_output.txt";

	HashMap<BorderCrossingDataKey, BorderCrossingComputedData> finalOutput =
	      dataProcessingUtils.calcTotalCrossings(testFileInputOne);

	dataProcessingUtils.writeFinalOutpout(finalOutput, testFileOutputOne);
      
   }
}
