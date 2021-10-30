package com.example.vijaya.myorder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    private static final String MAIN_ACTIVITY_TAG = "MainActivity";
    final int PIZZA_PRICE = 5;
    /*only meat affects price. Veggies and condiments are wrapped in pizza_price*/
    final int SAUSAGE = 1;
    final double CHICKEN = 1.50;
    final int BACON = 2;
    int quantity = 1;//set so user is automatically ordering one pizza
    boolean order_complete = false;
    public static String orderSummaryMessage = "TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed
        if (requestCode == 2) {//redirect to the order activity
            Intent ordercomplete = new Intent(this, order.class);
            startActivity(ordercomplete);//redirects to summary page
        }
    }

    public void submitOrder(View view) {
        // get user input
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();

        /*******************************************MEATS*************************************/

        // check if sausage is selected. used for order summary
        CheckBox sausage = (CheckBox) findViewById(R.id.sausage);
        boolean has_sausage = sausage.isChecked();

        // check if chicken is selected. used for order summary
        CheckBox chicken = (CheckBox) findViewById(R.id.chicken);
        boolean has_chicken = chicken.isChecked();

        // check if bacon is selected. used for order summary
        CheckBox bacon = (CheckBox) findViewById(R.id.bacon);
        boolean has_bacon = bacon.isChecked();

        /*****************************************VEGGIES*************************************/
        // check if mushroom is selected. only used for order summary
        CheckBox mushrooms = (CheckBox) findViewById(R.id.mushrooms);
        boolean has_mushrooms = mushrooms.isChecked();

        // check if onion is selected. only used for order summary
        CheckBox onions = (CheckBox) findViewById(R.id.onions);
        boolean has_onions = onions.isChecked();

        // check if tomatoes are selected. only used for order summary
        CheckBox tomatoes = (CheckBox) findViewById(R.id.tomatoes);
        boolean has_tomatoes = tomatoes.isChecked();

        /**************************************CONDIMENTS*************************************/

        // check if ranch is selected. only used for order summary
        // check if parmesan is selected. only used for order summary
        CheckBox parm = (CheckBox) findViewById(R.id.parmesean);
        boolean has_parm = parm.isChecked();

        // check if crushed red pepper is selected. only used for order summary
        CheckBox crp = (CheckBox) findViewById(R.id.crp);
        boolean has_crp = crp.isChecked();

        // check if salt/pepper is selected. only used for order summary
        CheckBox sp = (CheckBox) findViewById(R.id.saltpepper);
        boolean has_sp = sp.isChecked();

        // calculate and store the total price
        float totalPrice = calculatePrice(has_sausage, has_chicken, has_bacon);

        /*****************************BUTTON/EMAIL FUNCTIONALITY*********************************/

        //create and store the order summary
        orderSummaryMessage = createOrderSummary(userInputName, has_sausage, has_chicken, has_bacon, has_mushrooms,
                has_onions, has_tomatoes, has_parm, has_crp, has_sp, totalPrice);

        // Write the relevant code for making the buttons work(i.e implement the implicit and explicit intents
        sendEmail(orderSummaryMessage);//sends email
    }

    public void sendEmail(String output) {
        // Write the relevant code for triggering email

        // Hint to accomplish the task
        // Write the relevant code for triggering email
        String[] TO = {"toandchu142@mail.umkc.edu"};//user email to send to
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");//sets body formatting
        intent.putExtra(Intent.EXTRA_EMAIL, TO);//populates the receiver's email
        intent.putExtra(Intent.EXTRA_SUBJECT, "Pizza Order Summary");//populates the subject line
        intent.putExtra(Intent.EXTRA_TEXT, output);//populates the email body

        if (intent.resolveActivity(getPackageManager()) != null) {//checks for default email application
            startActivityForResult(Intent.createChooser(intent, "Choose an Email client :"),2);//redirects when order is complete

        } else {//if none found, give an error message
            Toast.makeText(this, "No email client installed", Toast.LENGTH_LONG);
        }
    }

    private String boolToString(boolean bool) {
        return bool ? (getString(R.string.yes)) : (getString(R.string.no));
    }

    private String createOrderSummary(String userInputName, boolean has_sausage,
                                      boolean has_chicken, boolean has_bacon,
                                      boolean has_mushrooms, boolean has_onions, boolean has_tomatoes,
                                       boolean has_parm, boolean has_crp, boolean has_sp, float price) {
        //concatenates strings to save summary
        String orderSummaryMessage = getString(R.string.order_summary_name, userInputName) + "\n" +
                getString(R.string.order_summary_sausage, boolToString(has_sausage)) + "\n" +
                getString(R.string.order_summary_chicken, boolToString(has_chicken)) + "\n" +
                getString(R.string.order_summary_bacon, boolToString(has_bacon)) + "\n" +
                getString(R.string.order_summary_mushrooms, boolToString(has_mushrooms)) + "\n" +
                getString(R.string.order_summary_onions, boolToString(has_onions)) + "\n" +
                getString(R.string.order_summary_tomatoes, boolToString(has_tomatoes)) + "\n" +
                getString(R.string.order_summary_parm, boolToString(has_parm)) + "\n" +
                getString(R.string.order_summary_crp, boolToString(has_crp)) + "\n" +
                getString(R.string.order_summary_sp, boolToString(has_sp)) + "\n" +
                getString(R.string.order_summary_quantity, quantity) + "\n" +
                getString(R.string.order_summary_total_price, price) + "\n";
        return orderSummaryMessage;
    }

    /**
     * Method to calculate the total price
     *
     * @return total Price
     */
    private float calculatePrice( boolean has_sasuage, boolean has_chicken, boolean has_bacon) {
        int basePrice = PIZZA_PRICE;
        if (has_sasuage) {//adds price of sausage
            basePrice += SAUSAGE;
        }
        if (has_chicken) {//adds price of chicken
            basePrice += CHICKEN;
        }
        if (has_bacon) {//adds price of bacon
            basePrice += BACON;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method increments the quantity of coffee cups by one
     *
     * @param view on passes the view that we are working with to the method
     */

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select less than one hundred cups of coffee");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.too_much_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }

    /**
     * This method decrements the quantity of coffee cups by one
     *
     * @param view passes on the view that we are working with to the method
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select atleast one cup of coffee");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.too_little_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }

    public void summary(View view){
        //same concept as the ordersubmit button is applied here
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();

        /*******************************************MEATS*************************************/
        // check if pepperoni is selected. used for order summary & price


        // check if sausage is selected. used for order summary
        CheckBox sausage = (CheckBox) findViewById(R.id.sausage);
        boolean has_sausage = sausage.isChecked();

        // check if chicken is selected. used for order summary
        CheckBox chicken = (CheckBox) findViewById(R.id.chicken);
        boolean has_chicken = chicken.isChecked();



        // check if bacon is selected. used for order summary
        CheckBox bacon = (CheckBox) findViewById(R.id.bacon);
        boolean has_bacon = bacon.isChecked();


        /*****************************************VEGGIES*************************************/
        // check if bell pepper is selected. only used for order summary


        // check if mushroom is selected. only used for order summary
        CheckBox mushrooms = (CheckBox) findViewById(R.id.mushrooms);
        boolean has_mushrooms = mushrooms.isChecked();

        // check if onion is selected. only used for order summary
        CheckBox onions = (CheckBox) findViewById(R.id.onions);
        boolean has_onions = onions.isChecked();


        // check if tomatoes are selected. only used for order summary
        CheckBox tomatoes = (CheckBox) findViewById(R.id.tomatoes);
        boolean has_tomatoes = tomatoes.isChecked();

        /**************************************CONDIMENTS*************************************/


        // check if parmesan is selected. only used for order summary
        CheckBox parm = (CheckBox) findViewById(R.id.parmesean);
        boolean has_parm = parm.isChecked();

        // check if crushed red pepper is selected. only used for order summary
        CheckBox crp = (CheckBox) findViewById(R.id.crp);
        boolean has_crp = crp.isChecked();

        // check if salt/pepper is selected. only used for order summary
        CheckBox sp = (CheckBox) findViewById(R.id.saltpepper);
        boolean has_sp = sp.isChecked();

        // calculate and store the total price
        float totalPrice = calculatePrice(has_sausage,has_chicken, has_bacon);

        /*****************************BUTTON/EMAIL FUNCTIONALITY*********************************/

        //create and store the order summary
        orderSummaryMessage = createOrderSummary(userInputName,  has_sausage, has_chicken,
               has_bacon, has_mushrooms, has_onions, has_tomatoes, has_parm, has_crp, has_sp, totalPrice);

        //Toast.makeText(this,orderSummaryMessage, Toast.LENGTH_LONG).show();
        Intent summary = new Intent(MainActivity.this, summary.class);
        startActivity(summary);//redirects to summary page
    }
}