import org.junit.Test;
import static org.junit.Assert.assertEquals;

import insight.DataProcessingUtils;
import insight.Constants;

public class TestJunit3 {
   @Test
   public void testInput3() {

      DataProcessingUtils dataProcessingUtils = new DataProcessingUtils();

      String testFileInput = "./tests/test_3/input/test_data_3.txt";
      String testFileOutput = "./tests/test_3/output/report.txt";
      dataProcessingUtils.writeTotalDrugCost(
        dataProcessingUtils.fillDrugDataCache
          ( testFileInput),
            testFileOutput
          );
   }
}
