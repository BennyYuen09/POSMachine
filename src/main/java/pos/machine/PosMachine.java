package pos.machine;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {

        return null;
    }

    public String[] generateDistinctItemList(String[] barcodeList){
        List<String> outputList = new ArrayList<>();
        for (String barcode : barcodeList){
            if (!java.util.Arrays.stream(outputList.toArray()).anyMatch(barcode::equals)){
                outputList.add(barcode);
            }
        }
        return (String[])outputList.toArray();
    }

    public ItemInfo getItemInfoFromDatabase(String barcode){
        return ItemDataLoader.loadAllItemInfos().stream().
                filter(itemInfo -> itemInfo.getBarcode().equals(barcode)).findFirst().orElse(null);
    }
}