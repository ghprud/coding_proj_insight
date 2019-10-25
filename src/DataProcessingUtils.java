
package insight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author pg
 */
public class DataProcessingUtils {

    public DataProcessingUtils(){

    }

    public HashMap<BorderCrossingDataKey, BorderCrossingComputedData> calcTotalCrossings(String fileInput ){
      HashMap<BorderCrossingDataKey, BorderCrossingComputedData> mapBorderCrossingData = new HashMap<>();
      HashMap<Date, ArrayList<Date>> mapPrevMonths = new HashMap<>();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");

      // read the file
      BufferedReader dataReader = null;
      try {
          String line = null;
          Integer count = 0;

          // for file operations
          dataReader = new BufferedReader(new FileReader(fileInput));
          BorderCrossingDataKey borderCrossingData = null;
          BorderCrossingComputedData borderCrossingComputedData = null;

          Long totalCrossings = new Long(0);

          while((line = dataReader.readLine()) != null){
              String[] tempLineData = line.split(",");
              count++;

              if (count > 1){
                  borderCrossingData = new BorderCrossingDataKey();

                  //what can go wrong in parsing??

                  // combination of border, date and travel source
                  // example: US-Canada Border + 03/01/2019 + Truck Containers Full
                  borderCrossingData.setBorder(tempLineData[3]);
                  borderCrossingData.setCrossedDate(LocalDateTime.parse(tempLineData[4], formatter));
                  borderCrossingData.setSource(tempLineData[5]);

                  if (mapBorderCrossingData.get(borderCrossingData) == null){
                      Long noOfCrossings = Long.parseLong(tempLineData[6]);

                      borderCrossingComputedData = new BorderCrossingComputedData(noOfCrossings, new Long(0));
                      mapBorderCrossingData.put(borderCrossingData, borderCrossingComputedData);
                  }else{
                      borderCrossingComputedData = mapBorderCrossingData.get(borderCrossingData);


                      totalCrossings = borderCrossingComputedData.getValue() + Long.parseLong(tempLineData[6]);
                      borderCrossingComputedData.setValue(totalCrossings);
                      mapBorderCrossingData.put(borderCrossingData, borderCrossingComputedData);

                  }
              }
          }

          //calcRunningAverage(mapBorderCrossingData);
      } catch (IOException ex) {
          Logger.getLogger(DataProcessingUtils.class.getName()).log(Level.SEVERE, null, ex);
      }finally {
          try {
              dataReader.close();

          } catch (IOException ex) {
              Logger.getLogger(DataProcessingUtils.class.getName()).log(Level.SEVERE, null, ex);
          }
      }

      //System.out.println(mapBorderCrossingData.toString());

      return mapBorderCrossingData;

  }

  public ArrayList<FinalOutput> getListToSort(HashMap<BorderCrossingDataKey, BorderCrossingComputedData> mapBorderCrossingData){
    ArrayList<FinalOutput> finalList = new ArrayList<>();
    FinalOutput finalOP = null;

    for (Map.Entry<BorderCrossingDataKey, BorderCrossingComputedData> entry : mapBorderCrossingData.entrySet()) {
        BorderCrossingDataKey key = entry.getKey();
        BorderCrossingComputedData value = entry.getValue();

        finalOP = new FinalOutput();
        finalOP.setBorder(key.getBorder());
        finalOP.setCrossedDate(key.getCrossedDate());
        finalOP.setTravelSource(key.getSource());
        finalOP.setValue(value.getValue());
        finalOP.setAverage(value.getAverage());

        finalList.add(finalOP);
    }

    return finalList;
  }

  public List<FinalOutput> getSortedList(ArrayList<FinalOutput> finalList){
      return finalList
                .stream()
                .sorted(Comparator.comparing(FinalOutput::getCrossedDate)
                        .thenComparing(FinalOutput::getValue)
                        .thenComparing(FinalOutput::getTravelSource)
                        .thenComparing(FinalOutput::getBorder))
                .collect(Collectors.toList());
  }


  public void writeFinalOutpout(HashMap<BorderCrossingDataKey, BorderCrossingComputedData> mapBorderCrossingData, String fileOutput){
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
        ArrayList<FinalOutput> finalList = new ArrayList<>();
        FinalOutput finalOP = null;

        // total crossings for different months
        long totalCrossingsMonth = 0l;

        // total number of months
        Long noOfMonths = 0l;

        String writerHeader = "Border,Date,Measure,Value,Average\n";
        try {
            fileWriter = new FileWriter(fileOutput);
            bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(writerHeader);

            finalList = getListToSort(mapBorderCrossingData);

            List<FinalOutput> sortedList = getSortedList(finalList);

            FinalOutput outerFinal = null;
            FinalOutput innerFinal = null;

            // calculate the running average
            for (int outer = sortedList.size() - 1; outer >= 0; outer--){
                outerFinal = sortedList.get(outer);
                for (int inner = outer - 1; inner >= 0; inner--){
                    innerFinal = sortedList.get(inner);

                    if (innerFinal.getBorder().equals(outerFinal.getBorder())
                            && innerFinal.getTravelSource().equals(outerFinal.getTravelSource())
                            && (outerFinal.getCrossedDate().compareTo(innerFinal.getCrossedDate()) > 0)){
                    totalCrossingsMonth += innerFinal.getValue();
                    noOfMonths += 1;
                }
                }

                // set the outerFinal value
                // avoid divide by zero error
                if (noOfMonths == 0){
                    noOfMonths = 1l;
                }
                long roundedOutput = (long) Math.ceil((double) totalCrossingsMonth / (double) noOfMonths);
                outerFinal.setAverage(roundedOutput);

                noOfMonths = 0l;
                totalCrossingsMonth = 0l;
            }

            // write the final output
            for (int i = sortedList.size() - 1; i >= 0; i--){
                String outputData = sortedList.get(i).getBorder() + ","
                        + sortedList.get(i).getCrossedDate().format(formatter)
                        + ","
                        + sortedList.get(i).getTravelSource()
                        + "," + sortedList.get(i).getValue()
                        + "," + sortedList.get(i).getAverage() + "\n";

                bufferedWriter.write(outputData);
            }

        }catch (IOException ex) {
            Logger.getLogger(DataProcessingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
          try {
              bufferedWriter.close();
              fileWriter.close();

          } catch (IOException ex) {
              Logger.getLogger(DataProcessingUtils.class.getName()).log(Level.SEVERE, null, ex);
          }
      }

  }
  }
