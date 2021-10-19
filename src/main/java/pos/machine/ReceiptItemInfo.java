package pos.machine;

public class ReceiptItemInfo {
    private ItemInfo itemInfo;
    private int quantity;

    public ReceiptItemInfo(ItemInfo itemInfo, int quantity) {
        this.itemInfo = itemInfo;
        this.quantity = quantity;
    }

    public ItemInfo getItemInfo() {
        return itemInfo;
    }

    public int getQuantity() {
        return quantity;
    }
}
