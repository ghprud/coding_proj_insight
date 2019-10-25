package insight;

import java.util.HashMap;

public class Insight {
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
      DataProcessingUtils dataProcessingUtils = new DataProcessingUtils();

      HashMap<BorderCrossingDataKey, BorderCrossingComputedData> finalOutput =
              dataProcessingUtils.calcTotalCrossings(Constants.fileInputOne);

      dataProcessingUtils.writeFinalOutpout(finalOutput, Constants.fileOutputOne);


  }

}
