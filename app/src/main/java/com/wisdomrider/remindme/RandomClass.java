/* Created By
  Wisdomrider
  On
  8/3/2018
  */
package com.wisdomrider.remindme;/* Created By
  Wisdomrider
  On
  8/3/2018
  RemindMe
  */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomClass {
    ArrayList<Integer> loadingicons = new ArrayList<>();
    ArrayList<Integer> colors=new ArrayList<Integer>();
    public RandomClass(){
       }


    public Integer getColor(){
        colors.add(R.color.SteelBlue);
        colors.add(R.color.red_primary_dark);
        colors.add(R.color.violate);
        colors.add(R.color.green_primary_dark);
        colors.add(R.color.MediumAquamarine);
        colors.add(R.color.MediumPurple);
        colors.add(R.color.MediumTurquoise);
        colors.add(R.color.orange_primary);
        Collections.shuffle(colors);
        return colors.get(new Random().nextInt(colors.size()-1));


    }

    public Integer getLoadingIcon(){
        loadingicons.clear();
        loadingicons.add(R.drawable.loading);
        loadingicons.add(R.drawable.loading2);
        loadingicons.add(R.drawable.loading3);
        loadingicons.add(R.drawable.ball);
        Collections.shuffle(loadingicons);
        return loadingicons.get(new Random().nextInt(loadingicons.size() - 1));
    }
}
