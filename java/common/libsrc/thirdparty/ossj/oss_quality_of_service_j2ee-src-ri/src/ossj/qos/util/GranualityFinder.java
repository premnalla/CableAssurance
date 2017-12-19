package ossj.qos.util;

/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Copyright 2002 Ericsson AB
 * @author Hooman Tahamtani, Katarina Wahlström
 * @version 0.1
 */

import java.util.*;

public class GranualityFinder {

private Set mainSet = null;
private Set compareSet = null;

  public GranualityFinder() {
     mainSet = new HashSet();
     compareSet = new HashSet();
  }//constructor

  public void setFirstSet(int[] inData) throws java.lang.IllegalStateException {
     if(mainSet.isEmpty() == false)
        mainSet.clear();
    if(inData == null || inData.length <= 0)
       throw new java.lang.IllegalStateException("Input data is null or Illegal index");
     try{
       for(int i = 0; i < inData.length; i++){
          mainSet.add(new Integer(inData[i]));
        }
      }
      catch (Exception e){
         throw new java.lang.IllegalStateException("First granuality set array: " + e.getMessage());
      }

  }//setFirstSet

  public void addToCompareSet(int[] compSet) throws java.lang.IllegalStateException{
     if(compSet == null || compSet.length <= 0)
       throw new java.lang.IllegalStateException("compSet data is null or Illegal index");
     if(compSet != null){  //just to be on the safe side
       if(compareSet.isEmpty()){
          try{
            for(int i = 0; i < compSet.length; i++) {
                compareSet.add(new Integer(compSet[i]));
            }
            getCommon(compareSet);
          }
          catch (Exception e){
             throw new java.lang.IllegalStateException("Compare granuality set array: " + e.getMessage());
          }

        }
        else{
           compareSet.clear();
            try{
              for(int i = 0; i < compSet.length; i++) {
                  compareSet.add(new Integer(compSet[i]));
              }
              getCommon(compareSet);
            }
            catch (Exception e){
               throw new java.lang.IllegalStateException("Compare granuality set array: " + e.getMessage());
            }
        }
      }
      else{
          throw new java.lang.IllegalStateException("granuality is null");
      }
  }//addToCompareSet(int[] compSet)

  private int[] convertToIntArray(Set set){

     int[] granuality = new int[set.size()];
     Object[] obj = new Object[set.size()];
     obj = set.toArray();
     for (int i = 0; i < obj.length; i++){
        granuality[i] = ((Integer) obj[i]).intValue();
     }
     return granuality;
  }//convertToIntArray(Set set)

  public int[] getGranuality() throws java.lang.IllegalStateException {

      if(mainSet == null || mainSet.isEmpty())
           throw new java.lang.IllegalStateException("There are no common Granualities.");
        return convertToIntArray(mainSet);
  }//getGranuality()

  private void getCommon(Set set){

     if(set.isEmpty() == false && compareSet != null){
        mainSet.retainAll(set);
      }
  }
}//GranualityFinder