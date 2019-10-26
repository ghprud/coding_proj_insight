
package insight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author pg
 */
public class DataProcessingUtils {
    
    public DataProcessingUtils(){
        
    }
    
    public BorderCrossingDataKey getBorderDataKey(String border, String crossedDate,
            String source){
        BorderCrossingDataKey borderCrossingData = null;new BorderCrossingDataKey();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
        
        boolean isEmpty = false;
        
        if (border.isEmpty() || crossedDate.isEmpty() || source.isEmpty()){
            isEmpty = true;
        }
        
        // consider the missing values...
        // If any of the border, or the date or the source is missing(empty)
        // the data is incomplete and do not include in the calculations
        // do not include the row data in the hash map.
        if (!isEmpty){      
            borderCrossingData = new BorderCrossingDataKey();
            borderCrossingData.setBorder(border);
            borderCrossingData.setCrossedDate(LocalDateTime.parse(crossedDate, formatter));
            borderCrossingData.setSource(source);         
        }      
        return borderCrossingData;
    }
    
    public HashMap<BorderCrossingDataKey, BorderCrossingComputedData> calcTotalCrossings(String fileInput ){
      HashMap<BorderCrossingDataKey, BorderCrossingComputedData> mapBorderCrossingData = new HashMap<>();      
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
                  //borderCrossingData = new BorderCrossingDataKey();
                  
                  //what can go wrong in parsing??
                  
                  // combination of border, date and travel source
                  // example: US-Canada Border + 03/01/2019 + Truck Containers Full
                  borderCrossingData = getBorderDataKey(tempLineData[3], tempLineData[4], 
                          tempLineData[5]);
                  
                  if (borderCrossingData != null){
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
          }
      } catch (IOException ex) {
          Logger.getLogger(DataProcessingUtils.class.getName()).log(Level.SEVERE, null, ex);
      }finally {
          try {
              dataReader.close();

          } catch (IOException ex) {
              Logger.getLogger(DataProcessingUtils.class.getName()).log(Level.SEVERE, null, ex);
          }
      }      
      return mapBorderCrossingData;
      
  }
    
  private ArrayList<FinalOutput> getListToSort(HashMap<BorderCrossingDataKey, BorderCrossingComputedData> mapBorderCrossingData){
    ArrayList<FinalOutput> finalList = new ArrayList<>();
    FinalOutput finalOP = null;
    
    if (mapBorderCrossingData != null){
        for (Map.Entry<BorderCrossingDataKey, BorderCrossingComputedData> entry 
                : mapBorderCrossingData.entrySet()) {
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
    }
    return finalList;
  } 
  
  private List<FinalOutput> getSortedList(ArrayList<FinalOutput> finalList){
      
      if (finalList != null){
        return finalList.stream()
              .sorted(Comparator.comparing(FinalOutput::getCrossedDate)
                      .thenComparing(FinalOutput::getValue)
                      .thenComparing(FinalOutput::getTravelSource)
                      .thenComparing(FinalOutput::getBorder))
              .collect(Collectors.toList());
      }
      return null;
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