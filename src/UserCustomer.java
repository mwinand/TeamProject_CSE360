import java.util.ArrayList;

public class UserCustomer extends User {
    private ArrayList<MenuItem> order;
    private int positionInLine;
    private long paymentInfo;

    public UserCustomer() {
        order = new ArrayList<MenuItem>();
        positionInLine = -1;
        paymentInfo = -1;
    }
    public void addToOrder(MenuItem item) {
        order.add(item);
    }
    public Boolean removeFromOrder(MenuItem item) {
        int loc;
        Boolean removed = false;
        loc = order.indexOf(item);
        if (loc != -1) {
            if (order.remove(loc) == item) removed = true;
        }
        return removed;
    }
    public void checkout() {
        if (paymentInfo == -1 || validatePaymentInfo()) {/*TODO: prompt user for new payment info, and update paymentInfo*/}

    }
    public void moveUpInLine() {
        positionInLine--;
    }
    public void cancelOrder() {
        order.clear();
        positionInLine = -1;
    }
    public void setPaymentInfo(long newPayment) {
        paymentInfo = newPayment;
    }
    public Boolean validatePaymentInfo() {
        Boolean valid = false;
        if (getPaymentInfo() >= 100000000000L) valid = true;
        return valid;
    }
    public Boolean isOrderEmpty() {
        return order.isEmpty();
    }
    public MenuItem[] getOrderItems() {
        MenuItem[] arr = new MenuItem[order.size()];
        arr = order.toArray(arr);
	    return arr;
    }
    public double getOrderTotal() {
        double totalPrice = 0;
        for (int i = 0; i < order.size(); i++) {
            totalPrice = totalPrice + order.get(i).getPrice();
        }
        return totalPrice;
    }
    public int getPositionInLine() {
        return positionInLine;
    }
    public long getPaymentInfo() {
        return paymentInfo;
    }
}
