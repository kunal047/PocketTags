package rusk.com.pockettags;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {

    Set<String> languages = new ArraySet<>();

    String[] language ={"C","C++","Java",".NET","iPhone","Android","ASP.NET","PHP"};
    List<String> tagToAdd = new ArrayList<>();
    AutoCompleteTextView actv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TagContainerLayout mTagContainerLayout = (TagContainerLayout) findViewById(R.id.tagcontainerlayout);
        actv = (AutoCompleteTextView)findViewById(R.id.editTags);
        actv.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    Toast.makeText(MainActivity.this, actv.getText().toString(), Toast.LENGTH_SHORT).show();
                    tagToAdd.add(actv.getText().toString());
                    mTagContainerLayout.setTags(tagToAdd);
                    actv.setText("");
                    return true;
                }
                return false;
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,language);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
//        mTagContainerLayout.setTags(actv.getText().toString().trim());
//        Log.d("", "onCreate: adapter iss ************************************************8 " + adapter);
//        mTagContainerLayout.setTags(language);
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("========>>", actv.getText().toString());
                tagToAdd.add(actv.getText().toString());
                mTagContainerLayout.setTags(tagToAdd);
                actv.setText("");
            }
        });

        mTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {

            }

            @Override
            public void onTagLongClick(int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {
                Log.d("", "onTagCrossClick: position **************** " + position);
                mTagContainerLayout.removeTag(position);
                tagToAdd.remove(position);
            }
        });

    }



    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_SPACE) {
            Log.d("test", "Space bar pressed!");
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
