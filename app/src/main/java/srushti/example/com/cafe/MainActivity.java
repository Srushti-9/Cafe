package srushti.example.com.cafe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {
int qty=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    }
    /**

     * This method displays the given text on the screen.

     */

    private void displayMessage(String message) {

        TextView orderSummaryTextView = (TextView) findViewById(R.id.orderSummary_text_view);

        orderSummaryTextView.setText(message);

    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {
        //int noOfCoffees = 2;
        CheckBox whippedCreamCheckBox=(CheckBox) findViewById(R.id.whipped_cream_checkBox);
        CheckBox chocolateCheckBox=(CheckBox) findViewById(R.id.chocolate_checkBox);

        EditText nameField=(EditText)findViewById(R.id.name_field);
        String name=nameField.getText().toString();

        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();
       // Log.v("MainActivity","Has whipped Cream"+ hasWhippedCream);

        int price=calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage=createOrderSummary(name,price,hasWhippedCream,hasChocolate);

        /*Sending Intent*/
        Intent intent=new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        /*intent.putExtra(Intent.EXTRA_EMAIL,addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);*/
        intent.putExtra(Intent.EXTRA_SUBJECT,"Your order of Coffee" + name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intent);
        }

        displayMessage(priceMessage);
    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is whether or not we should include whipped cream topping in the price
     * @param addChocolate    is whether or not we should include chocolate topping in the price
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // First calculate the price of one cup of coffee
        int basePrice = 5;

        // If the user wants whipped cream, add $1 per cup
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        // If the user wants chocolate, add $2 per cup
        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        // Calculate the total order price by multiplying by the quantity
        return qty * basePrice;
    }


    private String createOrderSummary(String name,int price, boolean addWhippedCream,boolean addChocolate)
    {
        /*String priceMessage ="Name:"+name;
        priceMessage +="\nAdd Whipped Cream?"+addWhippedCream;
        priceMessage +="\nAdd Chocolate?"+addChocolate;
        priceMessage += "\nQuantity:"+qty+"\nTotal: $"+price;
        priceMessage +="\nThank You!";*/
        String priceMessage =getString(R.string.order_name,name);
        priceMessage +="\n" + getString(R.string.add_whipped,addWhippedCream);
        priceMessage +="\n" + getString(R.string.add_chocolate,addChocolate);
        priceMessage +="\n" + getString(R.string.order_summary_quantity,+qty);
        priceMessage +="\n" + getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(price));
        priceMessage +="\n"+getString(R.string.thank_u);
        return priceMessage;
    }
     /* This method is called when the minus button is clicked.
     */

    public void decrement(View view) {
        if(qty==1)
        {
            Toast.makeText(this, "You cannot have less than 1 Coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        qty=qty-1;
        display(qty);

    }
    /* This method is called when the plus button is clicked.
     */

    public void increment(View view) {
        if(qty==100)
        {
            Toast.makeText(this,"You cannot have more than 100 Coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        qty=qty+1;
        display(qty);

    }

    /**
     * This method displays the given quantity value on the screen.
     */

    private void display(int number) {

        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);

        quantityTextView.setText("" + number);

    }
    /**

     * This method displays the given price on the screen.

     */

    private void displayPrice(int number) {

        TextView orderSummaryTextView = (TextView) findViewById(R.id.orderSummary_text_view);

        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));

    }

}