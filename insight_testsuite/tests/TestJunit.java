import org.junit.Test;
import static org.junit.Assert.assertEquals;

import insight.DataProcessingUtils;
import insight.Constants;

public class TestJunit {
   @Test
   public void testInput1() {

      DataProcessingUtils dataProcessingUtils = new DataProcessingUtils();

      String testFileInputOne = "./tests/test_1/input/test_data_1.txt";
      String testFileOutputOne = "./tests/test_1/output/report.txt";
      dataProcessingUtils.writeTotalDrugCost(
        dataProcessingUtils.fillDrugDataCache
          ( testFileInputOne),
            testFileOutputOne
          );
   }
}
