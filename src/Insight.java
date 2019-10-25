package insight;

public class Insight {
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
      // TODO code application logic here
      DataProcessingUtils dataProcessingUtils = new DataProcessingUtils();

      HashMap<BorderCrossingDataKey, BorderCrossingComputedData> finalOutput =
              dataProcessingUtils.calcTotalCrossings("/Users/pg/Downloads/Border_Crossing_Entry_Data.csv");
      dataProcessingUtils.writeFinalOutpout(finalOutput, "/Users/pg/Downloads/final_output.txt");


  }

}
