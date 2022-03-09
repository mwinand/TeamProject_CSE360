

public class UserCustomer extends User {
    private MenuItem[] order;
    private int positionInLine;
    private long[] paymentInfo;

    public void addToOrder(MenuItem item);
    public void removeFromOrder(MenuItem item);
    public void checkout(bool paymentInfo);
    public void moveUpInLine();
    public void cancelOrder();
    public bool addPaymentInfo(long newPayment);
    public bool isOrderEmpty();
    public MenuItem[] getOrderItems();
    public int getOrderTotal();
    public int getPositionInLine();
    public long[] getPaymentInfo();
}
