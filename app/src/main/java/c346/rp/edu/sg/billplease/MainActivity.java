package c346.rp.edu.sg.billplease;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    Button splitBill, reset;
    EditText bill, pax, disc;
    ToggleButton toggleButtonGst, toggleButtonSrv;
    TextView displayTv;

    int gstNSrvCharge = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        splitBill = findViewById(R.id.splitBtn);
        reset = findViewById(R.id.resetBtn);

        bill = findViewById(R.id.billEt);
        pax = findViewById(R.id.paxEt);
        disc = findViewById(R.id.discEt);

        toggleButtonSrv = findViewById(R.id.toggleButtonSrv);
        toggleButtonGst = findViewById(R.id.toggleButtonGst);

        displayTv = findViewById(R.id.totalBillnPaxTv);

        toggleButtonGst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!toggleButtonGst.isChecked()) {

                    gstNSrvCharge = gstNSrvCharge - 7;

                } else {
                    gstNSrvCharge = gstNSrvCharge + 7;
                }
            }
        });

        toggleButtonSrv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!toggleButtonSrv.isChecked()) {

                    gstNSrvCharge = gstNSrvCharge - 10;

                } else {
                    gstNSrvCharge = gstNSrvCharge + 10;
                }
            }
        });

        splitBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bill.getText().length() > 0) {
                    double billAmt = Double.parseDouble(bill.getText().toString());
                    int paxAmt = Integer.parseInt(pax.getText().toString());
                    double addGstnSrvChg = (100.0 + gstNSrvCharge) / 100;

                    int discAmt = 0;

                    if (disc.getText().length() > 0) {
                        discAmt = Integer.parseInt(disc.getText().toString());
                    }


                    double addDisc = 0.0;

                    if ((discAmt < 100) && (discAmt > 0)) {
                        addDisc = (100.0 - discAmt) / 100;
                    } else if (discAmt == 0) {
                        addDisc = 1;
                    }

                    if (paxAmt > 1) {
                        if (addDisc >= 0.0) {
                            double totalBill = ((billAmt * addGstnSrvChg) * addDisc);
                            double totalBillwPax = ((billAmt * addGstnSrvChg) * addDisc) / paxAmt;

                            String amt = String.format("%.2f", totalBill);
                            String amtwPax = String.format("%.2f", totalBillwPax);

                            Log.i("HELP", amt + amtwPax);

                            displayTv.setText("Total Amount: $" + amt + "\n Per Pax: $" + amtwPax);
                        } else if (disc.getText().length() == 0) {
                            double totalBill = (billAmt * addGstnSrvChg);
                            double totalBillwPax = (billAmt * addGstnSrvChg) / paxAmt;

                            String amt = String.format("%.2f", totalBill);
                            String amtwPax = String.format("%.2f", totalBillwPax);

                            Log.i("HELP", amt + amtwPax);

                            displayTv.setText("Total Amount: $" + amt + "\n Per Pax: $" + amtwPax);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Pax must be more than 1", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Bill Amount and Pax is Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bill.setText("");
                pax.setText("");
                disc.setText("");
                displayTv.setText("");
                toggleButtonGst.setChecked(false);
                toggleButtonSrv.setChecked(false);
            }
        });

    }
}
