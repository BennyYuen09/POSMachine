package pos.machine;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

    public ItemInfo[] generateItemInfoList(String[] distinctBarcodeList){
        List<ItemInfo> outputList = new ArrayList<>();
        for (String barcode : distinctBarcodeList){
            outputList.add(getItemInfoFromDatabase(barcode));
        }
        return (ItemInfo[]) outputList.toArray();
    }

    public ReceiptItemInfo[] generateReceiptItemInfoList(String[] barcodeList){
        String [] distinctBarcodeList = generateDistinctItemList(barcodeList);
        ItemInfo[] itemInfoList = generateItemInfoList(distinctBarcodeList);

        List<ReceiptItemInfo> receiptItemInfoList = new ArrayList<>();
        for (ItemInfo itemInfo: itemInfoList){
            int quantity = (int)Arrays.stream(barcodeList).
                    filter(barcode -> barcode.equals(itemInfo.getBarcode())).count();
            receiptItemInfoList.add(new ReceiptItemInfo(itemInfo, quantity));
        }

        return (ReceiptItemInfo[]) receiptItemInfoList.toArray();
    }

    public String printItem(ReceiptItemInfo receiptItemInfo){
        return ("Name: " + receiptItemInfo.getItemInfo().getName() + ", Quantity: " +
                receiptItemInfo.getQuantity() + ", Unit price: " + receiptItemInfo.getItemInfo().getPrice() +
                " (yuan),  Subtotal: " + receiptItemInfo.getTotalPrice());
    }
}