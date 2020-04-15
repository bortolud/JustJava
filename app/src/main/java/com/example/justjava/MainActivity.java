/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int numberOfCoffees=1;
    String priceMessage;
    int price = 0;

//    boolean hasWhipped;
    /**
     * This method is called when the order button is clicked.
     */
    public int updatePrice(boolean hasWhipped, boolean hasChoc){
        int unitPrice = 5;
        if (hasWhipped) {unitPrice += 1;}
        if (hasChoc) {unitPrice += 2;}
        price=numberOfCoffees*unitPrice;
        return price;
    }
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check);
        boolean hasWhipped = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check);
        boolean hasChoc = chocolateCheckBox.isChecked();
        price=updatePrice(hasWhipped,hasChoc);
        priceMessage=createOrderSummary(price,hasWhipped,hasChoc);
        //composeEmail(priceMessage);
        //displayMessage(priceMessage);
       // display(numberOfCoffees);
      //  displayPrice(numberOfCoffees*5);
    }

//    public void composeEmail(String message) {
//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_EMAIL, "daniel.bortolussi97@gmail.com");
//        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for");
//        intent.putExtra(Intent.EXTRA_TEXT, message);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
//    }

    public String createOrderSummary(int price, boolean hasWhipped,boolean hasChoc){
        String[] adresses = {"daniel.bortolussi97@gmail.com"};
        String name = getName();
        String message = "Name: " + name + "\nAdd whipped cream? " + hasWhipped+ "\nAdd whipped cream? " + hasChoc +"\nQuantity: " + numberOfCoffees +"\nTotal: $" + price + "\nThank You!";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, adresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        return message;
    }

    public String getName(){
        EditText inputName = (EditText) findViewById(R.id.name_input);
        String name=inputName.getText().toString();
        return name;
    }

    public void increment(View view){
        numberOfCoffees++;
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check);
        boolean hasWhipped = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check);
        boolean hasChoc = chocolateCheckBox.isChecked();
        //int price=updatePrice();
        price=updatePrice(hasWhipped,hasChoc);
        display(numberOfCoffees);
        displayPrice(price);
    }

    public void decrement(View view){
        //int price=0;
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check);
        boolean hasWhipped = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check);
        boolean hasChoc = chocolateCheckBox.isChecked();
        if (numberOfCoffees==1){
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), "Cannot go lower than 1", duration);
            toast.show();
            return;
        }
        numberOfCoffees--;
        price=updatePrice(hasWhipped,hasChoc);
        display(numberOfCoffees);
        displayPrice(price);
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
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
