package second.test.btfun;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ToggleButton tb;
    Button chngename;
    EditText et;
    ListView li;
    Set<BluetoothDevice> si;
    ArrayList<String> list;
    BluetoothAdapter bta;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //assigns id to widgets
        assignid();
        bta= BluetoothAdapter.getDefaultAdapter();
        //set state of bluetooth toggle button
        setstate();
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changer(isChecked);
            }
        } );
            // gets all required permissions
            permi();


    }

    private void setstate()
    {
        et.setFocusable(false);
        if(bta.isEnabled()) {
            et.setText(bta.getName());
            tb.setChecked(true);
            Toast.makeText(this, "cheched", Toast.LENGTH_SHORT).show();
            getPaired();
            li.setVisibility(View.VISIBLE);
        }
        else {
            Toast.makeText(this, "not cheched", Toast.LENGTH_SHORT).show();
            //set name of bluetooth
            setname();
            li.setVisibility(View.INVISIBLE);
            tb.setChecked(false);
        }
    }

    private void setname()
    {
        bta.enable();
        et.setText(bta.getName());
        bta.disable();

    }

    private void changer(boolean isChecked)
    {
        if(isChecked == true)
        {
            bta.enable();
            li.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "Enabled", Toast.LENGTH_SHORT).show();
            getPaired();
        }
        if(isChecked == false)
        {
            li.setVisibility(View.INVISIBLE);
            bta.disable();
            Toast.makeText(MainActivity.this, "Disables", Toast.LENGTH_SHORT).show();
        }
    }

    private void permi()
    {
    ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.BLUETOOTH , Manifest.permission.BLUETOOTH_ADMIN,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
    }

    private void assignid()
    {
        tb = (ToggleButton) findViewById(R.id.btn);
        et = (EditText) findViewById(R.id.name);
        li = (ListView) findViewById(R.id.list);
        chngename = (Button) findViewById(R.id.chngname);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    public void getPaired()
    {

                list = new ArrayList<String>();
                if(si != null)
                    si = null;
                while(!bta.isEnabled());
                si = bta.getBondedDevices();
                if(si.size()>0)
                {
                    for(BluetoothDevice bd : si)
                        list.add(bd.getName() +"  \n  " + bd.getAddress());
                    Toast.makeText(MainActivity.this, "Came", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(MainActivity.this, "Came again"+si.size(), Toast.LENGTH_SHORT).show();
                ArrayAdapter ad = new ArrayAdapter(MainActivity.this , android.R.layout.simple_list_item_1 , list);
                ad.notifyDataSetChanged();
                li.setAdapter(ad);


    }
    public void setChngename(View vv)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("CHANGE NAME");
        View v = LayoutInflater.from(this).inflate(R.layout.new_name,null , false);
        final EditText ett = (EditText) v.findViewById(R.id.input);
        ett.setText("");
        builder.setView(v);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!ett.getText().toString().equals("")) {
                    bta.setName(ett.getText().toString());
                    et.setText(ett.getText().toString());
                }dialog.dismiss();
            }
        });
        builder.setCancelable(true);
        if(bta.isEnabled())
        builder.show();
        else
            Toast.makeText(this, "Please Enable bluetooth first", Toast.LENGTH_SHORT).show();
    }
}
