import org.junit.Test;
import static org.junit.Assert.assertEquals;

import insight.DataProcessingUtils;
import insight.Constants;

public class TestJunit2 {
   @Test
   public void testInput2() {

      DataProcessingUtils dataProcessingUtils = new DataProcessingUtils();

      String testFileInput = "./tests/test_2/input/test_data_2.txt";
      String testFileOutput = "./tests/test_2/output/report.txt";
      dataProcessingUtils.writeTotalDrugCost(
        dataProcessingUtils.fillDrugDataCache
          ( testFileInput),
            testFileOutput
          );
   }
}
