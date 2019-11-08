package insight;

import java.util.HashMap;

public class Insight {
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
      DataProcessingUtils dataProcessingUtils = new DataProcessingUtils();

      String output = null;
      String input = null;

      if (args.length == 0){
        // default output
        output = Constants.fileOutputOne;
        input = Constants.fileInputOne;
      }else if (args.length == 2){
        output = args[1];
        input = args[0];
      }else{
        System.out.println("not the right number of arguments");
      }

      HashMap<BorderCrossingDataKey, BorderCrossingComputedData> finalOutput =
              dataProcessingUtils.calcTotalCrossings(input);

      dataProcessingUtils.writeFinalOutpout(finalOutput, output);


  }

}
