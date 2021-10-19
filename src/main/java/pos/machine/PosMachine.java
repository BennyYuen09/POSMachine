package pos.machine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        ReceiptItemInfo[] receiptItemInfoList = generateReceiptItemInfoList(barcodes.toArray(new String[0]));
        String itemReceiptString = printAllItemOnReceipt(receiptItemInfoList);
        String totalPriceReceiptString = printTotalPrice(receiptItemInfoList);
        return itemReceiptString + totalPriceReceiptString;
    }

    public String[] generateDistinctItemList(String[] barcodeList){
        List<String> outputList = new ArrayList<>();
        for (String barcode : barcodeList){
            if (Arrays.stream(outputList.toArray()).noneMatch(barcode::equals)){
                outputList.add(barcode);
            }
        }
        return outputList.toArray(new String[0]);
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
        return outputList.toArray(new ItemInfo[0]);
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

        return receiptItemInfoList.toArray(new ReceiptItemInfo[0]);
    }

    public String printItem(ReceiptItemInfo receiptItemInfo){
        return ("Name: " + receiptItemInfo.getItemInfo().getName() + ", Quantity: " +
                receiptItemInfo.getQuantity() + ", Unit price: " + receiptItemInfo.getItemInfo().getPrice() +
                " (yuan),  Subtotal: " + receiptItemInfo.getTotalPrice());
    }

    public String printAllItemOnReceipt(ReceiptItemInfo[] receiptItemInfoList){
        StringBuilder output = new StringBuilder();
        output.append("***<store earning no money>Receipt***\n");
        for (ReceiptItemInfo receiptItemInfo : receiptItemInfoList){
            output.append(printItem(receiptItemInfo));
            output.append("\n");
        }
        output.append("----------------------\n");
        return output.toString();
    }

    public String printTotalPrice(ReceiptItemInfo[] receiptItemInfoList){
        int totalPrice = 0;
        for (ReceiptItemInfo receiptItemInfo : receiptItemInfoList){
            totalPrice += receiptItemInfo.getTotalPrice();
        }
        String output = ("Total: " + totalPrice + " (yuan)\n");
        output += "**********************";
        return output;
    }
}