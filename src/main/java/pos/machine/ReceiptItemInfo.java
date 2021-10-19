package pos.machine;

public class ReceiptItemInfo {
    private ItemInfo itemInfo;
    private int quantity;
    private int totalPrice;

    public ReceiptItemInfo(ItemInfo itemInfo, int quantity) {
        this.itemInfo = itemInfo;
        this.quantity = quantity;
        this.totalPrice = itemInfo.getPrice() * quantity;
    }

    public ItemInfo getItemInfo() {
        return itemInfo;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
